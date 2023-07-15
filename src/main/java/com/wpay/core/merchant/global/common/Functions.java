package com.wpay.core.merchant.global.common;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Functions {

    public static java.util.function.Function<Date, String> getTimestampMilliSecond =
            (date) -> (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(date);


    public static java.util.function.Function<String, String> getIdcDvdCd = (serverName) -> {
        serverName = serverName.toLowerCase();
        if(serverName.indexOf("ks") == 0) return "NW";
        if(serverName.indexOf("fc") == 0) return "WP";
        if(serverName.indexOf("stg") == 0) return "ST";
        if(serverName.indexOf("dev") == 0) return "DE";
        if(serverName.indexOf("localhost") == 0) return "LO";
        return "UN"; // unknown server name
    };
}
