package com.wipro.capstone.service;

import java.util.List;
import java.util.Optional;

import com.wipro.capstone.entity.LineItem;

public interface LineItemService {

	List<LineItem> findAllLineItems();

	Optional<LineItem> searchLineItem(int id);

	LineItem addLineItem(LineItem item);

	String deleteLineItemById(int id);

	String deleteAllLineItems();
	
	List<LineItem> saveAllLineItems(List<LineItem> list);

}
