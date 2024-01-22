package com.wipro.shopping.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wipro.shopping.model.Inventory;

@FeignClient(name = "INVENTORY", url = "${inventoryService}")
public interface InventoryFeign{

	@GetMapping("/product/{productId}")
	public ResponseEntity<Inventory> searchInventoryByProductId(@PathVariable("productId") int productId);

}
