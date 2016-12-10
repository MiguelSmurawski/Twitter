package masterApp.trandingTopology;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaProducerTwitter {
	
	public static void main(String[] args) { 
		Properties props = new Properties(); 
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,1ocalhost:9093,1ocalhost:9094"); 
		props.put("acks", "all"); 
		props.put("retries", 0); 
		props.put("batch.size", 16384); 
		props.put("buffer.memory", 33554432); 
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer"); 
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer"); 
	
		KafkaProducer<String, String> prod = new KafkaProducer<String, String>(props); 
		String topic = "myTopic";
		int partition = 0; 
		String key = "testKey"; 
		String value = "testValue";
		prod.send(new ProducerRecord<String, String>(topic,partition,key, value)); 
		prod.close(); 
	}

}
