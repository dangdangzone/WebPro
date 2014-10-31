package org.web.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 */
public final class SysDate 
{
    public static Timestamp getSysDate() 
    {
		Date date = Calendar.getInstance().getTime();
		return getFormatSimpleDate(new Timestamp(date.getTime()));
    }

    public static Timestamp getFormatSimpleDate(Date date) 
    {

    	String format = "yyyy-MM-dd HH:mm:ss";
    	SimpleDateFormat df = new SimpleDateFormat(format);
    	String dateT = df.format(date);
    	try 
    	{
    		return new Timestamp(df.parse(dateT).getTime());
    	} 
    	catch (ParseException e)
    	{
    		e.printStackTrace();
    	}
    	return null;
    }

    public static Timestamp getSysDate(String dateStr) 
    {
    	String format = "yyyy-MM-dd HH:mm:ss";
    	SimpleDateFormat df = new SimpleDateFormat(format);

    	try 
    	{
    		
    		return new Timestamp(df.parse(dateStr).getTime());
    	}
    	catch (ParseException e) 
    	{
    		e.printStackTrace();
    	}
    	return null;
    }
    
    public static Timestamp getSysDate(String dateStr,String format) 
    {
    	SimpleDateFormat df = new SimpleDateFormat(format);

    	try 
    	{
    		
    		return new Timestamp(df.parse(dateStr).getTime());
    	}
    	catch (ParseException e) 
    	{
    		e.printStackTrace();
    	}
    	return null;
    }
    
    public static String getCustomDate(String dateStr,String format,String formatT) 
    {
    	
    	SimpleDateFormat df = new SimpleDateFormat(format);
    	
    	try 
    	{
    		Date dDate = df.parse(dateStr);  
        	SimpleDateFormat sdf = new SimpleDateFormat(formatT);
        	return sdf.format(dDate);
    	}
    	catch (ParseException e) 
    	{
    		e.printStackTrace();
    	}
    	return null;
    }
    
    public static String getCustomDate(String dateStr,String format) 
    {
    	String formatT = "yyyy-MM-dd HH:mm:ss";
    	SimpleDateFormat df = new SimpleDateFormat(formatT);
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
    	try 
    	{
    		Date dDate = df.parse(dateStr);  
        	return sdf.format(dDate);
    	}
    	catch (ParseException e) 
    	{
			try 
			{
				sdf.parse(dateStr);
				return dateStr;
			}
			catch (ParseException err) 
			{
				err.printStackTrace();
			}
    	}
    	return null;
    }
    
    public static String getDate()
    {
    	Date date = Calendar.getInstance().getTime();
    	return getFormatDate(new Timestamp(date.getTime()));
    }

    public static String getFormatDate(Date date) 
    {
    	String format = "yyyy-MM-dd HH:mm:ss";
    	SimpleDateFormat df = new SimpleDateFormat(format);
    	String dateT = df.format(date);
    	return dateT;
    }
    
}
