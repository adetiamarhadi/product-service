package com.github.adetiamarhadi.productservice.core.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "productlookup")
public class ProductLookupEntity {

	@Id
	private String productId;

	@Column(unique = true)
	private String title;
}
