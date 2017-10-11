package com.owl.wifi.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MyDateUtil {
  /**
   * 获取几个月前或几个月后的日期
   * i>0为i个月后的日期
   * i<0为i个月前的日期
   * */
	public static String getDate(int i){
		Calendar calendar = Calendar.getInstance();   
		calendar.add(Calendar.MONTH, i);    //得到前一个月   
		String year = calendar.get(Calendar.YEAR)+"";   
		String month = (calendar.get(Calendar.MONTH)+1)+"";  
		return year+"-"+month;
	}
	/**
	 * 获取几天后的日期
	 * */
	public static String getDate(String strDate ,String format,int i) throws ParseException{
		Calendar calendar = new GregorianCalendar(); 
		SimpleDateFormat sdf = new SimpleDateFormat(format); 
		Date date1 = sdf.parse(strDate);
		calendar.setTime(date1); //放入你的日期 
		calendar.add(Calendar.DATE, i);   
		String year = calendar.get(Calendar.YEAR)+"";   
		String month = (calendar.get(Calendar.MONTH)+1)+"";  
		String day = (calendar.get(Calendar.DATE))+""; 
		month=Integer.parseInt(month)<10?"0"+month:month;
		day=Integer.parseInt(day)<10?"0"+day:day;
		return year+"-"+month+"-"+day;
	}
	/**
	 * 获取某月的天数
	 * */
	public static int getMonthDays(String strDate,String format) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(format); 
		Calendar ca = new GregorianCalendar(); 
		Date date1 = sdf.parse(strDate); 
		ca.setTime(date1); //放入你的日期 
		return ca.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	/**
	 * 计算日期之间的时间
	 * */
	public static String calculatTime(Date dateBegin,Date dateEnd){
	      StringBuffer result =new StringBuffer();
		 
		  long diff = dateEnd.getTime() - dateBegin.getTime();//这样得到的差值是微秒级别
		  long days = diff / (1000 * 60 * 60 * 24);
		 
		  long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
		  long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
		  long seconds = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60)-minutes*(1000*60))/(1000);
		  System.out.println(""+days+"天"+hours+"小时"+minutes+"分"+seconds+"秒");
		  if(days==0&&hours<6){
			  if(hours>0){
				  result.append(hours+"小时");
			  }
			  if(minutes>0){
				  result.append(minutes+"分");
			  }
			  if(seconds>=0){
				  result.append(seconds+"秒");
			  }
			  if(Help.isNotNull(result.toString())){
				  result.append("前");
			  }
		  }else{
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  result.append(sdf.format(dateBegin));
		  }
		return result.toString();
}
	public static String calculatTime(Date dateBegin,Date dateEnd,int range,String pattern){
	      StringBuffer result =new StringBuffer();
		 
		  long diff = dateEnd.getTime() - dateBegin.getTime();//这样得到的差值是微秒级别
		  long days = diff / (1000 * 60 * 60 * 24);
		 
		  long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
		  long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
		  long seconds = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60)-minutes*(1000*60))/(1000);
		  //System.out.println(""+days+"天"+hours+"小时"+minutes+"分"+seconds+"秒");
		  if(days==0&&hours<range){
			  if(hours>0){
				  result.append(hours+"小时");
			  }else if(minutes>0){
				  result.append(minutes+"分钟");
			  }else if(seconds>=0){
				  result.append(seconds+"秒");
			  }
			  /*if(minutes>0){
				  result.append(minutes+"分");
			  }
			  if(seconds>=0){
				  result.append(seconds+"秒");
			  }*/
			  if(Help.isNotNull(result.toString())){
				  result.append("前");
			  }
		  }else{
			  SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			  result.append(sdf.format(dateBegin));
		  }
		return result.toString();
}
	
	public static Date parseDate(String dateStr,String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            java.util.Date d = sdf.parse(dateStr);
            return new Date(d.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
	public static String formatDate(Date date,String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
	public static Calendar getDateOfLastMonth(Calendar date) {  
	    Calendar lastDate = (Calendar) date.clone();  
	    lastDate.add(Calendar.MONTH, -1);  
	    return lastDate;  
	}  
	  
	public static Calendar getDateOfLastMonth(String dateStr,String pattern) {  
	    SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
	    try {  
	        Date date = sdf.parse(dateStr);  
	        Calendar c = Calendar.getInstance();  
	        c.setTime(date);  
	        return getDateOfLastMonth(c);  
	    } catch (ParseException e) {  
	        throw new IllegalArgumentException("Invalid date format(yyyyMMdd): " + dateStr);  
	    }  
	}  
	/**
	 * 计算两个日期之间相隔天数
	 * */
	public static long calculatDay(Date dateBegin,Date dateEnd){
		 
		  long diff = dateEnd.getTime() - dateBegin.getTime();//这样得到的差值是微秒级别
		  long days = diff / (1000 * 60 * 60 * 24);
		 
		return days;
	}
	/**
	 * 计算两个日期之间相隔秒数
	 * */
	public static long calculatSeconds(Date dateBegin,Date dateEnd){
		 
		  long diff = dateEnd.getTime() - dateBegin.getTime();//这样得到的差值是微秒级别
		  long seconds = diff / (1000);
		 
		return seconds;
	}
public static int compare_date(Date dt1, Date dt2) {
        
        try {
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
/*public static void main(String args[]) {
    int i= compare_date(parseDate("2995-11-12 15:21:11", "yyyy-MM-dd HH:mm:ss"), parseDate("1999-12-11 09:59:11", "yyyy-MM-dd HH:mm:ss"));
    System.out.println("i=="+i);
 }*/
}
