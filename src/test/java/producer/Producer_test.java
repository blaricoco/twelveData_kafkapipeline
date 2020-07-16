package producer;

import static org.junit.Assert.*;
import org.junit.Test;

public class Producer_test {

	@Test
	public void test_to_check_the_correct_portNumber_expecting_9092() {
		Twelvedata_producer producerTest = new Twelvedata_producer("topicTest", false);
		
		@SuppressWarnings("static-access")
		int output =  producerTest.KAFKA_SERVER_PORT;
		assertEquals( 9092, output);
	}
	
	@Test
	public void test_to_check_the_kafka_server_url_expected_localhost() {
		Twelvedata_producer producerTest = new Twelvedata_producer("topicTest", false);
		
		@SuppressWarnings("static-access")
		String output =  producerTest.KAFKA_SERVER_URL;
		assertEquals( "localhost", output);
	}
	
	@Test
	public void test_to_check_the_client_id_expected_twelvedata_producer() {
		Twelvedata_producer producerTest = new Twelvedata_producer("topicTest", false);
		
		@SuppressWarnings("static-access")
		String output =  producerTest.CLIENT_ID;
		assertEquals( "Twelvedata_producer", output);
	}
	
	@Test
	public void test_to_check_the_statusOfThread_of_producer_to_be_alive_when_running() {
		Twelvedata_producer producerTest = new Twelvedata_producer("topicTest", false);
		
		producerTest.start();
		Boolean output =  producerTest.isAlive();
		assertEquals( true, output);
	}
	
	@Test
	public void test_when_threadStart_expectedToHaveAnId_whenThreadItsCreated() {
		Twelvedata_producer producerTest = new Twelvedata_producer("topicTest", false);
		
		Long expected = (long) 17;
		producerTest.start();
		Long output =  producerTest.getId();
		assertEquals(expected.getClass(), output.getClass());
	}
}
