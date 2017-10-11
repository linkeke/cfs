package com.owl.wifi.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.owl.wifi.util.Help;
import com.owl.wifi.util.MUtil;
import com.owl.wifi.util.Serialize;


public class BKLogger{
	
	private static final Logger log= LoggerFactory.getLogger(Help.class);
	
	private BKLogger(){}
 
	@SuppressWarnings("rawtypes")
	public static BKLogger getLogger(Class clz){
		BKLogger bkLogger = new BKLogger();
		return bkLogger;
	}
	
	public void debug(String message){
		log.debug(message);
	}
	
	public void info(String message){
		log.info(message);
	}
	
	public void warn(String message){
		log.warn(message);
	}
	
	public void error(String message){
		log.error(message);
	}
	
	/**
	 * 包装日志debug方法
	 * 		加上自定义标题输入，
	 * 		计算文本大小，以k为单位
	 * 		序列话化内容并返回
	 * @param title
	 * @param message
	 * @return
	 */
	public String debugbk(String title, Object object){
		String content = Serialize.toJosnDate(object);
		
		StringBuffer infosb = new StringBuffer();
		infosb.append("[");
		infosb.append(MUtil.strObject(title));
		infosb.append("][数据大小为:(");
		infosb.append(content.getBytes().length/1024.00  + ")k][");
		infosb.append(content);
		infosb.append("]");
		
		debug(infosb.toString());
		
		return content;
		
	}
	
	/**
	 * 包装日志info方法
	 * 		加上自定义标题输入，
	 * 		计算文本大小，以k为单位
	 * 		序列话化内容并返回
	 * @param title
	 * @param message
	 * @return
	 */
	public String infobk(String title, Object object){
		String content = Serialize.toJosnDate(object);
		
		StringBuffer infosb = new StringBuffer();
		infosb.append("[");
		infosb.append(MUtil.strObject(title));
		infosb.append("][数据大小为:(");
		infosb.append(content.getBytes().length/1024.00  + ")k][");
		infosb.append(content);
		infosb.append("]");
		
		info(infosb.toString());
		
		return content;
		
	}
	
	/**
	 * 包装日志warn方法
	 * 		加上自定义标题输入，
	 * 		计算文本大小，以k为单位
	 * 		序列话化内容并返回
	 * @param title
	 * @param message
	 * @return
	 */
	public String warnbk(String title, Object object){
		String content = Serialize.toJosnDate(object);
		
		StringBuffer infosb = new StringBuffer();
		infosb.append("[");
		infosb.append(MUtil.strObject(title));
		infosb.append("][数据大小为:(");
		infosb.append(content.getBytes().length/1024.00  + ")k][");
		infosb.append(content);
		infosb.append("]");
		
		warn(infosb.toString());
		
		return content;
	}
	
	/**
	 * 包装日志error方法
	 * 		加上自定义标题输入，
	 * 		计算文本大小，以k为单位
	 * 		序列话化内容并返回
	 * @param title
	 * @param message
	 * @return
	 */
	public String errorbk(String title, Object object){
		String content = Serialize.toJosnDate(object);
		
		StringBuffer infosb = new StringBuffer();
		infosb.append("[");
		infosb.append(MUtil.strObject(title));
		infosb.append("][数据大小为:(");
		infosb.append(content.getBytes().length/1024.00  + ")k][");
		infosb.append(content);
		infosb.append("]");
		
		error(infosb.toString());
		
		return content;
	}
	
	/**
	 * 是否有DEBUG级别
	 * @return true/false
	 */
	//*************************************************************************
	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}

}
