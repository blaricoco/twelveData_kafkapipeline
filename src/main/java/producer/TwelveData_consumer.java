package producer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class TwelveData_consumer extends Thread{

	
	private final KafkaConsumer<Integer, String> consumer;
	private final String topic;
	
	public final String KAFKA_SERVER_URL = "localhost";
	public final int KAFKA_SERVER_PORT = 9092;
	public final String CLIENT_ID = "Twelvedata_consumer";
	
	public TwelveData_consumer(String topic) {
		
		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT);
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, CLIENT_ID);
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		
		consumer = new KafkaConsumer<Integer, String>(properties);
		this.topic = topic;
	}
	
	public void run() {
		while(true) {
			
			consumer.subscribe(Collections.singletonList(this.topic));
			//@SuppressWarnings("deprecation")
			ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofMillis(1000));
			
			//System.out.println(records);
			for(ConsumerRecord<Integer, String> record : records) {
				System.out.println("Received message: (" + record.key() + "," + record.value() + ") at offset " + record.offset());
			}
		}
	}
	
}
