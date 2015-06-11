package com.apep.util;

import java.io.File;

public class FileUtil {
	
	public static String getFileName(String fileName){
		if(hasFileNameSuffix(fileName)){
			return fileName.substring(0, fileName.indexOf("."));

		}else{
			return fileName;
		}
		
	}
	
	public static String getFileSuffix(String fileName){
		if(hasFileNameSuffix(fileName)){
			return fileName.substring(fileName.indexOf(".") + 1);
		}else{
			return null;
		}
	}
	
	public static String[] getLeafFilePath(String parentPath){
		File f = new File(parentPath);
		File[] files = f.listFiles();
		String[] fileNames = new String[files.length];
		for(int i = 0; i < files.length; i++){
			String filepath = null;
			if(parentPath.endsWith("\\") || parentPath.endsWith("/")){
				filepath = parentPath + files[i].getName();
			}else{
				filepath = parentPath + "\\" + files[i].getName();
			}
			fileNames[i] = filepath;
		}
		return fileNames;
	}
	
	public static boolean hasFileNameSuffix(String fileName) {
		return fileName.contains(".");
	}

	public static void main(String[] args) {
		for(String filePath : getLeafFilePath("D:\\WorkSpace\\OilManagement\\WebContent\\WEB-INF\\lib")){
			System.out.println(filePath);
		}
	}
}
