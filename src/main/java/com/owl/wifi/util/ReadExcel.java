package com.owl.wifi.util;



public class ReadExcel {/*
	
    
    public static List<UserAccount> readExcel(InputStream inputStream,String fileName) throws IOException{
		   System.out.println("fileName==="+fileName);
		   String extension = fileName.lastIndexOf(".")==-1?"":fileName.substring(fileName.lastIndexOf(".")+1);
		   if("xls".equals(extension)){
		       return read2003Excel(inputStream);
		   }else if("xlsx".equals(extension)){
		       return read2007Excel(inputStream);
		   }else{
		       throw new IOException("不支持的文件类型");
		   }
	}
	
    private static List<UserAccount> read2003Excel(InputStream inputStream)
            throws IOException {
        List<UserAccount> list = new ArrayList<UserAccount>();
        HSSFWorkbook hwb = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = hwb.getSheetAt(0);
        Object value = null;
        HSSFRow row = null;
        
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            if (row.getPhysicalNumberOfCells() > 6) {
                continue;
            }
            
            UserAccount userAccount = new UserAccount();
            
            HSSFCell account = row.getCell(0);
            if (account != null) {
                value = get2003Value(account);
                userAccount.setCUserAccounts(value+"");
            }
            HSSFCell userName = row.getCell(1);
            if (userName != null) {
                value = get2003Value(userName);
                userAccount.setCUserName(userName+"");
            }
            HSSFCell sex = row.getCell(2);
            
            if (sex != null) {
                value = get2003Value(sex);
                if("男".equals(value.toString().trim())){
                	userAccount.setUserSex("0");
                }else if("女".equals(value.toString().trim())){
                	userAccount.setUserSex("1");
                }else{
                	userAccount.setUserSex("-1");
                }
            }
            HSSFCell password = row.getCell(3);
            
            if (password != null) {
                value = get2003Value(sex);
               
                	userAccount.setCUserPassword(MUtil.md5(value+""));
                
            }else{
            	userAccount.setCUserPassword(MUtil.md5("123456"));//默认密码
            }
            list.add(userAccount);
        }
        
        return list;
    }
	
	private static List<UserAccount> read2007Excel(InputStream inputStream) throws IOException {

		   List<UserAccount> list = new LinkedList<UserAccount>();
		   // 构造 XSSFWorkbook 对象，strPath 传入文件路径
		   XSSFWorkbook xwb = new XSSFWorkbook(inputStream);
		   // 读取第一章表格内容
		   XSSFSheet sheet = xwb.getSheetAt(0);
		   Object value = null;
		   XSSFRow row = null;
		   for (int i = 1; i <= sheet.getLastRowNum(); i++) {
    		    row = sheet.getRow(i);
    		    if (row == null) {
    		     continue;
    		    }
    		   
    		    if(row.getPhysicalNumberOfCells()>7){
    		    	continue;
    		    }
		    
    		    UserAccount userAccount = new UserAccount();
                
                XSSFCell account = row.getCell(0);
                if (account != null) {
                    value = get2007Value(account);
                    userAccount.setCUserAccounts(value+"");
                }
                XSSFCell userName = row.getCell(1);
                if (userName != null) {
                    value = get2007Value(userName);
                    userAccount.setCUserName(userName+"");
                }
                XSSFCell sex = row.getCell(2);
                
                if (sex != null) {
                    value = get2007Value(sex);
                    if("男".equals(value.toString().trim())){
                    	userAccount.setUserSex("0");
                    }else if("女".equals(value.toString().trim())){
                    	userAccount.setUserSex("1");
                    }else{
                    	userAccount.setUserSex("-1");
                    }
                }
                XSSFCell password = row.getCell(3);
                
                if (password != null) {
                    value = get2007Value(sex);
                   
                    	userAccount.setCUserPassword(MUtil.md5(value+""));
                    
                }else{
                	userAccount.setCUserPassword(MUtil.md5("123456"));//默认密码
                }
                list.add(userAccount);
		   }
		   return list;
		}
	
	
	public static List<Product> readProductExcel(InputStream inputStream,String fileName) throws IOException{
		   System.out.println("fileName==="+fileName);
		   String extension = fileName.lastIndexOf(".")==-1?"":fileName.substring(fileName.lastIndexOf(".")+1);
		   if("xls".equals(extension)){
		       return read2003PExcel(inputStream);
		   }else if("xlsx".equals(extension)){
		       return read2007PExcel(inputStream);
		   }else{
		       throw new IOException("不支持的文件类型");
		   }
	}
	
 private static List<Product> read2003PExcel(InputStream inputStream)
         throws IOException {
     List<Product> list = new ArrayList<Product>();
     HSSFWorkbook hwb = new HSSFWorkbook(inputStream);
     HSSFSheet sheet = hwb.getSheetAt(0);
     Object value = null;
     HSSFRow row = null;
     
     for (int i = 1; i <= sheet.getLastRowNum(); i++) {
         row = sheet.getRow(i);
         if (row == null) {
             continue;
         }
         if (row.getPhysicalNumberOfCells() > 11) {
             continue;
         }
         
         Product product = new Product();
         
         HSSFCell PCode = row.getCell(0);
         if (PCode != null) {
             value = get2003Value(PCode);
             product.setPCode(value+"");
         }
         HSSFCell pName = row.getCell(1);
         if (pName != null) {
             value = get2003Value(pName);
             product.setPName(value+"");
         }
         HSSFCell PCategoryName = row.getCell(2);
         if (PCategoryName != null) {
             value = get2003Value(PCategoryName);
             if("ZANDER".equals(value.toString().trim())){
            	 product.setPCategoryId("31");
             }else if("RIESE".equals(value.toString().trim())){
            	 product.setPCategoryId("32");
             }else if("HESI".equals(value.toString().trim())){
            	 product.setPCategoryId("33");
             }else if("Controlway".equals(value.toString().trim())){
            	 product.setPCategoryId("34");
             }else if("REER".equals(value.toString().trim())){
            	 product.setPCategoryId("35");
             }else if("Leuze".equals(value.toString().trim())){
            	 product.setPCategoryId("36");
             }else if("microsonic".equals(value.toString().trim())){
            	 product.setPCategoryId("37");
             }else if("COMEPI".equals(value.toString().trim())){
            	 product.setPCategoryId("39");
             }else if("ERSCE".equals(value.toString().trim())){
            	 product.setPCategoryId("40");
             }else if("PIZZATO".equals(value.toString().trim())){
            	 product.setPCategoryId("41");
             }else if("BBH".equals(value.toString().trim())){
            	 product.setPCategoryId("42");
             }else if("AECO".equals(value.toString().trim())){
            	 product.setPCategoryId("43");
             }else if("ITALSENSOR".equals(value.toString().trim())){
            	 product.setPCategoryId("44");
             }else if("MAYSER".equals(value.toString().trim())){
            	 product.setPCategoryId("45");
             }else if("NUOVACEVA".equals(value.toString().trim())){
            	 product.setPCategoryId("46");
             }else if("ROSE".equals(value.toString().trim())){
            	 product.setPCategoryId("47");
             }else if("COMEPI".equals(value.toString().trim())){
            	 product.setPCategoryId("48");
             }else if("GIOVENZANA".equals(value.toString().trim())){
            	 product.setPCategoryId("49");
             }
         }
        
         HSSFCell PModel = row.getCell(3);         
         if (PModel != null) {
             value = get2003Value(PModel);            
             product.setPModel(value+"");          
         }
         
         HSSFCell PSpecification = row.getCell(4);         
         if (PSpecification != null) {
             value = get2003Value(PSpecification);            
             product.setPModel(value+"");          
         }
         
         HSSFCell PSellPrice = row.getCell(5);         
         if (PSellPrice != null) {
             value = get2003Value(PSellPrice);            
             product.setPSellPrice(value+"");          
         }
         
         HSSFCell PCostPrice = row.getCell(6);         
         if (PCostPrice != null) {
             value = get2003Value(PCostPrice);            
             product.setPCostPrice(value+"");          
         }
         
         HSSFCell whName = row.getCell(7);         
         if (whName != null) {
        	 if("成品库".equals(value.toString().trim())){
            	 product.setWarehouseId("cc5d1da1d0974ca2a1304b723a9854eb");
             }else if("样品库".equals(value.toString().trim())){
            	 product.setWarehouseId("5962384b030a4e77bc611beabb736f5e");
             }         
         }
        
         product.setpInventory("0");
         
         HSSFCell PUnit = row.getCell(9);         
         if (PUnit != null) {
             value = get2003Value(PUnit);            
             product.setPUnit(value+"");          
         }
         list.add(product);
     }
     
     return list;
 }
	
	private static List<Product> read2007PExcel(InputStream inputStream) throws IOException {

		   List<Product> list = new LinkedList<Product>();
		   // 构造 XSSFWorkbook 对象，strPath 传入文件路径
		   XSSFWorkbook xwb = new XSSFWorkbook(inputStream);
		   // 读取第一章表格内容
		   XSSFSheet sheet = xwb.getSheetAt(0);
		   Object value = null;
		   XSSFRow row = null;
		   for (int i = 1; i <= sheet.getLastRowNum(); i++) {
		         row = sheet.getRow(i);
		         if (row == null) {
		             continue;
		         }
		         if (row.getPhysicalNumberOfCells() > 11) {
		             continue;
		         }
		         
		         Product product = new Product();
		         
		         XSSFCell PCode = row.getCell(0);
		         if (PCode != null) {
		             value = get2007Value(PCode);
		             product.setPCode(value+"");
		         }
		         XSSFCell pName = row.getCell(1);
		         if (pName != null) {
		             value = get2007Value(pName);
		             product.setPName(value+"");
		         }
		         XSSFCell PCategoryName = row.getCell(2);
		         if (PCategoryName != null) {
		             value = get2007Value(PCategoryName);
		             if("ZANDER".equals(value.toString().trim())){
		            	 product.setPCategoryId("31");
		             }else if("RIESE".equals(value.toString().trim())){
		            	 product.setPCategoryId("32");
		             }else if("HESI".equals(value.toString().trim())){
		            	 product.setPCategoryId("33");
		             }else if("Controlway".equals(value.toString().trim())){
		            	 product.setPCategoryId("34");
		             }else if("REER".equals(value.toString().trim())){
		            	 product.setPCategoryId("35");
		             }else if("Leuze".equals(value.toString().trim())){
		            	 product.setPCategoryId("36");
		             }else if("microsonic".equals(value.toString().trim())){
		            	 product.setPCategoryId("37");
		             }else if("COMEPI".equals(value.toString().trim())){
		            	 product.setPCategoryId("39");
		             }else if("ERSCE".equals(value.toString().trim())){
		            	 product.setPCategoryId("40");
		             }else if("PIZZATO".equals(value.toString().trim())){
		            	 product.setPCategoryId("41");
		             }else if("BBH".equals(value.toString().trim())){
		            	 product.setPCategoryId("42");
		             }else if("AECO".equals(value.toString().trim())){
		            	 product.setPCategoryId("43");
		             }else if("ITALSENSOR".equals(value.toString().trim())){
		            	 product.setPCategoryId("44");
		             }else if("MAYSER".equals(value.toString().trim())){
		            	 product.setPCategoryId("45");
		             }else if("NUOVACEVA".equals(value.toString().trim())){
		            	 product.setPCategoryId("46");
		             }else if("ROSE".equals(value.toString().trim())){
		            	 product.setPCategoryId("47");
		             }else if("COMEPI".equals(value.toString().trim())){
		            	 product.setPCategoryId("48");
		             }else if("GIOVENZANA".equals(value.toString().trim())){
		            	 product.setPCategoryId("49");
		             }
		         }
		        
		         XSSFCell PModel = row.getCell(3);         
		         if (PModel != null) {
		             value = get2007Value(PModel);            
		             product.setPModel(value+"");          
		         }
		         
		         XSSFCell PSpecification = row.getCell(4);         
		         if (PSpecification != null) {
		             value = get2007Value(PSpecification);            
		             product.setPSpecification(value+"");          
		         }
		         
		         XSSFCell PSellPrice = row.getCell(5);         
		         if (PSellPrice != null) {
		             value = get2007Value(PSellPrice);            
		             product.setPSellPrice(value+"");          
		         }
		         
		         XSSFCell PCostPrice = row.getCell(6);         
		         if (PCostPrice != null) {
		             value = get2007Value(PCostPrice);            
		             product.setPCostPrice(value+"");          
		         }
		         
		         XSSFCell whName = row.getCell(7);         
		         if (whName != null) {
		        	 if("成品库".equals(whName.toString().trim())){
		            	 product.setWarehouseId("cc5d1da1d0974ca2a1304b723a9854eb");
		             }else if("样品库".equals(whName.toString().trim())){
		            	 product.setWarehouseId("5962384b030a4e77bc611beabb736f5e");
		             }         
		         }
		        
		         product.setpInventory("0");
		         
		         XSSFCell PUnit = row.getCell(9);         
		         if (PUnit != null) {
		             value = get2007Value(PUnit);            
		             product.setPUnit(value+"");          
		         }
		         list.add(product);
		     }
		   return list;
		}
	

		public static Object get2003Value(HSSFCell cell){
			Object value;
			DecimalFormat df = new DecimalFormat("0");// 格式化 number String 字符
		     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
		     DecimalFormat nf = new DecimalFormat("0");// 格式化数字
		     switch (cell.getCellType()) {
		     case XSSFCell.CELL_TYPE_STRING:
		//      System.out.println(i+"行"+j+" 列 is String type");
		      value = cell.getStringCellValue();
		      break;
		     case XSSFCell.CELL_TYPE_NUMERIC:
		//      System.out.println(i+"行"+j+" 列 is Number type ; DateFormt:"+cell.getCellStyle().getDataFormatString());
		      if("@".equals(cell.getCellStyle().getDataFormatString())){
		         value = df.format(cell.getNumericCellValue());
		      } else if("General".equals(cell.getCellStyle().getDataFormatString())){
		         value = nf.format(cell.getNumericCellValue());
		      }else{
		        value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
		      }
		      break;
		     case XSSFCell.CELL_TYPE_BOOLEAN:
		//      System.out.println(i+"行"+j+" 列 is Boolean type");
		      value = cell.getBooleanCellValue();
		      break;
		     case XSSFCell.CELL_TYPE_BLANK:
		//      System.out.println(i+"行"+j+" 列 is Blank type");
		      value = "";
		      break;
		     default:
		//      System.out.println(i+"行"+j+" 列 is default type");
		      value = cell.toString();
		     }
			return value;
		}
  public static Object get2007Value(XSSFCell cell){
	  Object value;
	  DecimalFormat df = new DecimalFormat("0");// 格式化 number String 字符
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
	     DecimalFormat nf = new DecimalFormat("0");// 格式化数字

	     switch (cell.getCellType()) {
	     case XSSFCell.CELL_TYPE_STRING:
//	      System.out.println(i+"行"+j+" 列 is String type");
	      value = cell.getStringCellValue();
	      break;
	     case XSSFCell.CELL_TYPE_NUMERIC:
//	      System.out.println(i+"行"+j+" 列 is Number type ; DateFormt:"+cell.getCellStyle().getDataFormatString());
	      if("@".equals(cell.getCellStyle().getDataFormatString())){
	        value = df.format(cell.getNumericCellValue());
	      } else if("General".equals(cell.getCellStyle().getDataFormatString())){
	        value = nf.format(cell.getNumericCellValue());
	      }else{
	       value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
	      }
	      break;
	     case XSSFCell.CELL_TYPE_BOOLEAN:
//	      System.out.println(i+"行"+j+" 列 is Boolean type");
	      value = cell.getBooleanCellValue();
	      break;
	     case XSSFCell.CELL_TYPE_BLANK:
//	      System.out.println(i+"行"+j+" 列 is Blank type");
	      value = "";
	      break;
	     default:
//	      System.out.println(i+"行"+j+" 列 is default type");
	      value = cell.toString();
	     }
	  return value;
  }
  public static void main(String[]args){
	  File file1 = new File("D:\\testFolder\\model.xlsx");
	 
	  try {
		long start = System.currentTimeMillis();
	//	System.out.println(read2003Excel(file1));
	
//		System.out.println(readExcel(file1));
		//System.out.println(readExcel2(file1));
		
		long end = System.currentTimeMillis();
		System.out.println(end-start);
		//read2007Excel(file2);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
  }

*/}
