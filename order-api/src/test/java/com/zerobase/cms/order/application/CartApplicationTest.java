package com.zerobase.cms.order.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.zerobase.cms.order.domain.model.Product;
import com.zerobase.cms.order.domain.product.AddProductCartForm;
import com.zerobase.cms.order.domain.product.AddProductForm;
import com.zerobase.cms.order.domain.product.AddProductItemForm;
import com.zerobase.cms.order.domain.redis.Cart;
import com.zerobase.cms.order.domain.repository.ProductRepository;
import com.zerobase.cms.order.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CartApplicationTest {

    @Autowired
    private CartApplication cartApplication;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    void ADD_AND_REFRESH_TEST() {
        Product p = addCart();
        Product result = productRepository.findWithProductItemsById(p.getId()).get();

        assertEquals(result.getName(), "나이키");
        assertEquals(result.getDescription(), "신발");
        assertEquals(result.getProductItem().size(), 3);
        assertEquals(result.getProductItem().get(0).getName(), "나이키0");
        assertEquals(result.getProductItem().get(0).getPrice(), 50000);
//        assertEquals(result.getProductItem().get(0).getCount(), 1);

        Long customerId = 100L;

        Cart cart = cartApplication.addCart(customerId, makeAddForm(result));
        assertEquals(cart.getMessage().size(), 0);

        cart = cartApplication.getCart(customerId);
        assertEquals(cart.getMessage().size(), 1);
//        p.getProductItem().get(0).setCount(0);
//        productRepository.save(p);
//        cartApplication.getCart()
    }

    AddProductCartForm makeAddForm(Product p) {
        AddProductCartForm.ProductItem productItem =
            AddProductCartForm.ProductItem.builder()
                .id(p.getProductItem().get(0).getId())
                .name(p.getProductItem().get(0).getName())
                .count(2)
                .price(20000)
                .build();
        return AddProductCartForm.builder()
            .id(p.getId())
            .sellerId(p.getSellerId())
            .name(p.getName())
            .description(p.getDescription())
            .items(List.of(productItem)).build();
    }

    Product addCart() {
        Long sellerId = 1L;

        AddProductForm form = makeProductForm("나이키", "신발", 3);

        return productService.addProduct(sellerId, form);
    }

    @Test
    void getCart() {
    }

    private static AddProductForm makeProductForm(String name, String description, int itemCount) {
        List<AddProductItemForm> itemForms = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            itemForms.add(makeProductItemFor(null, name + i));
        }
        return AddProductForm.builder()
            .name(name)
            .description(description)
            .items(itemForms)
            .build();
    }

    private static AddProductItemForm makeProductItemFor(Long productId, String name) {
        return AddProductItemForm.builder()
            .productId(productId)
            .name(name)
            .price(50000)
            .count(10)
            .build();
    }
}