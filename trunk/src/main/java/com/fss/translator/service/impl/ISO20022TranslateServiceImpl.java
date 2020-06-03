package com.fss.translator.service.impl;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.fss.translator.constants.ResponseMessages;
import com.fss.translator.constants.TranslatorConstants;
import com.fss.translator.dto.ValueDTO;
import com.fss.translator.exception.ServiceException;
import com.fss.translator.resource.TranslatorResources;
import com.fss.translator.service.ISO20022TranslateService;
import com.fss.translator.util.Util;

@Service
public class ISO20022TranslateServiceImpl implements ISO20022TranslateService{

	@Autowired
	TranslatorResources translatorResources;

	private static final Logger logger = LogManager.getLogger(ISO20022TranslateServiceImpl.class);


	@Override
	public void doMarshalling(ValueDTO value) throws ServiceException {

		logger.debug(TranslatorConstants.ENTER);
		String rrn=value.getRequestObject().get(TranslatorConstants.RRN)+"";
		Map<String,Object> transferObject=value.getTransferObject();
		if(Util.isNotNull(transferObject) && !transferObject.isEmpty() && Util.isNotNull(value.getRequestObject()) )
			transferObject.put(TranslatorConstants.RRN, !Util.isEmpty(rrn)?value.getRequestObject().get(TranslatorConstants.RRN):"");
		Map<String,String> instConfig=value.getInstituation();
		String sourceName="";
		if(Util.isNotNull(instConfig) && !instConfig.isEmpty())
			sourceName=instConfig.get(TranslatorConstants.TARGETRESPONSEFORMATOPT);
		Map<String,String> prop=translatorResources.getISO20022propertymapping(sourceName);
		Map<String,String> mappingClass=translatorResources.getISO20022XSDmapping(sourceName);
		if(Util.isNotNull(prop) && !prop.isEmpty() && Util.isNotNull(mappingClass) && !mappingClass.isEmpty()){
			Long startTime=System.currentTimeMillis();
			Object obj=getObject(prop,mappingClass,transferObject,null,sourceName);
			getObject(prop,mappingClass,transferObject,obj,sourceName);
			processofISO20022Marshalling(value, obj, sourceName);
			Long endTime=System.currentTimeMillis();
			logger.info("Object completion Time "+(endTime-startTime));
		}else{
			logger.info("Conguration was not done sourceName:"+sourceName);
			throw new ServiceException("", ResponseMessages.INAVALID_INVALID_REQUEST_PARSE);
		}
		logger.debug(TranslatorConstants.EXIT);
	}

	private void processofISO20022Marshalling(ValueDTO valueDto,Object obj,String sourceName) throws ServiceException {
		try {
			
			logger.debug(TranslatorConstants.ENTER);
			Long startTime=System.currentTimeMillis();
			JAXBContext jc = JAXBContext.newInstance(getSource(sourceName,false));
			Marshaller jaxbMarshaller  = jc.createMarshaller();
			@SuppressWarnings("unchecked")
			JAXBElement<Object> jaxbElement =
			new JAXBElement<Object>( new QName("", obj.getClass().getSimpleName()),
					(Class<Object>) obj.getClass(),obj);
			StringWriter writer = new StringWriter();
			jaxbMarshaller.marshal(jaxbElement, writer);
			String formatedXmlData = writer.toString();
			logger.info("XML"+formatedXmlData);
			valueDto.setResponseData(formatedXmlData);
			Long endTime=System.currentTimeMillis();
			logger.info("processofISO20022Marshalling Compplition Time "+(endTime-startTime));
			logger.debug(TranslatorConstants.EXIT);

		} catch (Exception e) {

			throw new ServiceException("", ResponseMessages.INAVALID_INVALID_REQUEST_PARSE);
		}finally{
			obj=null;
		}

	}

	@Override
	public void doUnmarshalling(ValueDTO value) throws ServiceException {
	}

	private  String gettingValueBasedOnProp(String innerVal,Map<String, Object> reqValueObj) {

		String[] totalValues = innerVal.split("\\|");

		//StringBuilder finalValue = "";
		StringBuilder finalValue = new StringBuilder();
		for (String k : totalValues) {
			if (k.contains(",") && k.contains("(")) {
				String val = k.substring(0, k.length() - 5);
				Object innerValue = reqValueObj.get(val);
				String val1 = innerValue.toString().substring(
						Character.getNumericValue(k.charAt(k.length() - 4)),
						Character.getNumericValue(k.charAt(k.length() - 2)));
				//finalValue = finalValue + val1;
				finalValue.append(val1);

			} else if (!k.contains(",") && k.contains("(")) {
				String val = k.substring(0, k.length() - 3);
				Object innerValue = reqValueObj.get(val);
				String val1 = innerValue.toString()
						.substring(Character.getNumericValue(k.charAt(k.length() - 2)));
				//finalValue = finalValue + val1;
				finalValue.append(val1);
			}

			else {
				//finalValue = finalValue + reqValueObj.get(k);
				finalValue.append(reqValueObj.get(k));
			}

		}

		return finalValue.toString();
	}


