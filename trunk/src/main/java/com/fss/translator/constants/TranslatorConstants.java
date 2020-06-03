package com.fss.translator.constants;


/**
 * Translator constants defined for constants declaration 
 * The keys are mapped to key-value pairs in requestData Map
 * @author ravinaganaboyina
 *
 */

public class TranslatorConstants {

	public TranslatorConstants(){

		throw new IllegalStateException("Constants class");
	}
	//Keys declaration 

	public static final String CONTENT_RESPONSE_ISXML="application/xml";

	public static final String CONTENT_RESPONSE_ISJSON="application/json";

	public static final String RESPONSE_CODE="responseCode";

	public static final String RESPONSE_MESSAGE="responseMessage";

	public static final String RESPONSE_DATA="responseData";

	public static final String TRANSLATOR_DATA_CACHE = "translateDataCache";

	public static final String CONTENT_TYPE="content-type";
	public static final String ACCEPT="accept";
	public static final String SRCAPPID="srcappid";
	public static final String IPADDRESS="ipaddress";
	public static final String CORRELATIONID="correlationid";

	public static final String SOURCEMESSAGETYPE="sourceMessageType";
	public static final String APIKEY="apiKey";
	public static final String REQUESTDATA="requestData";
	public static final String RRN="RRN";


	public static final String SOURCEMESSAGETYPE_IS08085="ISO8583";
	public static final String SOURCEMESSAGETYPE_ISO020022="ISO020022";

	public static final String REQUEST_VALIDATION_FILE="Requestvalidation.properties";

	public static final String REQUEST_ISO_TEMPLATE_FILE="ISOTemplate.properties";
	public static final String REQUEST_ISO22_MAP_FILE="Root_xsd.properties";
	public static final String INST_PREFIX="inst_";
	public static final String INST_ID="instid";
	public static final String ISO8583PACKAGER_FILE="ISO8583Packager.file";
	
	public static final String PROPERTIES_PATH="Properties";
	public static final String XML_PATH="XML";
	public static final String XSD_PATH="XSD";

	public static final String IS_MANDATORY="M";
	public static final String IS_NON_MANDATORY="N";
	public static final String IS_OPTIONA="O";

	public static final String BINDING_CLASS_PAKAGE="com.fss.translator.biniding.";
	
	public static final String BINDING_CLASS_SET="set";

	public static final String BINDING_CLASS_GET="get";


	//Values declaration 

	public static final String TARGET_RESPONSE_FORMAT="targetResponseFormat";
	public static final String  TARGETRESPONSEFORMATOPT="targetResponseFormatOpt";
	public static final String REQUESTDATAFORMATOPT="requestDataFormatOpt";
	public static final String CONTENT_RESPONSE_FORMAT="contentResponseFormat";
	public static final String TARGET_RESPONSE_ISO8583_87="ISO8583_87";
	public static final String TARGET_RESPONSE_ISO8583_93="ISO8583_93";
	public static final String TARGET_RESPONSE_ISO20022="ISO20022";
	public static final String TARGET_RESPONSE_ISO8583="ISO8583";
	public static final String TARGET_RESPONSE_JSON="JSON";



	//ISO Releated keys

	public static final String ISO = "iso";
	public static final String DCID = "dcid";
	public static final String RELID = "relid";
	public static final String BIT_POSITION = "bit_position";
	public static final String REASONCODE = "reasoncode";
	public static final String ORIGINATOR = "originator";
	public static final String AUTHORIZOR = "authorizor";
	public static final String MSGTYPE="MsgType";
	public static final String DATAELEMENTSVALUE = "DataElementsValue";
	public static final String DE_60 = "60";
	public static final String DE_125 = "125";
	public static final String DE125FULLDATA = "De125fullData";
	public static final String NETWORKMANAGEMENTINFORMATION = "NetworkManagementInformation";

	//Institution configuration properties
	public static final String INSTITITUION_TARGET_CONVERSION = "mappedPainversion";
	
	//Common constants
	public static final String ENTER="ENTER";
	public static final String EXIT="EXIT";
	public static final String PEROPERTY_SUFFIX = ".properties";
	public static final String XSD_SUFFIX = ".xsd";
	
	public static final String XSD_ROOTS="xsd_roots";
	




}
