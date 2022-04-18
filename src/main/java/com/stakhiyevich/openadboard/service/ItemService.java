package com.stakhiyevich.openadboard.service;

import com.stakhiyevich.openadboard.model.entity.Item;
import com.stakhiyevich.openadboard.model.entity.User;
import jakarta.servlet.http.Part;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ItemService {

    boolean addItem(String title, BigDecimal price, String description, String contact, Long categoryId, User user, Long cityId, List<Part> parts);

    int countItems();

    List<Item> findItemsWithParameters(Map<String, String[]> parameters);

    int countItemsWithParameters(Map<String, String[]> parameters);

    int countItemsByUserId(long userId);

    List<Item> findPaginatedItems(int currentPage, int itemsPerPage);

    List<Item> findPaginatedItemsByUserId(long userId, int currentPage, int itemsPerPage);

    Optional<Item> findItemById(long id);

    boolean canUserEditItem(long userId, long itemId);

    boolean updateItem(long id, String title, BigDecimal price, String description, String contact, Long categoryId, User user, Long cityId, List<Part> parts);

    boolean deleteItemById(long id);

}
