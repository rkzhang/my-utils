package com.apep.util.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author rkzhang
 */
public abstract class TransformUtil {
	
	/**
	 * @param doc
	 * @return
	 */
	public static String transform(Document doc){
		StringWriter out = null;
		try {
		 TransformerFactory tFactory = TransformerFactory.newInstance();
         Transformer transformer = tFactory.newTransformer();	
         DOMSource source = new DOMSource(doc);
         out = new StringWriter();
         StreamResult result = new StreamResult(out);   
         transformer.setOutputProperty("encoding", "UTF-8");
         transformer.transform(source, result);
         out.flush();
         return out.toString();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * @param xmlStr
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document transform(String xmlStr) throws SAXException, IOException{
		Document doc = null;
		ByteArrayInputStream bi = null;
		try{		
			bi = new ByteArrayInputStream(xmlStr.getBytes("utf-8"));	
			doc = JaxpDocumentBuilderFactory.getDocumentBuilder().parse(bi);				
		}finally{
			if(bi != null){
				try {
					bi.close();
					bi = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return doc;
	}
	
	/**
	 * @param nodeList
	 * @return
	 */
	public static String getChildTextValue(NodeList nodeList){
		if(nodeList != null && nodeList.getLength() > 0 && nodeList.item(0).hasChildNodes()){
			return nodeList.item(0).getChildNodes().item(0).getNodeValue();
		}
		return null;
	}
}

