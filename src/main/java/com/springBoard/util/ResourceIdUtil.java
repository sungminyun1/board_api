package com.springBoard.util;

import java.util.UUID;

public class ResourceIdUtil {
    public static String generate(){
        String[] uuid = UUID.randomUUID().toString().split("-");
        return String.join("", uuid);
    }
}
