package producer;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;


public class Twelvedata_HBase_consumer extends Thread{

	private final KafkaConsumer<Integer, String> consumer;
	private final String topic;
	private Configuration hBase_conf;
	
	
	public static final String KAFKA_SERVER_URL = "localhost";
	public static final int KAFKA_SERVER_PORT = 9092;
	public static final String CLIENT_ID = "Twelvedata_consumer";
	
	public Twelvedata_HBase_consumer(String topic) throws IOException {
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
		
		hBase_conf = HBaseConfiguration.create();
		hBase_conf.set("hbase.zookeeper.quorum", "localhost");
		hBase_conf.setInt("timeout", 120000);
		hBase_conf.set("hbase.zookeeper.property.clientPort", "2181");
		
	}
	
	public void run() {
		while(true) {
			
			consumer.subscribe(Collections.singletonList(this.topic));
			ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofMillis(1000));
			
			for(ConsumerRecord<Integer, String> record : records) {
				System.out.println("Received message: (" + record.key() + "," + record.value() + ") at offset " + record.offset());
			}
		}
	}
}
