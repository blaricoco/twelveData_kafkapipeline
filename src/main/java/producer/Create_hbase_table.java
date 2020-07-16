package producer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.io.encoding.DataBlockEncoding;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.TableName;

public class Create_hbase_table extends Thread{
	
	Configuration conf = HBaseConfiguration.create();
	private Boolean running = true;
	private String table_name = "stocks_test";
	// Create table data structure
	private TableName stocks_table = TableName.valueOf(table_name);
	// Column families 
	private String family_datetime = "datetime";
	private String family_open = "open";
	private String family_high = "high";
	private String family_low = "low";
	private String family_close = "close";
	
	public void run(){

		// Point to h-base client to configuration file
		String path = this.getClass()
				.getClassLoader()
				.getResource("hbase-site.xml")
				.getPath();
		conf.addResource(new Path(path));
		
		try {
			// Create connection to the database 
			Connection conn = ConnectionFactory.createConnection(conf);
			Admin hAdmin = conn.getAdmin();

			// Create table if it does not exist 
			if(!hAdmin.tableExists(stocks_table)) {
				// Instance new table with specific table name 
				TableDescriptorBuilder htable = TableDescriptorBuilder.newBuilder(stocks_table);
				
				// Create and assign column families to the table 
				ColumnFamilyDescriptorBuilder familyBuilder_datetime = ColumnFamilyDescriptorBuilder
						.newBuilder(Bytes.toBytes(family_datetime)).setBlocksize(32 * 1024)
						.setCompressionType(Compression.Algorithm.SNAPPY).setDataBlockEncoding(DataBlockEncoding.NONE);
				htable.setColumnFamily(familyBuilder_datetime.build());
				
				ColumnFamilyDescriptorBuilder familyBuilder_open = ColumnFamilyDescriptorBuilder
						.newBuilder(Bytes.toBytes(family_open)).setBlocksize(32 * 1024)
						.setCompressionType(Compression.Algorithm.SNAPPY).setDataBlockEncoding(DataBlockEncoding.NONE);
				htable.setColumnFamily(familyBuilder_open.build());
				
				ColumnFamilyDescriptorBuilder familyBuilder_high = ColumnFamilyDescriptorBuilder
						.newBuilder(Bytes.toBytes(family_high)).setBlocksize(32 * 1024)
						.setCompressionType(Compression.Algorithm.SNAPPY).setDataBlockEncoding(DataBlockEncoding.NONE);
				htable.setColumnFamily(familyBuilder_high.build());
				
				ColumnFamilyDescriptorBuilder familyBuilder_low = ColumnFamilyDescriptorBuilder
						.newBuilder(Bytes.toBytes(family_low)).setBlocksize(32 * 1024)
						.setCompressionType(Compression.Algorithm.SNAPPY).setDataBlockEncoding(DataBlockEncoding.NONE);
				htable.setColumnFamily(familyBuilder_low.build());
				
				ColumnFamilyDescriptorBuilder familyBuilder_close = ColumnFamilyDescriptorBuilder
						.newBuilder(Bytes.toBytes(family_close)).setBlocksize(32 * 1024)
						.setCompressionType(Compression.Algorithm.SNAPPY).setDataBlockEncoding(DataBlockEncoding.NONE);
				htable.setColumnFamily(familyBuilder_close.build());
				
				// Create table using administrator connection
				//hAdmin.createTable(htable);
				
				//hAdmin.addColumnFamily(tableName, columnFamily);
			}
			// Get table 
			Table table = conn.getTable(stocks_table);
			
			// Add new data while running
			while(running) {
				byte[] row1 = Bytes.toBytes("row1");
				Put p = new Put(row1);
				
				p.addColumn(family_datetime.getBytes(), Bytes.toBytes("datetime"), Bytes.toBytes("2020-07-10 16:26:00"));
				p.addColumn(family_open.getBytes(), Bytes.toBytes("open"), Bytes.toBytes(1.6276));
				p.addColumn(family_high.getBytes(), Bytes.toBytes("high"), Bytes.toBytes(2.6276));
				p.addColumn(family_low.getBytes(), Bytes.toBytes("low"), Bytes.toBytes(3.6276));
				p.addColumn(family_close.getBytes(), Bytes.toBytes("close"), Bytes.toBytes(4.6276));
				
				// put the rows as data into the h-base table.
				table.put(p);
				running = false;
			}
			// Close connection and table link
			conn.close();
			table.close();
		}
		catch (Exception e){
			// If its not able to create/put data - error 
			e.printStackTrace();
		}
		finally
		{
		   //hello
		}
	}
}
