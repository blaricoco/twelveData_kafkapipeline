package producer;

public class Producer_Application {
	public static final String TOPIC = "twelveData";
	
	public static void main(String[] args) {
		//boolean isAsync = false;
		//Twelvedata_producer producerThread = new Twelvedata_producer(TOPIC, isAsync);
		//TwelveData_consumer consumerThread = new TwelveData_consumer(TOPIC);
		//TwelveData_Spark_consumer twelveData_SparkThread = new TwelveData_Spark_consumer(TOPIC);
		Create_hbase_table testThread = new Create_hbase_table();
		
		// Start the producer
		//producerThread.start();
		//consumerThread.start();
		//twelveData_SparkThread.start();
		testThread.start();
	}
}
