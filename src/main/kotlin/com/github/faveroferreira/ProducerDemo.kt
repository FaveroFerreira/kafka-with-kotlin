package com.github.faveroferreira

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*


fun main() {
    val props = Properties().apply {
        setProperty(BOOTSTRAP_SERVERS_CONFIG, BOOSTRAP_SERVERS)
        setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.qualifiedName)
        setProperty(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.qualifiedName)
    }

    val producer = KafkaProducer<String, String>(props)

    val record = ProducerRecord<String, String>(TOPIC_NAME, "Hello World 2!")

    producer.send(record)
    producer.close()
}