package com.github.adetiamarhadi.productservice.command;

import com.github.adetiamarhadi.productservice.core.events.ProductCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {

	public ProductAggregate() {}

	@CommandHandler
	public ProductAggregate(CreateProductCommand createProductCommand) {
		// validate create product command
		if (createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("price cannot be less or equal than zero");
		}

		if (createProductCommand.getTitle() == null
				|| createProductCommand.getTitle().isBlank()) {
			throw new IllegalArgumentException("title cannot be empty");
		}

		ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();

		BeanUtils.copyProperties(createProductCommand, productCreatedEvent);

		AggregateLifecycle.apply(productCreatedEvent);
	}
}
