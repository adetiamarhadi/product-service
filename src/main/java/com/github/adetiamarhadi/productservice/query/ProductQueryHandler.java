package com.github.adetiamarhadi.productservice.query;

import com.github.adetiamarhadi.productservice.core.data.ProductEntity;
import com.github.adetiamarhadi.productservice.core.data.ProductRepository;
import com.github.adetiamarhadi.productservice.query.rest.ProductRestModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductQueryHandler {

	private final ProductRepository productRepository;

	public ProductQueryHandler(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@QueryHandler
	public List<ProductRestModel> findProducts(FindProductQuery query) {

		List<ProductRestModel> products = new ArrayList<>();

		List<ProductEntity> storedProducts = productRepository.findAll();

		for (ProductEntity productEntity : storedProducts) {

			ProductRestModel productRestModel = new ProductRestModel();

			BeanUtils.copyProperties(productEntity, productRestModel);

			products.add(productRestModel);
		}

		return products;
	}
}
