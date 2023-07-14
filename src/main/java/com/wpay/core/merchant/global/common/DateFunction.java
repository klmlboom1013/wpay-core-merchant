package com.wpay.core.merchant.global.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;


public class DateFunction {

    public static Function<Date, String> TimestampMilliSecond =
            (date) -> (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(date);

}
