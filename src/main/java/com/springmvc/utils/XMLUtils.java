package com.springmvc.utils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class XMLUtils {
	
	static Logger log = Logger.getLogger(XMLUtils.class);

	public static <T> T xml2Object(Class<T> cls, String doc) throws Exception {
		if (doc == null ||
				doc.equals("")) {
			return null;
		}

		Document  document = DocumentHelper.parseText(doc);;
		Element root = document.getRootElement();
		T obj = null;
		try {
			// new an instance for the class
			obj = (T) cls.newInstance();
			Method[] methods = cls.getMethods();
			for (Method m : methods) {
				String mName = m.getName();
				if (mName.startsWith("set")) {
					String field = mName.substring(3);
					@SuppressWarnings("rawtypes")
					Class[] paramTypes = m.getParameterTypes();
	                String parm = paramTypes[0].getName();

					@SuppressWarnings("unchecked")
					List<Element> elements = root.elements();
					if (elements.isEmpty()) {
						return null;
					}
					
					for (Element e : elements) {
						if (e.getName().equals(field)) {
							String text = e.getText();
							if (parm.equals("java.lang.Long")) {
								m.invoke(obj, Long.parseLong(text));
							}
							
							if (parm.equals("int")) {
								m.invoke(obj, Integer.parseInt(text));
							}
							
							if (parm.equals("java.lang.Double")) {
								m.invoke(obj, Double.parseDouble(text));
							}
							
							if (parm.equals("java.util.Date")) {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                m.invoke(obj, sdf.parse(text));
							}
							
							if (parm.equals("java.lang.String")) {
								m.invoke(obj, text);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		return (T) obj;
	}
}
