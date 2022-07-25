package com.messenger.backend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.backend.entity.UserEntity;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserListToStringConverter implements AttributeConverter<List<UserEntity>, String> {
        private static final String SEPARATOR = ", ";



    @Override
    public String convertToDatabaseColumn(List<UserEntity> list) {
        if (list == null)
            return null;
        StringBuilder builder = new StringBuilder();
        for (UserEntity user:list)
            builder.append(user.getId());
        return builder.toString();
    }


    @Override
    public List<UserEntity> convertToEntityAttribute(String list) {
        if (list == null)
            return null;
        String[] pieces = list.split(SEPARATOR);
        if (pieces == null || pieces.length == 0) {
            return null;
        }

        List<UserEntity> userEntityList = new ArrayList<>();

        for (String s:pieces) userEntityList.add(new UserEntity(Integer.parseInt(s)));

        return userEntityList;
    }
}
