package producer;

import static org.junit.Assert.*;

import org.junit.Test;

public class Consumer_test {

	@Test
	public void test_toCheckIfKafkaServerPort_has_theSameAsTheProducer_expecting9092() {
		TwelveData_consumer consumer_test = new TwelveData_consumer("test");
		int output = consumer_test.KAFKA_SERVER_PORT;
		assertEquals(9092, output);
	}
	
	@Test
	public void test_toCheck_kafkaServerUrl_expecting_localhost() {
		TwelveData_consumer consumer_test = new TwelveData_consumer("test");
		String output = consumer_test.KAFKA_SERVER_URL;
		assertEquals("localhost", output);
	}
	
	@Test
	public void test_toCheck_client_id_hasTheRightName_expecting_Twelvedata_consumer() {
		TwelveData_consumer consumer_test = new TwelveData_consumer("test");
		String output = consumer_test.CLIENT_ID;
		assertEquals("Twelvedata_consumer", output);
	}

	@Test
	public void test_to_check_the_statusOfThread_of_consumer_to_be_alive_when_running() {
		TwelveData_consumer consumer_test = new TwelveData_consumer("test");
		consumer_test.start();
		Boolean output = consumer_test.isAlive();
		assertEquals(true, output);
	}
}
