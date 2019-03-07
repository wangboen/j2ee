package com.wbe.j2ee.common;

import java.util.UUID;

public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
