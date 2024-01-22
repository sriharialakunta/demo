package com.wipro.capstone.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.capstone.entity.LineItem;
import com.wipro.capstone.exception.ResourceNotFoundException;
import com.wipro.capstone.repository.LineItemRepository;
import com.wipro.capstone.service.LineItemService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LineItemServiceImpl implements LineItemService {

	@Autowired
	private LineItemRepository repository;

	@Override
	public List<LineItem> findAllLineItems() {
		
		List<LineItem> items = repository.findAll();
		if (items.isEmpty()) {
			log.error("EMPTY LineItems");
			throw new ResourceNotFoundException("LineItems");
		}
		return items;
	}

	@Override
	public Optional<LineItem> searchLineItem(int id) {
		Optional<LineItem> item = repository.findById(id);
		if (item.isEmpty()) {
			log.error("LineItem with id : " + id + " not found");
			throw new ResourceNotFoundException("LineItem with id : " + id);
		}
		return item;
	}

	@Override
	public LineItem addLineItem(LineItem item) {
		return repository.save(item);
	}

	@Override
	public String deleteLineItemById(int id) {
		
		log.info("Fetching & Deleting LineItem with id " + id);
		searchLineItem(id);
		repository.deleteById(id);
		return "Deleted lineitem with id : " + id;
	}

	@Override
	public String deleteAllLineItems() {
		repository.deleteAll();
		return "Deleted All lineitems";
	}


	@Override
	public List<LineItem> saveAllLineItems(List<LineItem> list) {
		return repository.saveAll(list);
	}

}
