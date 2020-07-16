package producer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ApiCall_test {

	@Test
	public void test_toCheckTheOutputof_apiCall_isString() {
		TwelvedataAPI apiCall_test = new TwelvedataAPI();
		String expected = "";
		String output = apiCall_test.getStocks();
		assertEquals(expected.getClass(), output.getClass());
	}
	
	@Test
	public void test_toCheckThat_whenTheAPI_IsCalled_TheReturnIsNot_Empty() {
		TwelvedataAPI apiCall_test = new TwelvedataAPI();
		String output = apiCall_test.getStocks();
		assertFalse(output.isEmpty());
	}
	
	@Test
	public void test_To_checkTheMetadataOf_APICall_HasTheSpecified_Parameters_expectedProperties() {
		
		TwelvedataAPI apiCall_test = new TwelvedataAPI();
		String output = apiCall_test.getStocks();
		
		JsonObject expected = new JsonObject();
		expected.addProperty("symbol","EUR/USD");
		expected.addProperty("interval","1min");
		expected.addProperty("currency_base","Euro");
		expected.addProperty("currency_quote","US Dollar");
		expected.addProperty("type","Physical Currency");
		
		JsonObject test_json = new Gson().fromJson(output, JsonObject.class);
		
		assertEquals(expected, test_json.get("meta"));
	}
	
	@Test
	public void test_To_checkTheMetadataOf_APICall_HasTheSpecified_Parameters_expectedStatus_ok() {
		
		TwelvedataAPI apiCall_test = new TwelvedataAPI();
		String output = apiCall_test.getStocks();
		
		JsonObject expected = new JsonObject();
		expected.addProperty("status","ok");
		
		
		JsonObject test_json = new Gson().fromJson(output, JsonObject.class);
		
		assertEquals(expected.get("status"), test_json.get("status"));
	}
	
	@Test
	public void test_To_checkTheMetadataOf_APICall_HasTheSpecified_Parameters_expectedKeys_meta_values() {
		
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("meta");
		expected.add("values");
		expected.add("status");
		
		TwelvedataAPI apiCall_test = new TwelvedataAPI();
		String output = apiCall_test.getStocks();
		JsonObject test_json = new Gson().fromJson(output, JsonObject.class);
		test_json.keySet().forEach(key -> result.add(key));
		
		assertEquals(expected, result);
	}
	
	@Test
	public void test_To_checkTheMetadataOf_APICall_HasTheSpecified_Parameters_expectedKeys_meta_values_status() {
		
		int result = 0;
		TwelvedataAPI apiCall_test = new TwelvedataAPI();
		String output = apiCall_test.getStocks();
		
		JsonObject test_json = new Gson().fromJson(output, JsonObject.class);
		for(JsonElement record : test_json.get("values").getAsJsonArray()) {
			System.out.println(record);
			result++;
		}
		assertEquals(30, result);
	}
}
