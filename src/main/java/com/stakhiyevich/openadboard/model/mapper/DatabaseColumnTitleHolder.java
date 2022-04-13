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

    public static final String CATEGORY_ID = "item_categories.id";
    public static final String CATEGORY_TITLE = "item_categories.title";

    public static final String CITY_ID = "cities.id";
    public static final String CITY_TITLE = "cities.title";

    private DatabaseColumnTitleHolder() {
    }
}
