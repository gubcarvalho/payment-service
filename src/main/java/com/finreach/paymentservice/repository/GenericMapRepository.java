package com.finreach.paymentservice.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class GenericMapRepository<T, K> {
	
	private final Map<K, T> data = new HashMap<>();

	public void save(K key, T entity) {
    	if (key == null || entity == null)
    		return;

    	this.data.put(key, entity);
	}
	
	public Optional<T> findById(K key) {
        return Optional.ofNullable(this.data.get(key));
    }

	public boolean exists(K key) {
		if (key == null)
			return false;
		
		return findById(key).isPresent();
    }
	
	public List<T> findAll() {
    	return this.data.values().stream()
    			.collect(Collectors.toList());
    }
}