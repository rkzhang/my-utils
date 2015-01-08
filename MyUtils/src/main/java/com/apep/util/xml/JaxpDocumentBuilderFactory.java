package com.apep.util.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author rkzhang
 */
public class JaxpDocumentBuilderFactory {
	
	private static final Log logger = LogFactory.getLog(JaxpDocumentBuilderFactory.class);
	
	private JaxpDocumentBuilderFactory(){	
	}

	private static DocumentBuilder db;
	
	static{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		logger.debug("XmlParser Class is :" + dbf.getClass().getName());
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public static DocumentBuilder getDocumentBuilder(){
		return db;
	}
	
}
