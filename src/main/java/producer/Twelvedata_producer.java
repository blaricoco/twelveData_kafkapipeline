package producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Twelvedata_producer extends Thread {

	private final KafkaProducer<Integer, String> producer;
	private final String topic;
	private final Boolean isAsync;
	
	public static final String KAFKA_SERVER_URL = "localhost";
	public static final int KAFKA_SERVER_PORT = 9092;
	public static final String CLIENT_ID = "Twelvedata_producer";
	
	public Twelvedata_producer(String topic, Boolean isAsync) {
		Properties properties = new Properties();
		properties.put("bootstrap.servers", KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT);
		properties.put("client.id", CLIENT_ID);
		properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		producer = new KafkaProducer<Integer, String>(properties);
		this.topic = topic;
		this.isAsync = isAsync;
	}
	
	public void run() {

		int messageNo = 1;
		
		while(true & messageNo < 5) {
			TwelvedataAPI twelvedataAPI = new TwelvedataAPI();
			String messageStr = "Message_" + messageNo;
			long startTime = System.currentTimeMillis();
			if (isAsync) {
				// Send asynchronously
				
				producer.send(new ProducerRecord<Integer, String>(
						topic,
						messageNo,
						twelvedataAPI.getStocks()),
						new Twelvedata_CallBack(startTime, messageNo, messageStr));
			}
			else {
				try {
					producer.send(new ProducerRecord<Integer, String>(
							topic,
							messageNo,
							twelvedataAPI.getStocks()
							)).get();
					System.out.println("Sent message: (" + messageNo + ", " + messageStr + ")");
					TimeUnit.SECONDS.sleep(60);
				}
				catch(InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
			++messageNo;
		}
	}
}

class Twelvedata_CallBack implements Callback {
	
	private final long startTime;
	private final int key;
	private final String message;
	
	public Twelvedata_CallBack(long startTime, int key, String message) {
		this.startTime = startTime;
		this.key = key;
		this.message = message;
	}
	
	public void onCompletion(RecordMetadata metadata, Exception exception) {
		long elapsedTime = System.currentTimeMillis() - startTime;
		if(metadata != null) {
			System.out.println(
                    "message(" + key + ", " + message + ") sent to partition(" + metadata.partition() +
                    "), " + "offset(" + metadata.offset() + ") in " + elapsedTime + " ms");	
		}
		else {
			exception.printStackTrace();
		}
	}
}

