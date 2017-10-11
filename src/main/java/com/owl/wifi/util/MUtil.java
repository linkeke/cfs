package com.owl.wifi.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Encoder;



/**
 * 帮助类
 * @author guowu
 * @version 1.0
 * @updated 10-七月-2013 16:13:44
 */
public class MUtil {
	
	public static List<String> dateFormatStrList = null;
	static{
		if(dateFormatStrList == null){
			dateFormatStrList = new ArrayList<String>();
		}
		dateFormatStrList.add("yyyy-MM-dd HH:mm:ss z");
		dateFormatStrList.add("yyyy-MM-dd HH:mm:ss");
		dateFormatStrList.add("yyyy-MM-dd");
		dateFormatStrList.add("MM-dd-yyyy HH:mm:ss z");
		dateFormatStrList.add("MM-dd-yyyy HH:mm:ss");
		dateFormatStrList.add("MM-dd-yyyy");
		dateFormatStrList.add("yyyy/MM/dd HH:mm:ss z");
		dateFormatStrList.add("yyyy/MM/dd HH:mm:ss");
		dateFormatStrList.add("yyyy/MM/dd");
		dateFormatStrList.add("yyyy.MM.dd HH:mm:ss z");
		dateFormatStrList.add("yyyy.MM.dd HH:mm:ss");
		dateFormatStrList.add("yyyy.MM.dd");
		dateFormatStrList.add("yyyy.MM.dd G 'at' HH:mm:ss z");
		dateFormatStrList.add("yyyy.MM.dd");
		dateFormatStrList.add("yyyyMMddHHmmssz");
		dateFormatStrList.add("yyyyMMddHHmmss");
		dateFormatStrList.add("yyyyMMdd");
		dateFormatStrList.add("yyyy-MM-dd KK:mm:ss a");
		dateFormatStrList.add("yyyy-MM-dd KK:mm:ss");
		dateFormatStrList.add("yyyy-MM-dd");
		dateFormatStrList.add("yyyy年MM月dd日 HH时mm分ss秒");
		dateFormatStrList.add("yyyy年MM月dd日");
	}
	
	public static String strObject(Object object){
		String result = "";
		if(object != null && !"".equals(object)){
			result = object.toString();
		}
		return result;
	}

	/**
	 * 生成n位验证码
	 * 
	 * @param
	 * @return
	 */
	public static String random(int n) {
		if (n < 1 || n > 10) {
			throw new IllegalArgumentException("cannot random " + n + " bit number");
		}
		Random ran = new Random();
		if (n == 1) {
			return String.valueOf(ran.nextInt(10));
		}
		int bitField = 0;
		char[] chs = new char[n];
		for (int i = 0; i < n; i++) {
			while(true) {
				int k = ran.nextInt(10);
				if( (bitField & (1 << k)) == 0) {
					bitField |= 1 << k;
					chs[i] = (char)(k + '0');
					break;
				}
			}
		}
		return new String(chs);
	}
	
