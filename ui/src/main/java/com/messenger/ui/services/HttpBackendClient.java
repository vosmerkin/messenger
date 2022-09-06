package com.messenger.ui.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.common.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class HttpBackendClient {
    private static final Logger log = LoggerFactory.getLogger(HttpBackendClient.class);


    private HttpClient client = HttpClient.newHttpClient();
    private HttpRequest request;

    public HttpBackendClient() {
    }

//    public HttpBackendClient(String endpoint, HttpMethod method, String data, Map<String, String> headers) {   //used in test
//        HttpRequest.Builder builder = HttpRequest.newBuilder();
//        builder.uri(URI.create(endpoint));
//        if (headers != null && !headers.isEmpty()) {
//            headers.keySet().forEach(s -> builder.header(s, headers.get(s)));
//        }
//        if (HttpMethod.GET.equals(method) || isNull(method)) {
//            builder.GET();
//        } else if (HttpMethod.POST.equals(method) && !"".equals(data)) {
//            builder.POST(HttpRequest.BodyPublishers.ofString(data));
//        } else if (HttpMethod.PUT.equals(method)) {
//            builder.PUT(HttpRequest.BodyPublishers.ofString(data));
//        } else if (HttpMethod.DELETE.equals(method)) {
//            builder.DELETE();
//        } else {
////            log.info("Request error - wrong method");
//        }
//        request = builder.build();
//
//
//    }

    public String call() throws IOException, InterruptedException {
        String result;
        HttpResponse<String> response;
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        result = response.body();
        return result;
    }


//    public String createAction(Name data) {
//        String result;
//
//        request = HttpRequest.newBuilder(URI.create(Adresses.CREATE))
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(data.toJson()))
//                .build();
//        try {
//            result = this.call();
//        } catch (IOException | InterruptedException e) {
////            e.printStackTrace();
//            result = "Request Error";
//            JOptionPane.showMessageDialog(null,
//                    "InfoBox: " + result,
//                    "HttpClient Error",
//                    JOptionPane.INFORMATION_MESSAGE);
//            log.debug(result);
//        }
//        return result;
//    }


    public UserDto userRequest(String name) {
        UserDto result = null;
        String resultString;
        request = HttpRequest.newBuilder(URI.create(Adresses.USER_REQUEST + name))
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            resultString = response.body();
        } catch (IOException | InterruptedException e) {
            resultString = "Request Error";
            JOptionPane.showMessageDialog(null,
                    "InfoBox: " + resultString,
                    "HttpClient Error",
                    JOptionPane.INFORMATION_MESSAGE);
//            log.debug(result);
        }
//        result =
//        var om = new ObjectMapper();
//        try {
//            result = om.readValue(resultString, UserDto.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
        return result.fromJson(resultString);
    }


    public String userUpdate(UserDto data) {
        String result;
        request = HttpRequest.newBuilder(URI.create(Adresses.UPDATE))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(data.toJson()))
                .build();
        try {
            result = this.call();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            result = "Request Error";
            JOptionPane.showMessageDialog(null,
                    "InfoBox: " + result,
                    "HttpClient Error",
                    JOptionPane.INFORMATION_MESSAGE);
            log.debug(result);
        }
        return result;
    }


//    public String deleteAction(String name) {
//        String result;
//        request = HttpRequest.newBuilder(URI.create(Adresses.DELETE + name))
//                .header("Content-Type", "application/json")
//                .DELETE()
//                .build();
//        try {
//            result = this.call();
//        } catch (IOException | InterruptedException e) {
////            e.printStackTrace();
//            result = "Request Error";
//            JOptionPane.showMessageDialog(null,
//                    "InfoBox: " + result,
//                    "HttpClient Error",
//                    JOptionPane.INFORMATION_MESSAGE);
//            log.debug(result);
//        }
//        return result;
//    }

//    public String request(String address,
//                          String header1,
//                          String header2,
//                          String method,
//                          String data) {
//        String result;
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest.Builder builder = HttpRequest.newBuilder();
//        builder.uri(URI.create(address));
//        if (!"".equals(header1) && !"".equals(header2)) {
//            builder.header(header1, header2);
//        } else if (("".equals(header1) && !"".equals(header2)) ||
//                (!"".equals(header1) && "".equals(header2))) {
//            log.info("Request error - wrong header");
//        }
//        if ("GET".equals(method) || "".equals(method)) {
//            builder.GET();
//        } else if ("POST".equals(method) && !"".equals(data)) {
//            builder.POST(HttpRequest.BodyPublishers.ofString(data));
//        } else if ("PUT".equals(method)) {
//            builder.PUT(HttpRequest.BodyPublishers.ofString(data));
//        } else if ("DELETE".equals(method)) {
//            builder.DELETE();
//        } else {
//            log.info("Request error - wrong method");
//        }
//        HttpRequest request = builder.build();
//        HttpResponse<String> response;
//        try {
//            response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            result = response.body();
//        } catch (IOException | InterruptedException e) {
//            result = "Request Error";
//            JOptionPane.showMessageDialog(null,
//                    "InfoBox: " + result,
//                    "HttpClient Error",
//                    JOptionPane.INFORMATION_MESSAGE);
//            log.debug(result);
//        }
//        return result;
//    }
}
