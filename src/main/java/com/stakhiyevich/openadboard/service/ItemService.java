package com.stakhiyevich.openadboard.service;

import com.stakhiyevich.openadboard.exception.ServiceException;
import com.stakhiyevich.openadboard.model.entity.Item;
import com.stakhiyevich.openadboard.model.entity.User;
import jakarta.servlet.http.Part;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The Item service interface.
 */
public interface ItemService {
    /**
     * Adds a new item.
     *
     * @param title an item's title
     * @param price an item's price
     * @param description an item's description
     * @param contact an item's contact
     * @param categoryId an item's category id
     * @param user an item's user id
     * @param cityId an item's city id
     * @param parts a list of parts that was received within a multipart/from-data POST request
     * @return whether an item was successfully created
     */
    boolean addItem(String title, BigDecimal price, String description, String contact, Long categoryId, User user, Long cityId, List<Part> parts);

    /**
     * Count all items.
     *
     * @return the number of all items
     */
    int countItems();

    /**
     * Finds all items with specified parameters.
     *
     * @param parameters item's parameters from a request
     * @return a list of items
     */
    List<Item> findItemsWithParameters(Map<String, String[]> parameters) throws ServiceException;

    /**
     * Count all items with specified parameters.
     *
     * @param parameters item's parameters from a request
     * @return a number of items
     */
    int countItemsWithParameters(Map<String, String[]> parameters);

    /**
     * Counts all items with specified user id.
     *
     * @param userId user's id
     * @return a number of items
     */
    int countItemsByUserId(long userId);

    /**
     * Finds items for the current page.
     *
     * @param currentPage a current page number
     * @param itemsPerPage an items per page number
     * @return a list of items
     */
    List<Item> findPaginatedItems(int currentPage, int itemsPerPage) throws ServiceException;

    /**
     * Finds items for the current page with specified user id
     *
     * @param userId a user's id
     * @param currentPage a current page number
     * @param itemsPerPage an items per page number
     * @return a list of items
     */
    List<Item> findPaginatedItemsByUserId(long userId, int currentPage, int itemsPerPage) throws ServiceException;

    /**
     * Finds an item with the specified item id
     *
     * @param id item's id
     * @return an optional object of an item
     */
    Optional<Item> findItemById(long id) throws ServiceException;

    /**
     * Checks whether a user can edit an item, in other works whether it is a user's own item.
     *
     * @param userId a user's id
     * @param itemId an item's id
     * @return whether a user can edit an item
     */
    boolean canUserEditItem(long userId, long itemId);

    /**
     * Updates an item with given parameters.
     *
     * @param id an item's id
     * @param title an item's title to update
     * @param price an item's price to update
     * @param description an item's description to update
     * @param contact an item's contact to update
     * @param categoryId an item's category id to update
     * @param user an item's user
     * @param cityId an item's city id to update
     * @param parts a list of an item's picture parts that was received within a multipart/from-data POST request
     * @return whether an item was successfully updated
     */
    boolean updateItem(long id, String title, BigDecimal price, String description, String contact, Long categoryId, User user, Long cityId, List<Part> parts);

    /**
     * Deletes an item with the specified id
     *
     * @param id an item's id to delete
     * @return whether an item was successfully deleted
     */
    boolean deleteItemById(long id);
}
