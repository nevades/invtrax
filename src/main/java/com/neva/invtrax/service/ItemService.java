package com.neva.invtrax.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neva.invtrax.models.Item;
import com.neva.invtrax.models.ItemDto;
import com.neva.invtrax.repository.ItemRepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public ItemDto convertToDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        return dto;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> getItemById(long id) {
        return itemRepository.findById(id);
    }

    public boolean itemExistsByName(String name) {
        return itemRepository.findByName(name) != null;
    }

    public void createItem(ItemDto itemDto) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        itemRepository.save(item);
    }

    public void updateItem(Item item, ItemDto itemDto) {
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        itemRepository.save(item);
    }

    public void deleteItem(long id) {
        itemRepository.deleteById(id);
    }

}