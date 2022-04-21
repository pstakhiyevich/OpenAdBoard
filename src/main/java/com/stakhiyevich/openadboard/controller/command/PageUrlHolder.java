package com.stakhiyevich.openadboard.controller.command;

public class PageUrlHolder {

    public static final String HOME_URL = "/controller?command=home_page";
    public static final String SIGN_UP_URL = "/controller?command=sign_up_page";
    public static final String ITEM_URL = "/controller?command=item_page&item_id=";
    public static final String ADD_ITEM_URL = "/controller?command=add_item_page";
    public static final String EDIT_ITEM_URL = "/controller?command=edit_item_page";
    public static final String CITY_MANAGEMENT_PAGE_URL = "/controller?command=city_management_page";
    public static final String CATEGORY_MANAGEMENT_PAGE_URL = "/controller?command=category_management_page";
    public static final String EDIT_USER_URL = "/controller?command=edit_user_page&user_id=";


    private PageUrlHolder() {
    }
}
