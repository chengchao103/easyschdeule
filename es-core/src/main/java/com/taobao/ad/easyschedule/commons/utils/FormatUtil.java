/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {

    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd hh:mm:ss";
    static SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_PATTERN);

    /**
    *
    * @param date
    * @return
    */
   public static String getDateAsString(Date date)
   {
       if (date==null)
       {
           return null;
       }
       return dateFormatter.format(date);
   }

   /**
    *
    * @param dateStr
    * @return
    * @throws ParseException
    */
   public static Date parseStringToDate(String dateStr) throws ParseException
   {
       if (dateStr==null)
       {
           return null;
       }
       return dateFormatter.parse(dateStr);
   }
    
}
