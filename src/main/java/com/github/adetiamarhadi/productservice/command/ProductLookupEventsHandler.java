package com.github.adetiamarhadi.productservice.command;

import com.github.adetiamarhadi.productservice.core.data.ProductLookupEntity;
import com.github.adetiamarhadi.productservice.core.data.ProductLookupRepository;
import com.github.adetiamarhadi.productservice.core.events.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventsHandler {

	private final ProductLookupRepository productLookupRepository;

	public ProductLookupEventsHandler(ProductLookupRepository productLookupRepository) {
		this.productLookupRepository = productLookupRepository;
	}

	@EventHandler
	public void on(ProductCreatedEvent productCreatedEvent) {

		ProductLookupEntity productLookupEntity = new ProductLookupEntity(productCreatedEvent.getProductId(),
				productCreatedEvent.getTitle());

		productLookupRepository.save(productLookupEntity);
	}
}
