package producer;


import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class TwelveData_Spark_consumer extends Thread{
	
	private final SparkSession spark;
	private final String topic;
	public static final String KAFKA_SERVER_URL = "localhost";
	public static final int KAFKA_SERVER_PORT = 9092;
	
	public TwelveData_Spark_consumer(String topic) {
		this.topic = topic;
		spark = new SparkSession
				.Builder()
				.appName("twelvedata_session")
				.master("local[*]")
				.getOrCreate();
	}

	public void run() {
		
		Dataset<Row> df = spark
				.readStream()
				.format("kafka")
				.option("kafka.bootstrap.servers", KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT)
				.option("subscribe", topic)
				.load();
		//df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)");
		while(true) {
			df.show();
		}
	}
}
