package com.neva.invtrax.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neva.invtrax.models.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

    public Item findByName(String name);
}
