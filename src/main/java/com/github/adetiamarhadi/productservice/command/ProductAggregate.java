package com.github.adetiamarhadi.productservice.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.spring.stereotype.Aggregate;

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
	}
}
