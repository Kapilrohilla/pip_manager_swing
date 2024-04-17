/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.managerapp.Utility;

/**
 *
 * @author Lenovo
 */
public class APIs {

//    public static String BASE_URL = "http://3.111.211.154:8080/";
//    public static String BASE_URL = "http://13.233.114.27:8080/";
    public static String LOGIN = Config.BASE_URL + "login-manager";

    public static String GET_MANAGERS_ACCOUNT = Config.BASE_URL + "get-manager-accounts";

    public static String GET_MANAGERS_ORDERS = Config.BASE_URL + "get-manager-orders";

    public static String GET_MANAGERS_POSITIONS = Config.BASE_URL + "get-manager-positions";

    public static String JOURNAL_DATA = Config.BASE_URL + "journal";

    public static String GET_ALL_GROUPS = Config.BASE_URL + "get-all-groups";

    public static String GET_OPEN_POSITIONS = Config.BASE_URL + "get-open-positions";

    public static String GET_TRANSACTIONS = Config.BASE_URL + "get-transactions";

    public static String ADD_BALANCE = Config.BASE_URL + "add-balance";

    public static String DELETE_BALANCE = Config.BASE_URL + "delete-balance";

    public static String GET_ORDER_DETAILS = Config.BASE_URL + "get-order-detail";

    public static String GET_POSITION_DETAILS = Config.BASE_URL + "get-position-detail";

    public static String GET_DEAL_DETAILS = Config.BASE_URL + "get-deal-detail";

    public static String GET_CATEGORY_GROUPS = Config.BASE_URL + "get-category-groups";

    public static String UPDATE_USER = Config.BASE_URL + "update-user";

    public static String CREATE_ORDER = Config.BASE_URL + "create-manager-order";

    public static String GET_SYMBOL_CATEGORY = Config.BASE_URL + "get-all-categories";

    public static String UPDATE_ORDER = Config.BASE_URL + "update-order";

    public static String BULK_CLOSING_BY_SYMBOL = Config.BASE_URL + "exit-symbol-positions-manager";

    public static String BULK_CLOSING = Config.BASE_URL + "exit-all-positions-manager";

    public static String BULK_CLOSING_ORDER_TYPE = Config.BASE_URL + "exit-positions-by-order-type-manager";

    public static String CLOSE_POSITION = Config.BASE_URL + "exit-position-manager";

    public static String GET_SINGLE_CLIENT_DETAILS = Config.BASE_URL + "get-client";

    public static String CREATE_CLIENT = Config.BASE_URL + "create-client";

    public static String CREATE_TRADING_ACCOUNT = Config.BASE_URL + "signup";

    public static String DELETE_HISTORY = Config.BASE_URL + "delete-position";

    public static String UPDATE_POSITION = Config.BASE_URL + "update-position";

    public static String DELETE_ORDER = Config.BASE_URL + "delete-order-manager";
    // exit-position-manager
    // body positionId
}