	private  Object getObject(Map<String,String>  map,Map<String,String> mapingKeys,Map<String,Object> reqValueObj,Object supperClass,String sourceName){
		logger.debug(TranslatorConstants.ENTER);
		Object innerObject=supperClass;

		Object childObject=null;
		
		for(Entry<String, String> mapEntry:map.entrySet()){
		
			String key=mapEntry.getKey();
			String value=mapEntry.getValue();
			String[] mapingValue=key.split("\\.");
			String oldParent="";
			if(!"{}".equals(value)){
				for(int i=0;i<mapingValue.length;i++){
					Class<?> clasName=null;
					oldParent=oldParent+mapingValue[i];
					String mapClas=mapingKeys.get(oldParent);

					if(mapClas!=null && mapClas.length()>0){
						mapClas=getSource(sourceName, true)+mapClas;
						try {
							if(innerObject!=null && !mapClas.endsWith(innerObject.getClass().getName()) ){

								clasName=Class.forName(mapClas);
								Method m = innerObject.getClass().getDeclaredMethod("get" + mapingValue[i]);
								childObject = m.invoke(innerObject);
								innerObject=setObjectRef(mapingValue[i], innerObject, childObject,m,clasName,null);
							}
							if(supperClass==null){
								clasName=Class.forName(mapClas);
								innerObject=clasName.newInstance();
								return innerObject;
							}

						} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
							logger.debug("Element data:"+oldParent+" Error:"+e.getMessage());
						}
					}else if(Util.isNotNull(innerObject) && mapingValue[mapingValue.length-1].equals(mapingValue[i])){

						try {
							Method m = innerObject.getClass().getDeclaredMethod("get" + mapingValue[i]);
							childObject = m.invoke(innerObject);
							if(value!=null && value.indexOf("{")!=-1){
								String innerVal = value.toString().substring(1, value.toString().length() - 1);
								String fieldFinalVal = gettingValueBasedOnProp(innerVal,reqValueObj);
								if(m.getReturnType().equals(String.class)){
									setObjectRef(mapingValue[i], innerObject, childObject,m,clasName,fieldFinalVal);
									logger.debug("Key:"+oldParent+":Value:"+fieldFinalVal);
								}else{
									setfields(innerObject, mapingValue[i], fieldFinalVal);
									logger.debug("Key:"+oldParent+":Value:"+fieldFinalVal);
								}
								innerObject=supperClass;
							}
						} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
							logger.debug("Set Element data:"+oldParent+" Error:"+e.getMessage());
						}


					}
					oldParent=oldParent+".";
				}
			}



		}

		logger.debug(TranslatorConstants.EXIT);
		return innerObject;
	}
	
	

	@SuppressWarnings("unchecked")
	private static Object setObjectRef(String clasKey, Object innerObject, Object childObject,Method m,Class<?> childName,String mapString){
		try {

			logger.debug(TranslatorConstants.ENTER);
			if (childObject instanceof List) {

				@SuppressWarnings("rawtypes")
				List tempList = (List) childObject;
				if ((CollectionUtils.isEmpty(tempList))) {
					childObject=childName.newInstance();
					tempList.add(childObject);

					m.invoke(innerObject);


				}else{
					childObject = tempList.get(0);

				}

			}else if(Util.isNotNull(innerObject)){

				if(!Util.isNotNull(childObject) && childName!=null){
					childObject=childName.newInstance();
				}else if(mapString!=null){
					childObject=mapString;

				}
				Method m1 = innerObject.getClass().getDeclaredMethod(TranslatorConstants.BINDING_CLASS_SET + clasKey, childObject.getClass());

				m1.invoke(innerObject, childObject);

			}

		} catch (Exception e) {
			logger.info("Element data:"+e.getMessage());
		}


		logger.debug(TranslatorConstants.EXIT);

		return childObject;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void setfields(Object innerObject,String clas,String value){

		try {
			Class<?> bigdeci = BigDecimal.class;

			Class<?> xmlGregorianCal = XMLGregorianCalendar.class;

			Class<?> list = java.util.List.class;

			Field field = innerObject.getClass().getDeclaredField(doField(clas));
			logger.debug("field  :"+field);

			field.setAccessible(true);

			if(field.getType().equals(bigdeci)) {
				BigDecimal bd=new BigDecimal(value);
				field.set(innerObject, bd);

			}

			else if(field.getType().equals(xmlGregorianCal)) {
				GregorianCalendar c = new GregorianCalendar();
				XMLGregorianCalendar dateXMLGreg = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
				field.set(innerObject, dateXMLGreg);
			}

			else if(field.getType().equals(list)) {
				String s=value;
				List<String> myList = new ArrayList<String>(Arrays.asList(s));
				logger.debug("end object :"+innerObject+" final value"+myList);	
				field.set(innerObject, myList);
			}else if(field.getType().isEnum()) {
				field.set(innerObject, Enum.valueOf((Class<Enum>) field.getType(), value));
			}


			else {

				field.set(innerObject, value);

				logger.debug("end object :"+innerObject+" final value"+value);	
			}
		} catch (IllegalArgumentException e) {
			logger.info("Element data:"+e.getMessage());
		} catch (Exception e) {
			logger.info("Element data:"+e.getMessage());
		}

	}
	private static String doField(String str){
		if(str!=null && str!="")
			str = str.toLowerCase().charAt(0)+str.substring(1,str.length());
		return str;
	}

	@SuppressWarnings("unused")
	private String getString(String str){

		if(str.indexOf("{")!=-1 && str.indexOf("}")!=-1)
		{
			str= str.replace("{", "").replace("}", "");
			System.out.println(str);
		}
		return str;
	}



	private String getSource(String name,boolean isClass){
		name=name.replace(".", "");
		if(isClass){
			return TranslatorConstants.BINDING_CLASS_PAKAGE+name+".";
		}
		return TranslatorConstants.BINDING_CLASS_PAKAGE+name;
	}



}
