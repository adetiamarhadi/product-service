package com.github.adetiamarhadi.productservice;

import com.github.adetiamarhadi.productservice.command.interceptor.CreateProductCommandInterceptor;
import com.github.adetiamarhadi.productservice.core.errorhandling.ProductServiceEventErrorHandler;
import com.github.adetiamarhadi.sagacore.config.AxonConfig;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@EnableDiscoveryClient
@SpringBootApplication
@Import({AxonConfig.class})
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Autowired
	public void registerCreateProductCommandInterceptor(ApplicationContext applicationContext, CommandBus commandBus) {
		commandBus.registerDispatchInterceptor(applicationContext.getBean(CreateProductCommandInterceptor.class));
	}

	@Autowired
	public void configure(EventProcessingConfigurer config) {

		config.registerListenerInvocationErrorHandler("product-group", cf -> new ProductServiceEventErrorHandler());
//		config.registerListenerInvocationErrorHandler("product-group", cf -> PropagatingErrorHandler.instance());
	}
}
