package com.owl.wifi.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public final class FileUtil {
	
	private static final Logger log= LoggerFactory.getLogger(FileUtil.class);
	
	
	private int fileSize = 0;					//文件大小单位字节
	private String filePath = null;				//文件服务器路径
	private String lastFileName = null;				//文件名
	/**
	 * 保存上传的文件到本地
	 * @param file
	 * @return
	 * 		保存后的路径
	 * @throws IOException 
	 */
	public void saveFileToTemp(InputStream is, String fileName,String uploadPath) {
		
		OutputStream os = null;
		
		try {
			File file= new File(uploadPath);
			if(!file.exists()){
				file.mkdirs();
			}
			String suffixStr = fileName.substring(fileName.lastIndexOf("."));
			String uuid=UUID.randomUUID().toString().replace("-", "");
			String newFileName  = uuid + suffixStr;
			
			
			
			// 设置目标文件
			File toFile = new File(uploadPath, newFileName);
		//	 System.out.println("*******************newFileName:"+newFileName+"**********");  
			// 创建一个输出流
			os = new FileOutputStream(toFile);
			
			int length = 0;
			// 设置缓存
			byte[] buffer = new byte[1024];
			// 读取myFile文件输出到toFile文件中
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
				fileSize += length;
			}
			//图片压缩
			//String suffix=suffixStr.substring(1);
			//boolean b=false;
			/*if(fileType!=null&&"topic".equals(fileType)){
			
			 b=ImageUtil.transferImg(toFile, uploadPath+File.separator, uuid, suffix, Integer.parseInt(Global.TOPIC_WIDTH), Integer.parseInt(Global.TOPIC_HEIGHT),false);
			
			}
			if(fileType!=null&&"infomation".equals(fileType)){
				
			 b=ImageUtil.transferImg(toFile, uploadPath+File.separator, uuid, suffix, Integer.parseInt(Global.INFO_WIDTH), Integer.parseInt(Global.INFO_HEIGHT),false);
				
			}*/
			filePath = toFile.getPath();
			lastFileName=newFileName;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.equals(e.getMessage());
		} catch (IOException e) {
			log.equals(e.getMessage());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					is = null;
				}
			}
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {
					os = null;
				}
			}
		}
		
	} 
	public int getFileSize() {
		return fileSize;
	}

	public String getFilePath() {
		return filePath;
	}
	public String getLastFileName() {
		return lastFileName;
	}

}