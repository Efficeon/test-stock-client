package com.testStockClient;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Arrays;
import java.util.Properties;

public class ClientApplication {

    public static void main(String[] args) {
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.DoubleDeserializer");
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Double.class);

        KafkaConsumer<String, Double> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("stock-topic-less"));
        while (true) {
            ConsumerRecords<String, Double> records = consumer.poll(10);

            records.forEach(e -> System.out.println("Offset " + e.offset()
                    + " key= " + e.key()
                    + " value= " + e.value()));
        }
    }
}
