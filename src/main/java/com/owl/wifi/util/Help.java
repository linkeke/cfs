package com.owl.wifi.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;



public class Help {
   
	 private static final Logger logger= LoggerFactory.getLogger(Help.class);
    
    /**
     * 判断非空
     * @param target
     * @return
     */
    public static boolean isNotNull(Object target) {
        return !isNull(target);
    }
    
    public static boolean isNull(Object target) {
        boolean isNull = false;
        if (null == target) {
            isNull = true;
        }
        if (!isNull) {
            // String
            if (target instanceof String) {
                String temp = target.toString().trim();
                if ("".equals(temp) || "null".equals(temp)
                        || "undefined".equals(temp)) {
                    isNull = true;
                }
            }
            // StringBuffer
            if (target instanceof StringBuffer) {
                String temp = target.toString().trim();
                if ("".equals(temp) || "null".equals(temp)
                        || "undefined".equals(temp)) {
                    isNull = true;
                }
            }
            //StringBuilder
            if (target instanceof StringBuilder) {
                String temp = target.toString().trim();
                if ("".equals(temp) || "null".equals(temp)
                        || "undefined".equals(temp)) {
                    isNull = true;
                }
            }
            // List
            else if (target instanceof List) {
                List<Object> temp = (List<Object>) target;
                if (0 == temp.size()) {
                    isNull = true;
                }
            }
            // Map
            else if (target instanceof Map) {
                Map<Object, Object> temp = (Map<Object, Object>) target;
                if (0 == temp.size()) {
                    isNull = true;
                }
            }
            else if (target instanceof Set) {
                Set<Object> temp = (Set<Object>) target;
                if (0 == temp.size()) {
                    isNull = true;
                }
            }
            // Array
            else if (target.getClass().isArray()) {
                Object[] temp = (Object[]) target;
                if (0 == temp.length) {
                    isNull = true;
                }
            }
        }
        return isNull;
    }
    
    public static void convertBean2Bean(Object from, Object to) {
        BeanUtils.copyProperties(from, to);
    }
    
    /**
     * 判断target是否存在于boss字符串中
     *
     *  
     * @param boss      (.. , .. , ..)
     * @param target    (..)
     * @return
     */
    public static boolean hasContain(String boss, String target) {
        boolean flag = false;
        if (isNull(boss) || isNull(target)) {
            return flag;
        }
        if (("," + boss + ",").indexOf("," + target + ",") >= 0) {
            flag = true;
        }
        
        return flag;
    }
    
    /**
     * 字符串增加内容
     *
     *  
     * @param target
     * @param content
     * @param checkExsit   true 排除已经存在的
     * 
     * @return
     */
    public static StringBuffer append(StringBuffer target, String content,
            boolean checkExsit) {
        if (checkExsit) {
            boolean flag = false;
            if (isNotNull(target)) {
                flag = hasContain(target.toString(), content);
            }
            if (!flag) {
                if (isNotNull(target)) {
                    target.append("," + content);
                }
                else {
                    target.append(content);
                }
            }
        }
        else {
            if (isNotNull(target)) {
                target.append("," + content);
            }
            else {
                target.append(content);
            }
        }
        
        return target;
    }
    
    /**
     * 判断对象是否存在， 如果存在返回自己， 如果不存在 返回not
     *
     *  
     * @param target
     * @param not
     * @return
     */
    public static Object exsitOrNot(Object target, Object not) {
        if (isNotNull(target)) {
            return target;
        }
        return not;
    }
    
    /**
     * 解析attr扩展字段信息
     * 有序的LinkedHashMap
     * <一句话功能简述>
     * <功能详细描述>
     * @param attr 值
     * @param splitStr 分隔符
     * @return [参数说明]
     * 
     * @return LinkedHashMap<String,String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static LinkedHashMap<String, String> parseAttrMap(String attr,
            String splitStr) {
        LinkedHashMap<String, String> attrLinkedHashMap = new LinkedHashMap<String, String>();
        logger.info("parseAttrMap attr:{0}", attr);
        logger.info("parseAttrMap splitStr:{0}", splitStr);
        if (Help.isNull(attr)) {
            logger.error("parseAttrMap attr为空!");
            return null;
        }
        if (Help.isNull(splitStr)) {
            logger.error("parseAttrMap splitStr为空!");
            return null;
        }
        String[] attrStr = attr.split(splitStr);
        for (int i = 0; i < attrStr.length; i++) {
            String attrStrValue = attrStr[i];
            String[] valueStr = attrStrValue.split(":");
            if (valueStr.length == 2) {
                attrLinkedHashMap.put(valueStr[0], valueStr[1]);
            }
            else {
                int strLength = attrStrValue.indexOf(":");
                String mapKey = attrStrValue.substring(0, strLength);
                String mapValue = attrStrValue.substring(strLength + 1,
                        attrStrValue.length());
                attrLinkedHashMap.put(mapKey, mapValue);
            }
        }
        return attrLinkedHashMap;
    }
    
    public static boolean isNumeric(String str){ 
        Pattern pattern = Pattern.compile("[0-9]+"); 
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches() ){
            return false; 
        } 
        return true; 
     }
    
    /**
     * <将字符串转换成int类型的list>
     * <功能详细描述>
     * @param in_str
     * @return [参数说明]
     * 
     * @return List<Integer> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static List<Integer> splitStringToList(String in_str){
        List<Integer> result =  new ArrayList<Integer>();
        if(isNotNull(in_str)){
            String[] in_arr =  in_str.split(",");
            for(String in:in_arr){
                try{
                    if(null !=in &&in.length()>0){
                        result.add(Integer.parseInt(in));
                    }
                }catch(Exception e){
                    logger.info("message{0}",e.getMessage());
                }
            }
        }
        return result;
    }
    
    
    public static String normalMac(String mac){
    	StringBuilder sb = new StringBuilder();
    	for(int i = 0 ; i < mac.length() ; i++){
    		sb.append(mac.charAt(i));
    		if(i < mac.length() - 2 && i%2 == 1 )
    			sb.append(':');
    	}
    	return sb.toString();
    }
    
    public static void main(String[] args){
    	System.out.println(normalMac("60833472C3DB"));
    }
}
