package com.stakhiyevich.openadboard.model.mapper;

public class DatabaseColumnTitleHolder {

    public static final String USER_ID = "users.id";
    public static final String USER_NAME = "users.name";
    public static final String USER_EMAIL = "users.email";
    public static final String USER_PASSWORD = "users.password";
    public static final String USER_REGISTRATION_DATE = "users.registration_date";
    public static final String USER_HASH = "users.hash";
    public static final String USER_AVATAR = "users.avatar";
    public static final String USER_STATUS = "user_statuses.title";
    public static final String USER_ROLE = "user_roles.title";

    public static final String ITEM_ID = "items.id";
    public static final String ITEM_TITLE = "items.title";
    public static final String ITEM_PRICE = "items.price";
    public static final String ITEM_DESCRIPTION = "items.description";
    public static final String ITEM_CONTACT = "items.contact";
    public static final String ITEM_CITY = "items.city";
    public static final String ITEM_CREATE_TIME = "items.create_time";
    public static final String ITEM_UPDATE_TIME = "items.update_time";
    public static final String ITEM_PICTURE = "items.picture";
    public static final String ITEM_ACTIVE = "items.active";
    public static final String ITEM_CATEGORY = "items.item_categories.title";
    public static final String ITEM_USER_ID = "items.users_id";

    public static final String CATEGORY_ID = "item_categories.id";
    public static final String CATEGORY_TITLE = "item_categories.title";

    public static final String CITY_ID = "cities.id";
    public static final String CITY_TITLE = "cities.title";

    public static final String COMMENT_ID = "comments.id";
    public static final String COMMENT_TEXT = "comments.text";
    public static final String COMMENT_CREATE_TIME = "comments.create_time";
    public static final String COMMENT_ITEM_ID = "comments.items_id";
    public static final String COMMENT_USER_ID = "comments.users_id";

    public static final String BOOKMARK_USER_ID = "bookmarks.users_id";
    public static final String BOOKMARK_ITEM_ID = "bookmarks.items_id";

    private DatabaseColumnTitleHolder() {
    }
}
