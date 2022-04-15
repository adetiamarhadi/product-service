package com.github.adetiamarhadi.productservice.command;

import com.github.adetiamarhadi.productservice.core.data.ProductLookupEntity;
import com.github.adetiamarhadi.productservice.core.data.ProductLookupRepository;
import com.github.adetiamarhadi.productservice.core.events.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventsHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductLookupEventsHandler.class);

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

	@ResetHandler
	public void reset() {
		productLookupRepository.deleteAll();

		LOGGER.info("ProductEventsHandler reset triggered");
	}
}