	/**
	 * 数据验证
	 * 
	 * 		//如果不填数据，默认进行非空验证
	 * 		//如果需要进行其他规则验证，但是但是没有验证非空，自动验证非空
	 * 
	 * @param object
	 * @param types
	 * 
	 * 			0非空验证
	 * 			1 纯数字 
	 * 			2 手机号码 
	 * 			3 邮箱 
	 * 			4 日期 [yyyy-MM-dd] 
	 * 			5 中文
	 *        	6 非数字与特殊字符
	 *        	7身份证号
	 *        	8 判断是否为浮点数，包括double和float 
	 *        	9纯英文
	 *        	10只有字母、数字和下划线且不能以下划线开头和结尾返回true
	 *        	11验证电话号码     11位手机号码3-4位区号，7-8位直播号码，1－4位分机号	如：12345678901、1234-12345678-1234
	 *        	12验证money
	 *          13只能字母数字且不能以字母开头返回true
	 * @return
	 */
	public static boolean toJudgeInput(Object object, int... types){
		boolean result = true;
		
		//如果不填数据，默认进行非空验证
		if(types.length == 0){
			types = new int[]{0};
		}
		
		//如果需要进行其他规则验证，但是但是没有验证非空，自动验证非空
		if(Arrays.binarySearch(types, 0) < 0){
			int[] newTypes = new int[types.length + 1];
			newTypes[0] = 0;
			for(int i = 0; i < types.length;){
				newTypes[i + 1] = types[i++];
			}
			types = newTypes;
		}

		for(int type :types){
			switch (type) {
				case 0:
					//非空验证
					if(object != null && !"".equals(object)){
						result = true;
					}else{
						result = false;
					}
					break;
				case 1:
					//纯数字匹配
					try{
						Integer.parseInt(object.toString());
						result = true;
					}catch(Exception ex){
						result = false;
					}
					break;
				case 2: {
					//手机号码匹配
					Pattern pattern = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
					Matcher matcher = pattern.matcher(object.toString());
					result = matcher.matches();
					break;
				}
				case 3: {
					//邮箱匹配
					String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
					Pattern pattern = Pattern.compile(check);
					Matcher matcher = pattern.matcher(object.toString());
					result = matcher.matches();
					break;
				}
				case 4: {
					try{
						parseDateAuto(object.toString());
						result = true;
					}catch(ParseException ex){
						result = false;
					}
					break;
				}
				case 5: {
					//中文匹配(全部为中文)
					Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]*$");
					Matcher matcher = pattern.matcher(object.toString());
					result = matcher.matches();
					break;
				}
				case 6: {
					//非数字与特殊字符
					Pattern pattern = Pattern.compile("[^\\d\\:\\!\"\\#\\$\\%\\&\\'\\(\\)\\*\\+\\,\\-\\.\\/\\:\\;\\<\\=\\>\\?\\@\\[\\\\\\]\\^\\_\\`\\{\\|\\}\\~｛｝【】，。？]*");
					Matcher matcher = pattern.matcher(object.toString());
					result = matcher.matches();
					break;
				}case 7: {
					//定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）  
					Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");  
					//通过Pattern获得Matcher  
					Matcher idNumMatcher = idNumPattern.matcher(object.toString());  
					//判断用户输入是否为身份证号  
					result = idNumMatcher.matches();
					break;
				}case 8: {
					Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$"); 
					Matcher matcher = pattern.matcher(object.toString());
					result = matcher.matches();  
					break;
				}case 9: {
					//如果是纯英文返回true
					Pattern pattern = Pattern.compile("^[a-z]+$"); 
					Matcher matcher = pattern.matcher(object.toString());
					result = matcher.matches();  
					break;
				}case 10: {
					//只有字母、数字和下划线且不能以下划线开头和结尾返回true
					Pattern pattern = Pattern.compile("^(?!_)(?!.*?_$)[a-zA-Z0-9_]+$"); 
					Matcher matcher = pattern.matcher(object.toString());
					result = matcher.matches();  
					break;
				}case 11: {
					//验证电话号码     11位手机号码3-4位区号，7-8位直播号码，1－4位分机号	如：12345678901、1234-12345678-1234
					Pattern pattern = Pattern.compile("^(((13[0-9])|(15[^4,\\D])|(18[0-3,5-9])|(17[0-9]))\\d{8})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$"); 
					Matcher matcher = pattern.matcher(object.toString());
					result = matcher.matches();  
					break;
				}case 12: {
					//验证是否为money，是返回true,不是返回false
					Pattern pattern = Pattern.compile("^\\d{1,12}(?:\\.\\d{1,4})?$"); 
					Matcher matcher = pattern.matcher(object.toString());
					result = matcher.matches();  
					break;
				}case 13: {
					//只能字母数字且不能以数字开头返回true
					Pattern pattern = Pattern.compile("^(?![0-9])[a-zA-Z0-9]+$"); 
					Matcher matcher = pattern.matcher(object.toString());
					result = matcher.matches();  
					break;
				}case 14: {
					//如果包含@返回true
					result = object.toString().indexOf("@") == -1 ? false : true;
					break;
				}
			}
			if(result == false){
				break;
			}
		}
		
