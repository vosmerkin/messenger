package com.messenger.ui.services;

public class Addresses {

    private static String host1 = "http://192.168.31.35:8080";
    private static String host2 = "http://localhost:8080";

    private static final String ip = "http://3.86.107.142";
    private static final int port = 8080;
    private static final String host = ip + ":" + port;


    //private static String host = host2;


    public static final String GRPC_ADDRESS = ip;
    public static final int GRPC_PORT = port;

    public static final String CREATE = host + "/CRUDaddnames";
    public static final String UPDATE = host + "/CRUDupdatenames";
    public static final String DELETE = host + "/CRUDdeletenames?name=";


    public static final String ROOM_REQUEST = host + "/getRoom?name=";
    public static final String ROOM_CREATE = host + "/createRoom";
    public static final String USER_REQUEST = host + "/getUser?name=";
}
