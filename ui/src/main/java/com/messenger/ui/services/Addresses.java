package com.messenger.ui.services;

public class Addresses {

    private static final String host = "http://3.86.107.142:8080";
    public static final String CREATE = host + "/CRUDaddnames";
    public static final String UPDATE = host + "/CRUDupdatenames";
    public static final String DELETE = host + "/CRUDdeletenames?name=";

    public static final String ROOM_REQUEST = host + "/getRoom?name=";
    public static final String USER_REQUEST = host + "/getUser?name=";
}
