/**
 * 
 */
package com.fss.translator.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fss.translator.util.Util;

/**
 * @author ravinaganaboyina
 *
 */
public class UtilTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_isEmpty_With_NullValue() {

		assertEquals(true, Util.isEmpty(null));
	}

	@Test
	public void test_isEmpty_With_EmptyValue() {

		assertEquals(true, Util.isEmpty(" "));
	}
	
	@Test
	public void test_isEmpty_With_ValidValue() {

		assertEquals(false, Util.isEmpty("test"));
	}
	
	@Test
	public void test_jsonToMap_with_values() throws IOException
	{

		Map<String, Object> productAttributes = Util.jsonToMap("{\"Details\":{\"Id\":15335,\r\n" + 
				"            \"Name\": \"Harikrishna Agnikondu\",\r\n" + 
				"            \"Description\": \"Java Developer\"\r\n" + 
				"}}");
	assertNotNull(productAttributes.get("Details"));
	}
	
	@Test
	public void test_mapToJson_with_values() throws IOException
	{
		 Map<String, Object> attributes = new HashMap<>();
		Map<String,Object> detailAttributes = new HashMap<>();
		detailAttributes.put("Id", "15335");
		detailAttributes.put("Name", "Harikrishna");
		attributes.put("Details", detailAttributes);
		String JsonString= Util.mapToJson(attributes);
		assertNotNull(JsonString);
	}
	
}
