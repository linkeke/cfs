package com.owl.wifi.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Serialize {
	
	public static String toJosnDate(Object object){
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		objectMapper.setDateFormat(dateFormat);
        String result = "";
        try{
        	result = objectMapper.writeValueAsString(object);
        }catch(JsonProcessingException e){
        	e.printStackTrace();
        }
		return result;
	}
	
	public static Result parseJson(String jsonStr){
		Result result = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			result = objectMapper.readValue(jsonStr.trim(), Result.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseJsonToMap(String jsonStr){
		Map<String, Object> result = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			result = objectMapper.readValue(jsonStr.trim(), Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
}
