package com.github.adetiamarhadi.productservice.query;

import com.github.adetiamarhadi.productservice.core.data.ProductEntity;
import com.github.adetiamarhadi.productservice.core.data.ProductRepository;
import com.github.adetiamarhadi.productservice.core.events.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {

    private final ProductRepository productRepository;

    public ProductEventsHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception ex) throws Exception {
        throw ex;
    }

    @ExceptionHandler(resultType = IllegalStateException.class)
    public void handle(IllegalStateException ex) {
        //
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) throws Exception {

        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productCreatedEvent, productEntity);

        try {
            productRepository.save(productEntity);
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        if (true) {
            throw new Exception("Forcing exception in the Event Handler class");
        }
    }
}
