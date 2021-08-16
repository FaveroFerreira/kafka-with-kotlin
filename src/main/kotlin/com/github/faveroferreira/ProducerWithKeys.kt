package com.github.faveroferreira

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

fun main() {
    val props = Properties().apply {
        setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOSTRAP_SERVERS)
        setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.qualifiedName)
        setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.qualifiedName)
    }

    val producer = KafkaProducer<String, String>(props)

    for (i in 0..9) {
        val key = "id_$i"
        val value = "Hello World $i"

        val record = ProducerRecord(TOPIC_NAME, key, value)

        producer.send(record) { metadata, exception ->
            metadata ?: throw RuntimeException("Error producing record with exception: $exception")

            println("""
                Success producing record with Key: $key and Value: $value to: 
                    - topic: ${metadata.topic()}
                    - partition: ${metadata.partition()}
                    - offset: ${metadata.offset()}
                    - timestamp: ${metadata.timestamp()}
            """.trimIndent())
        }
    }


    producer.close()
}