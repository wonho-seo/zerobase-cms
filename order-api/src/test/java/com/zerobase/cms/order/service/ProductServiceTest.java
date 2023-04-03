package com.zerobase.cms.order.service;

import static org.junit.jupiter.api.Assertions.*;

import com.zerobase.cms.order.domain.model.Product;
import com.zerobase.cms.order.domain.product.AddProductForm;
import com.zerobase.cms.order.domain.product.AddProductItemForm;
import com.zerobase.cms.order.domain.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void addProduct_Test() {
        Long sellerId = 1L;

        AddProductForm form = makeProductForm("나이키", "신발", 3);

        Product p = productService.addProduct(sellerId,form);

        Product result = productRepository.findWithProductItemsById(p.getId()).get();

        assertEquals(result.getName(),"나이키");
        assertEquals(result.getDescription(), "신발");
        assertEquals(result.getProductItem().size(), 3);
        assertEquals(result.getProductItem().get(0).getName(),"나이키0");
        assertEquals(result.getProductItem().get(0).getPrice(),10000);
        assertEquals(result.getProductItem().get(0).getCount(),1);
    }

    private static AddProductForm makeProductForm(String name, String description, int itemCount){
        List<AddProductItemForm> itemForms = new ArrayList<>();
        for (int i = 0; i < itemCount;i++){
            itemForms.add(makeProductItemFor(null, name + i));
        }
        return AddProductForm.builder()
            .name(name)
            .description(description)
            .items(itemForms)
            .build();
    }
    private static AddProductItemForm makeProductItemFor(Long productId, String name){
        return AddProductItemForm.builder()
            .productId(productId)
            .name(name)
            .price(50000)
            .count(1)
            .build();
    }
}