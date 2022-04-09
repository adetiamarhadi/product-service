package com.github.adetiamarhadi.productservice.query;

import com.github.adetiamarhadi.productservice.core.data.ProductEntity;
import com.github.adetiamarhadi.productservice.core.data.ProductRepository;
import com.github.adetiamarhadi.productservice.core.events.ProductCreatedEvent;
import com.github.adetiamarhadi.sagacore.events.ProductReservationCancelledEvent;
import com.github.adetiamarhadi.sagacore.events.ProductReservedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventsHandler.class);

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
    }

    @EventHandler
    public void on(ProductReservedEvent productReservedEvent) {

        ProductEntity productEntity = productRepository.findByProductId(productReservedEvent.getProductId());

        LOGGER.debug("ProductReservedEvent: current product quantity " + productReservedEvent.getQuantity());

        productEntity.setQuantity(productEntity.getQuantity() - productReservedEvent.getQuantity());
        productRepository.save(productEntity);

        LOGGER.debug("ProductReservedEvent: new product quantity " + productReservedEvent.getQuantity());

        LOGGER.info("ProductReservedEvent is called for productId: " + productReservedEvent.getProductId() + " and orderId: " + productReservedEvent.getOrderId());
    }

    @EventHandler
    public void on(ProductReservationCancelledEvent productReservationCancelledEvent) {

        ProductEntity productEntity = productRepository.findByProductId(productReservationCancelledEvent.getProductId());

        LOGGER.debug("ProductReservationCancelledEvent: current product quantity " + productReservationCancelledEvent.getQuantity());

        int newQuantity = productEntity.getQuantity() + productReservationCancelledEvent.getQuantity();

        productEntity.setQuantity(newQuantity);

        productRepository.save(productEntity);

        LOGGER.debug("ProductReservationCancelledEvent: new product quantity " + productReservationCancelledEvent.getQuantity());
    }
}
