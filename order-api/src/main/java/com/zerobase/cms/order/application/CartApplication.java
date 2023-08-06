package com.zerobase.cms.order.application;

import com.zerobase.cms.order.domain.model.Product;
import com.zerobase.cms.order.domain.model.ProductItem;
import com.zerobase.cms.order.domain.product.AddProductCartForm;
import com.zerobase.cms.order.domain.redis.Cart;
import com.zerobase.cms.order.exception.CustomException;
import com.zerobase.cms.order.exception.ErrorCode;
import com.zerobase.cms.order.service.CartService;
import com.zerobase.cms.order.service.ProductSearchService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartApplication {

    private final ProductSearchService productSearchService;
    private final CartService cartService;

    public Cart addCart(Long customerId, AddProductCartForm form) {

        Product product = productSearchService.getProductId(form.getId());
        if (product == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_PRODUCT);
        }

        Cart cart = cartService.getCart(customerId);

        if (cart != null && !addAble(cart, product, form)) {
            throw new CustomException(ErrorCode.ITEM_COUNT_NOT_ENOUGH);
        }
        return cartService.addCart(customerId, form);
    }

    public Cart getCart(Long customerId) {
        Cart cart = refreshCart(cartService.getCart(customerId));
        Cart returnCart = new Cart();
        returnCart.setCustomerId(customerId);
        returnCart.setProducts(cart.getProducts());
        returnCart.setMessage(cart.getMessage());
        cart.setMessage(new ArrayList<>());

        cartService.putCart(customerId, cart);
        return returnCart;
    }

    public void clearCart(Long customerId){
        cartService.putCart(customerId, null);
    }

    public Cart updateCart(Long customerId, Cart cart){
        cartService.putCart(customerId, cart);
        return getCart(customerId);
    }

    protected Cart refreshCart(Cart cart) {
        Map<Long, Product> products = productSearchService.getListByProductIds(
                cart.getProducts().stream().map(Cart.Product::getId).collect(Collectors.toList()))
            .stream()
            .collect(Collectors.toMap(Product::getId, product -> product));

        for (int i = 0; i < cart.getProducts().size(); i++) {
            Cart.Product cartProduct = cart.getProducts().get(i);

            Product product = products.get(cartProduct.getId());
            if (cartProduct == null) {
                cart.getProducts().remove(cartProduct);
                i--;
                cart.addMessage(cartProduct.getName() + " 상품이 삭제되었습니다");
                continue;
            }

            Map<Long, ProductItem> productItemMap = product.getProductItem().stream()
                .collect(Collectors.toMap(ProductItem::getId, productItem -> productItem));


            List<String> tmpMessage = new ArrayList<>();
            for (int j = 0; j < cartProduct.getItems().size(); j++) {
                Cart.ProductItem cartProductItem = cartProduct.getItems().get(i);
                ProductItem pi = productItemMap.get(cartProductItem);

                if (pi == null) {
                    cartProduct.getItems().remove(cartProductItem);
                    j--;
                    tmpMessage.add(cartProduct.getName() + "옵션이 삭제되었습니다.");
                    continue;
                }
                boolean isPriceChanged = false, isCountNotEnough = false;
                if (!cartProductItem.getPrice()
                    .equals(productItemMap.get(cartProductItem.getId()).getPrice())) {
                    isPriceChanged = true;
                    cartProductItem.setPrice(pi.getPrice());
                }
                if (cartProductItem.getCount() > productItemMap.get(cartProductItem.getId())
                    .getCount()) {
                    isCountNotEnough = true;
                    cartProductItem.setPrice(pi.getCount());
                }
                if (isCountNotEnough && isPriceChanged) {
                    tmpMessage.add(cartProduct.getName() + "가격변동, 수량이 부족하여 구매 가능한 최대치로 변경되었습니다.");
                } else if (isPriceChanged) {
                    tmpMessage.add(cartProduct.getName() + "가격이 변경되었습니다.");
                } else if (isCountNotEnough) {
                    tmpMessage.add(cartProduct.getName() + "수량이 변경되었습니다.");
                }
            }
            if (cartProduct.getItems().size() == 0){
                cart.getProducts().remove(cartProduct);
                i--;
                cart.addMessage(cartProduct.getName() + " 상품의 옵션이 모두 없어져 구매가 불가능합니다.");
                continue;
            }
            else if(tmpMessage.size() > 0){
                StringBuilder sb = new StringBuilder();
                sb.append(cartProduct.getName() + " 상품의 변동사항");
                sb.append(String.join(", ", tmpMessage));
                cart.addMessage(sb.toString());
            }
        }
        cartService.putCart(cart.getCustomerId(), cart);
        return cart;
    }

    private boolean addAble(Cart cart, Product product, AddProductCartForm form) {
        Map<Long, Integer> dbItemCountMap = product.getProductItem().stream()
            .collect(Collectors.toMap(ProductItem::getId, ProductItem::getCount));

        if(cart == null){
            return form.getItems().stream().noneMatch(
                item -> item.getCount() > dbItemCountMap.get(item.getId())
            );
        }

        Cart.Product cartProduct = cart.getProducts().stream()
            .filter(product1 -> product1.getItems().equals(form.getId()))
            .findFirst().orElse(null);

        if (cartProduct == null){
            return form.getItems().stream().noneMatch(
                item -> item.getCount() > dbItemCountMap.get(item.getId())
            );
        }

        Map<Long, Integer> cartItemCountMap = cartProduct.getItems().stream()
            .collect(Collectors.toMap(Cart.ProductItem::getId, Cart.ProductItem::getCount));

        return form.getItems().stream().noneMatch(
            productItem -> {
                Integer cartCount = cartItemCountMap.get(productItem.getId());
                if (cartCount == null){
                    cartCount = 0;
                }
                Integer currentCount = dbItemCountMap.get(productItem.getId());
                return productItem.getCount() + cartCount > currentCount;
            }
        );
    }
}
