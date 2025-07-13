package com.neva.invtrax.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.neva.invtrax.models.Item;
import com.neva.invtrax.models.ItemDto;
import com.neva.invtrax.service.ItemService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping({ "", "/" })
    public String getItems(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        return "items/index";
    }

    @GetMapping("/create")
    public String createItem(Model model) {
        model.addAttribute("itemDto", new ItemDto());
        return "items/create";
    }

    @PostMapping("/create")
    public String createItem(@Valid @ModelAttribute ItemDto itemDto, BindingResult result) {
        if (itemService.itemExistsByName(itemDto.getName())) {
            result.addError(new FieldError("itemDto", "name", itemDto.getName(), false, null, null,
                    "Item already present in system."));
        }

        if (result.hasErrors()) {
            return "items/create";
        }

        itemService.createItem(itemDto);
        return "redirect:/items";
    }

    @GetMapping("/edit")
    public String editItem(Model model, @RequestParam long id) {
        var optionalItem = itemService.getItemById(id);

        if (optionalItem.isEmpty()) {
            return "redirect:/items";
        }

        Item item = optionalItem.get();
        model.addAttribute("item", item);
        model.addAttribute("itemDto", itemService.convertToDto(item));
        return "items/edit";
    }

    @PostMapping("/edit")
    public String editItem(
            Model model,
            @RequestParam long id,
            @Valid @ModelAttribute ItemDto itemDto,
            BindingResult result) {

        var optionalItem = itemService.getItemById(id);

        if (optionalItem.isEmpty()) {
            return "redirect:/items";
        }

        Item item = optionalItem.get();
        model.addAttribute("item", item);

        if (result.hasErrors()) {
            return "items/edit";
        }

        try {
            itemService.updateItem(item, itemDto);
        } catch (Exception e) {
            result.addError(new FieldError("itemDto", "name", itemDto.getName(), false, null, null,
                    "Item already present in system."));
            return "items/edit";
        }

        return "redirect:/items";
    }

    @GetMapping("/delete")
    public String deleteItem(@RequestParam long id) {

        Optional<Item> item = itemService.getItemById(id);

        if (item.isEmpty()) {
            return "redirect:/items";
        }

        itemService.deleteItem(id);

        return "redirect:/items";
    }
}
