package com.apep.util.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.apep.util.reflect.DynamicMethod;
import com.apep.util.reflect.ReflectStringUtil;
import com.apep.util.excel.annotations.ExcelColumn;

/**
 * @author rkzhang
 * @param <T>
 */
public class ExcelFile<T> {
		
	public ExcelFile(List<T> infoList){
		
		wb = new HSSFWorkbook();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dataMap.put(dateFormat.format(new Date()), infoList);
	}
	
	public ExcelFile(Map<String, List<T> > infoMap){
		
		wb = new HSSFWorkbook();
		dataMap = infoMap;
	}
	
	public ExcelFile(String sheetName, List<T> infoList){
		
		wb = new HSSFWorkbook();
		dataMap.put(sheetName, infoList);
	}
	
	private HSSFWorkbook wb;
	
	private String fileName;
	
	private Class entityClass;
	
	private Map<String, List<T>> dataMap = new TreeMap<String, List<T>>();
	
	public HSSFWorkbook getHSSFWorkbook(){
		int sheetIndex = 0;
		for(String sheetName : dataMap.keySet()){
			List infoList = dataMap.get(sheetName);
			createSheet(sheetIndex ++, wb, sheetName, infoList);
		}	
		return wb;
	}
	
	private Map<String, String> pasePropConfig(Class targetClass){	
		Map<String, String> propConfigMap = new TreeMap<String, String>();
		for(Field field : targetClass.getDeclaredFields()){
			if(field.getAnnotations().length > 0){
				ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
				if(excelColumn != null){
					propConfigMap.put(field.getName(), excelColumn.name());
				}
			}
		}	
		return propConfigMap;
	}
	
	private void initColumnIndexConfig(Class targetClass,Map<Integer, String> colnumMap, Map<String, Integer> colnumIndexMap ){	

		for(Field field : targetClass.getDeclaredFields()){
			if(field.getAnnotations().length > 0){
				ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
				if(excelColumn != null){
					colnumMap.put(excelColumn.index(), field.getName());
					colnumIndexMap.put(field.getName(), excelColumn.index());
				}
			}
		}	
	}
	
	private void createSheet(int sheetIndex, HSSFWorkbook wb, String sheetName, List infoList) {
		HSSFSheet sheet = wb.createSheet();
		wb.setSheetName(sheetIndex, sheetName);
		Class entityClas = null;
		if(infoList.size() > 0){
			entityClas = infoList.get(0).getClass();
		}else{
			entityClas = entityClass;
		}
		if(entityClas == null){
			return;
		}
		Map<String, String> propMap = pasePropConfig(entityClas);	
		Map<Integer, String> colnumMap = new HashMap<Integer, String>();
		Map<String, Integer> colnumIndexMap = new HashMap<String, Integer>();
		
		initColumnIndexConfig(entityClas, colnumMap, colnumIndexMap);
		
		Integer colNum = 0;
		HSSFRow rowTitle = sheet.createRow((short) 0);
		for(String fieldName : propMap.keySet()){
			
			colNum = colnumIndexMap.get(fieldName);
			if(colNum != null){
				HSSFCell cellTitle = rowTitle.createCell((short) colNum.intValue());
				//cellTitle.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellTitle.setCellStyle(createTitleStyle(wb));
				cellTitle.setCellValue(propMap.get(fieldName));
			}
			//colnumMap.put(colNum++, fieldName);	
		}
		if(infoList == null || infoList.size() == 0){
			return;
		}
		
		Object[] dataArray = infoList.toArray();
		for(int rowNum = 1; rowNum <= dataArray.length; rowNum++){
			HSSFRow dataRow = sheet.createRow((short) rowNum);
			Object targetObject = dataArray[rowNum - 1];
			
			for(Integer col : colnumMap.keySet()){
				String fieldName = colnumMap.get(col);
				
				HSSFCell dataCell = dataRow.createCell((short) col.intValue());
				//dataCell.setEncoding(HSSFCell.ENCODING_UTF_16);
				//dataCell.setCellStyle(createDateStyle(wb));
				Object value = getFieldValue(targetObject, fieldName);
				if(value != null){
					dataCell.setCellValue(value.toString());
				}
			}	
		}
		
	}

	private Object getFieldValue(Object targetObject, String fieldName) {
		String getMethodName = ReflectStringUtil.populateGetMethodName(fieldName);
		if(!DynamicMethod.containsMethod(targetObject, getMethodName)){
			return null;
		}
		return DynamicMethod.invokeMethod(targetObject, getMethodName);
	}
	
	/**
	 * 创建数据样式
	 * @param wb
	 * @return
	 */
	private HSSFCellStyle createDateStyle(HSSFWorkbook wb){
		HSSFCellStyle cellstyle = wb.createCellStyle();
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直   
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平   
		HSSFFont font = wb.createFont();
		font.setCharSet(HSSFCell.ENCODING_UTF_16);
		cellstyle.setFont(font);
		return cellstyle;
	}
	
	/**
	 * 创建标题样式
	 * @param wb
	 * @return
	 */
	public HSSFCellStyle createTitleStyle(HSSFWorkbook wb){
		HSSFCellStyle cellstyle = wb.createCellStyle();
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直   
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平   
		HSSFFont font = wb.createFont();
		font.setCharSet(HSSFCell.ENCODING_UTF_16);
		cellstyle.setFont(font);
		return cellstyle;
	}
	
	public InputStream getExcelInputStream(){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			getHSSFWorkbook().write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStream excelStream = new ByteArrayInputStream(out.toByteArray());
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return excelStream;
	}

	public Class getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}
	
	
} 
