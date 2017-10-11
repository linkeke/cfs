package com.owl.wifi.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: ��ʼ�����дʿ⣬�����дʼ��뵽HashMap�У�����DFA�㷨ģ��
 * @Project��test
 * @Author : chenming
 * @Date �� 2014��4��20�� ����2:27:06
 * @version 1.0
 */
public class SensitiveWordInit {
	private String ENCODING = "GBK";    //�ַ����
	@SuppressWarnings("rawtypes")
	public HashMap sensitiveWordMap;
	
	public SensitiveWordInit(){
		super();
	}
	
	/**
	 * @author chenming 
	 * @date 2014��4��20�� ����2:28:32
	 * @version 1.0
	 */
	@SuppressWarnings("rawtypes")
	public Map initKeyWord(){
		try {
			//��ȡ���дʿ�
			Set<String> keyWordSet = readSensitiveWordFile();
			//�����дʿ���뵽HashMap��
			addSensitiveWordToHashMap(keyWordSet);
			//spring��ȡapplication��Ȼ��application.setAttribute("sensitiveWordMap",sensitiveWordMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sensitiveWordMap;
	}
	@SuppressWarnings("rawtypes")
	public Map initKeyWord(List<String> words){
		try {
			//��ȡ���дʿ�
			Set<String> keyWordSet = readSensitiveWord(words);
			//�����дʿ���뵽HashMap��
			addSensitiveWordToHashMap(keyWordSet);
			//spring��ȡapplication��Ȼ��application.setAttribute("sensitiveWordMap",sensitiveWordMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sensitiveWordMap;
	}

	/**
	 * ��ȡ���дʿ⣬�����дʷ���HashSet�У�����һ��DFA�㷨ģ�ͣ�<br>
	 * �� = {
	 *      isEnd = 0
	 *      �� = {<br>
	 *      	 isEnd = 1
	 *           �� = {isEnd = 0
	 *                �� = {isEnd = 1}
	 *                }
	 *           ��  = {
	 *           	   isEnd = 0
	 *           		�� = {
	 *           			 isEnd = 1
	 *           			}
	 *           	}
	 *           }
	 *      }
	 *  �� = {
	 *      isEnd = 0
	 *      �� = {
	 *      	isEnd = 0
	 *      	�� = {
	 *              isEnd = 0
	 *              �� = {
	 *                   isEnd = 1
	 *                  }
	 *              }
	 *      	}
	 *      }
	 * @author chenming 
	 * @date 2014��4��20�� ����3:04:20
	 * @param keyWordSet  ���дʿ�
	 * @version 1.0
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
		sensitiveWordMap = new HashMap(keyWordSet.size());     //��ʼ�����д��������������ݲ���
		String key = null;  
		Map nowMap = null;
		Map<String, String> newWorMap = null;
		//���keyWordSet
		Iterator<String> iterator = keyWordSet.iterator();
		while(iterator.hasNext()){
			key = iterator.next();    //�ؼ���
			nowMap = sensitiveWordMap;
			for(int i = 0 ; i < key.length() ; i++){
				char keyChar = key.charAt(i);       //ת����char��
				Object wordMap = nowMap.get(keyChar);       //��ȡ
				
				if(wordMap != null){        //�����ڸ�key��ֱ�Ӹ�ֵ
					nowMap = (Map) wordMap;
				}
				else{     //���������򹹽�һ��map��ͬʱ��isEnd����Ϊ0����Ϊ�������һ��
					newWorMap = new HashMap<String,String>();
					newWorMap.put("isEnd", "0");     //�������һ��
					nowMap.put(keyChar, newWorMap);
					nowMap = newWorMap;
				}
				
				if(i == key.length() - 1){
					nowMap.put("isEnd", "1");    //���һ��
				}
			}
		}
	}

	/**
	 * ��ȡ���дʿ��е����ݣ���������ӵ�set������
	 * @author chenming 
	 * @date 2014��4��20�� ����2:31:18
	 * @return
	 * @version 1.0
	 * @throws Exception 
	 */
	@SuppressWarnings("resource")
	private Set<String> readSensitiveWordFile() throws Exception{
		Set<String> set = null;
		
		File file = new File("D:\\SensitiveWord.txt");    //��ȡ�ļ�
		InputStreamReader read = new InputStreamReader(new FileInputStream(file),ENCODING);
		try {
			if(file.isFile() && file.exists()){      //�ļ����Ƿ����
				set = new HashSet<String>();
				BufferedReader bufferedReader = new BufferedReader(read);
				String txt = null;
				while((txt = bufferedReader.readLine()) != null){    //��ȡ�ļ������ļ����ݷ��뵽set��
					set.add(txt);
			    }
			}
			else{         //�������׳��쳣��Ϣ
				throw new Exception("���дʿ��ļ�������");
			}
		} catch (Exception e) {
			throw e;
		}finally{
			read.close();     //�ر��ļ���
		}
		return set;
	}
	private Set<String> readSensitiveWord(List<String> words) throws Exception{
		Set<String> set = null;
		
		if(words!=null&&words.size()>0){
			set = new HashSet<String>();
			for(String word:words){
				set.add(word);
			}
		}
		
		return set;
	}
}