		return result;
	}

	/**
	 * 获取时间戳(11位)
	 * 
	 * @param
	 * @return
	 */
	public static Integer obtainTimestamp(Date date) {
		long second = date.getTime() / 1000;
		Integer m = Integer.decode(Long.toString(second));
		return m;
	}
	
	/**
	 * Convert Unix timestamp to normal date style
	 * @param timestampString
	 * @return
	 */
	public static String timeStamp2Date(String timestampString, String format){  
		Long timestamp = Long.parseLong(timestampString)*1000;  
		String date = new java.text.SimpleDateFormat(format).format(new java.util.Date(timestamp));  
		return date;  
	} 

	
	/**
	 * 格式化时间，时间进行
	 * @param dateStr
	 * @return
	 * @throws ParseException 
	 */
	public static Date parseDateAuto(String dateStr) throws ParseException{
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		Date date = null;
		for(String dsStr : dateFormatStrList){
			dateFormat.applyPattern(dsStr);
			try {
				date = dateFormat.parse(dateStr);
				break;
			} catch (ParseException e) {
				continue;
			}
		}
		if(date == null){
			throw new ParseException("日期格式转换错误", 0);
		}
		return date;
	}

	/**
	 * 格式化时间
	 * @param args
	 * @throws ParseException 
	 */
	public static Date parseDateFormat(String dateStr, String format) throws ParseException{
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = dateFormat.parse(dateStr);
		return date;
	}


	/**
	 * 格式化时间
	 * @param args
	 * @throws ParseException 
	 */
	public static String formDateFormat(Object date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String dateStr = dateFormat.format(date);
		return dateStr;
	}

	/**
	 * 比较两个时间
	 * 		date1 > date2   0
	 * 		date1 < date	1
	 * 		date1 == date2	3
	 * @throws Exception 
	 */
	public static int compare_date(Date date1, Date date2) {
		if(date1.getTime() > date2.getTime()){
			return 0;
		}else if(date1.getTime() < date2.getTime()){
			return 1;
		}
		return 3;
	}

	/**
	 * 计算end和start相差的天数
	 * @param start
	 * @param end
	 * @return
	 * 		天数
	 */
	public static int daysBetween(Date start, Date end){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		int day = 0;
		try {
			start = simpleDateFormat.parse(simpleDateFormat.format(start));
			end = simpleDateFormat.parse(simpleDateFormat.format(end));
			Calendar cal = Calendar.getInstance();    
			cal.setTime(start);    
			long time1 = cal.getTimeInMillis();                 
			cal.setTime(end);    
			long time2 = cal.getTimeInMillis();         
			long interval_days = (time2-time1)/(1000*3600*24);
			day = Integer.parseInt(String.valueOf(interval_days));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}


	/**
	 * collection集合拼成String
	 * @param set
	 * @return
	 */
	public static <T> String collectionToString(Collection<T> collection, String warpstr){
		StringBuffer sb = new StringBuffer();
		for(Object obj : collection){
			sb.append(warpstr).append(obj).append(warpstr).append(",");
		}
		String result = sb.toString();
		if(result.length() > 0){
			result = result.substring(0, result.lastIndexOf(","));
		}
		return result;
	}
	
	/**
	 * 数组转list
	 * @param tupe
	 * @return
	 */
	public static <T> List<T> tupeToList(T[] tupe) {
		List<T> list = new ArrayList<T>();
		for(T object : tupe){
			list.add(object);
		}
		return list;
    }
	

	/**
	 * 过滤需要的对象
	 * 
	 * @param paraMap
	 * 		需要过滤的map
	 * @param keys
	 * 		需要过滤的key
	 * @return
	 * 		map
	 */
	public static Map<String, Object> filterMapByKey(Map<String, Object> paraMap, String...keys){
		Map<String, Object> result = new HashMap<String, Object>();
		for(String key : keys){
			if(paraMap.containsKey(key)){
				result.put(key, paraMap.get(key));
			}
		}
		return result;
	}

	/**
	 * 构建一个Map
	 * @param key
	 * @param value
	 * @return
	 * 		map
	 */
	public static Map<String, Object> buildMap(String key, Object value){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(key, value);
		return map;
	}

	/**
	 * 获取当前时间和下个月时间
	 * @param currentDate
	 * @return
	 */
	public static Date[] currentDateAndNextMonth(){
		Calendar calendar = Calendar.getInstance();
		Date[] result = new Date[2];
		result[0] = calendar.getTime();
		calendar.add(Calendar.MONTH, 1);
		result[1] = calendar.getTime();
		return result;
	}

	/**
	 * 获取当前时间和下个指定叠加天数时间
	 * @param currentDate
	 * @return
	 */
	public static Date[] currentDateAndAddDay(int day){
		Calendar calendar = Calendar.getInstance();
		Date[] result = new Date[2];
		result[0] = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, day);
		result[1] = calendar.getTime();
		return result;
	}

	
	/**
	 * 获取一个uuid
	 * 		去掉了-然后转成了大写
	 * @return
	 */
	public static String uuid(){
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		return uuid;
	}
	
	/**
	 * md5
	 * @param content
	 * @return
	 */
	public static String md5(String content){
		String CHECK_CODE = "chuangtou";
		content = CHECK_CODE + content;
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			BASE64Encoder encoder = new BASE64Encoder();
			result = encoder.encode(md.digest(content.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	/**
	 * 签名参数
	 * @param content
	 * @return
	 */
	public final static String sign(String content) {  
        //16进制下数字到字符的映射数组    
		
		String CHECK_CODE = "tangqing";
		content = CHECK_CODE + content;
       char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
               'a', 'b', 'c', 'd', 'e', 'f' };  
       try {  
           byte[] strTemp = content.getBytes();  
           MessageDigest mdTemp = MessageDigest.getInstance("MD5");  
           mdTemp.update(strTemp);  
           byte[] md = mdTemp.digest();  
           int j = md.length;  
           char str[] = new char[j * 2];  
           int k = 0;  
           for (int i = 0; i < j; i++) {  
               byte byte0 = md[i];  
               str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
               str[k++] = hexDigits[byte0 & 0xf];  
           }  
           return new String(str);  
       } catch (Exception e) {  
           // TODO: handle exception  
           e.printStackTrace();  
           return null;  
       }  
   }  
	/**
	 * openfire用户密码加密
	 * @param noEncryPWD
	 * @return
	 */
	/*
	public static String encryptString(String noEncryPWD){
		String resultPWD = null;  
		String passWordKey = null; //passwordKey,从openfire数据库中读取
		
		SqlSession sqlSession = MySqlSession.getSessionFactory().openSession();
		
		OfpropertyMapper ofpropertyMapper = sqlSession.getMapper(OfpropertyMapper.class);
		//下面这段是从ofProperty表中查询得到passwordKey的值。 
		Ofproperty ofProperty = ofpropertyMapper.selectByPrimaryKey("passwordKey"); 
		if (ofProperty != null) { 
		    passWordKey = ofProperty.getPropValue();
		}  
		sqlSession.close();
		Blowfish blowFish = new Blowfish(passWordKey); //根据加密key初始化  
		resultPWD = blowFish.encryptString(noEncryPWD); //加密  
		return resultPWD; //返回加密后的结果 
	}
	*/
	
	/**
	 * openfire用户密码解密
	 * @param sCipherText
	 * @return
	 */
	/*
	public static String decryptString(String sCipherText){
		String resultPWD = null;  
		String passWordKey = null; //passwordKey,从openfire数据库中读取
		
		SqlSession sqlSession = MySqlSession.getSessionFactory().openSession();
		
		OfpropertyMapper ofpropertyMapper = sqlSession.getMapper(OfpropertyMapper.class);
		//下面这段是从ofProperty表中查询得到passwordKey的值。 
		Ofproperty ofProperty = ofpropertyMapper.selectByPrimaryKey("passwordKey"); 
		if (ofProperty != null) { 
		    passWordKey = ofProperty.getPropValue();
		}  
		sqlSession.close();
		Blowfish blowFish = new Blowfish(passWordKey); //根据加密key初始化  
		resultPWD = blowFish.decryptString(sCipherText); //加密  
		return resultPWD;  
	}
	*/
	
	/**
	 * 获取openfire格式的时间
	 * @return
	 */
	public static String openfireDate(){
		return String.format("00%d", System.currentTimeMillis());
	}
	
	/**
	 * 去除字符串开头和结尾的空格
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		if (str.charAt(0) == ' ') {
			str = str.substring(1, str.length());
		}
		if (str.charAt(str.length() - 1) == ' ') {
			str = str.substring(0, str.length() - 1);
		}
		if (str.charAt(0) == ' ' || str.charAt(str.length() - 1) == ' ') {
			str = MUtil.trim(str);
		}

		return str;
	}
	
	/**
	 * 判断是否是字符串数组
	 */
	public static boolean isStringArr(Object obj) {
		if (obj instanceof String[]) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String addSlashes(String str) {
		str = str.replaceAll("'", "''"); // SQL-92标准
		// str = str.replaceAll("\"", "\\\\\"");
		return str;
	}

	public static String stripSlashes(String str) {
		str = str.replaceAll("''", "'");// SQL-92标准
		// str = str.replaceAll("\\\\\"", "\"");
		return str;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return 空 true 非空 false
	 */
	public static boolean isEmpty(String str) {
		if (str != null && !"".equals(str) && !"null".equals(str)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 去除数组中的空对象
	 * 		null,
	 * 		"",
	 * 		"null"
	 * @param array
	 * @return
	 */
	public static String[] arrayClear(String[] array){
		if(array == null){
			return new String[]{};
		}
		List<String> list = new ArrayList<String>();
		for(String s : array){
			if("".equals(MUtil.strObject(s)) || "null".equalsIgnoreCase(MUtil.strObject(s))){
				continue;
			}
			list.add(s);
		}
		return list.toArray(new String[]{});
	}
	
	/**
	 * 更具jid获取用户帐号
	 * @param jid
	 * @return
	 */
	public static String getUserNameByJID(String jid){
		jid = MUtil.strObject(jid);
		return jid.substring(0, jid.indexOf("@"));
	}
	
	/**
	 * 根据身份证获取生日
	 * @param idCard
	 * @return
	 */
	public static String getBirthdayByIDCard(String idCard){
		String result = "";
		if(idCard.length() == 15){  
			result= idCard.substring(6, 12);  
		}else if(idCard.length() == 18){  
			result = idCard.substring(6, 14);  
		}
		if(result.length() != 8){
			result = null;
		}
		return result;  
	}
	
	/**
	 * 根据身份证获取性别
	 * 
	 * 		1如果是15位的号码，根据最末位的奇偶数判断，双数为＂女＂，单数为＂男＂
	 * 		2如果是18位的号码，根据号码的倒数第二位的奇偶数判断，双数为＂女＂，单数为＂男＂
	 * 
	 * 		如果是错误的身份证号返回null
	 * 
	 * @param idCard
	 * 		
	 * @return
	 * 		0:是女
	 * 		1：是男
	 */
	public static String getSexByIDCard(String idCard){
		idCard = MUtil.strObject(idCard);
		
		String result = null;
		
		//如果不是正确的身份证号，直接返回null
		if(!MUtil.toJudgeInput(idCard, 7)){
			return result;
		}
		
		if(idCard.length() == 15){  
			String sexFlag = idCard.substring(14, 15);  
			int sex = Integer.parseInt(sexFlag);
			if(sex % 2 != 0){
				result = "1";
			}else{
				result = "0";
			}
		}else if(idCard.length() == 18){  
			String sexFlag = idCard.substring(16, 17);  
			int sex = Integer.parseInt(sexFlag);
			if(sex % 2 != 0){
				result = "1";
			}else{
				result = "0";
			} 
		}
		return result;  
	}
	
	
	/**
	* 将utf-8编码转化为unicode编码
	* @param aByte byte[] -原utf-8编码字节数组
	* return sByte byte[] -转化后的unicode编码字节数组
	*/
	public static String changeUtf8ToUnicode(byte[] aByte) {
	   int sLength = aByte.length; //原字节数组长度
	   //存储转化为unicode编码后的StringBuffer字符串
	   StringBuffer sUnicodeStringBuffer = new StringBuffer();
	   char sChar; //用于临时存放每个从utf-8中解析出来的unicode编码
	   //以下操作是判断字节是否以"1110 xxxx 10xxxxxx 10xxxxxx"的形式出现
	   for (int i = 0; i < sLength; i++) { //循环每一个字节
	       if (i + 2 < sLength) {
	          /**
	           * aByte[i] & 0xF0 == 0xE0       ---> 判断当前字节是否以“1110”的形式开始；
	           * aByte[i + 1] & 0xC0 == 0x80   ---> 判断下一个字节是否以“10”的形式开始；
	           * aByte[i + 2] & 0xC0 == 0x80   ---> 判断下下一个字节是否以“10”的形式开始。
	           * 假如条件都满足，则表示此断字节进行了utf-8编码，则将对其进行解码操作（即转
	           * 化为unicode编码）
	           */
	          if ((aByte[i] & 0xF0) == 0xE0 && (aByte[i + 1] & 0xC0) == 0x80 &&
	              (aByte[i + 2] & 0xC0) == 0x80) {
	              /**
	               * 将当前字节 1110 xxxx 转化为 xxxx 000000 000000 的形式，具体步骤为：
	               * 1110 xxxx << 12 = xxxx 000000 000000
	               * 1110 0100 << 12 = 0100 000000 000000
	               */
	              sChar = (char) (aByte[i] << 12);
	              /**
	               * 将 前两个字节 转化为 xxxx xxxxxx 000000 的形式，具体步骤为：
	               * 10 xxxxxx & 0x003F = 0000 000000 xxxxxx
	               * 10 111000 & 0x003F = 0000 000000 111000
	               *
	               * 0000 000000 xxxxxx << 6 = 0000 xxxxxx 000000
	              * 0000 000000 111000 << 6 = 0000 111000 000000
	               *
	               * xxxx 000000 000000 | 0000 xxxxxx 000000 = xxxx xxxxxx 000000
	               * 0100 000000 000000 | 0000 111000 000000 = 0100 111000 000000
	               */
	              sChar = (char) ((((aByte[i + 1] & 0x003F) << 6) | sChar));
	              /**
	               * 将此三个字节转化为 xxxx xxxxxx xxxxxx 的形式，具体步骤为：
	               * 10 xxxxxx & 0x003F = 0000 0000 00 xxxxxx
	               * 10 101101 & 0x003F = 0000 0000 00 101101
	               *
	               * xxxx xxxxxx 000000 | 0000 000000 xxxxxx = xxxx xxxxxx xxxxxx
	               * 0100 111000 000000 | 0000 000000 101101 = 0100 111000 101101
	               */
	              sChar = (char) ((aByte[i + 2] & 0x003F) | sChar);
	               i = i + 2;
	               sUnicodeStringBuffer.append(sChar);
	           } else {
	               sUnicodeStringBuffer.append((char) aByte[i]);
	           }
	       }
	     }
	   return sUnicodeStringBuffer.toString();
	}
	
	 // 加密  
    public static String getBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);  
        }  
        return s;  
    }  
    public static List<String> convertStringToList(String content){
		List<String> result=null;
		if(Help.isNotNull(content)){
			result = new ArrayList<String>();
			if(content.indexOf("#")==-1){
				result.add(content);
			}else{
				String[] contentArr = MUtil.arrayClear(content.split("#"));
				for(String str:contentArr){
					result.add(str);
				}
		}
		}
		return result;
	}
    /**
	    * 按map 的key值从小到大排序
	    * */
	   public static Map sortMap(Map map) {  
	        ArrayList<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(map.entrySet());  
	        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {  

	            @Override  
	            public int compare(Entry<java.lang.String, String> arg0,  
	                    Entry<java.lang.String, String> arg1) {  
	            	
	                return Integer.parseInt(arg0.getKey()) - Integer.parseInt(arg1.getKey());  
	            }  
	        });  
	        Map newMap = new LinkedHashMap();  
	        out:for (int i = 0; i < list.size(); i++) {  
	            newMap.put(list.get(i).getKey(), list.get(i).getValue());  
	        }  
	        return newMap;  
	    }
	public static void main(String[] args)  {
//		System.out.println(toJudgeInput("12455554447", 2));
//		
//		System.out.println(MUtil.uuid());
		
		try {
			System.out.println(md5("vtimer"));
//			System.out.println("password=="+sign("chuangtouaccount=13701197921#type=0#appkey=9fegad45fec56scs"));
//			 System.out.println(PathCode.decrypt(Global.SECRET_KEY,URLDecoder.decode("CUBEoDINUtfBrl0vGZWsYw==", "utf-8")));
		} 
//		catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//String rs = getFromBase64("eyJjb21wYW55Ijoi5ZCM5rSyIiwiam9iIjoiaU9TIiwicmVhbG5hbWUiOiJKaWFuZ2xvbmciLCJ1c2VycG9ydHJhaXQiOiJodHRwOi8vMTEyLjc0LjY3LjIzOS9kb3dubG9hZEZpbGU/ZmlsZW5hbWVcdTAwM2QyNzEzODE3YjMyMDY0YjRjOGVkOTgxMWYwZTFkZWRhYy5qcGdcdTAwMjZmaWxldHlwZVx1MDAzZDAifQ==");
		//System.out.println(rs);
//		System.out.println(sign("IDNum=123123123123#accesstoken=0bfaf263c6e64ad3a1fe067465c9d5d2#appkey=bajcmdmiojtpijfc#authImg=xxxx#courseTypeName=公开课#intro=123123123123"));
	}

}