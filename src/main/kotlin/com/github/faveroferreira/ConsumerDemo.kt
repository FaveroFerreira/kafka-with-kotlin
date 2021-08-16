package com.github.faveroferreira

import org.apache.kafka.clients.consumer.ConsumerConfig.*
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*

fun main() {
    val groupId = "simple-consumer"

    val props = Properties().apply {
        setProperty(BOOTSTRAP_SERVERS_CONFIG, BOOSTRAP_SERVERS)
        setProperty(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.qualifiedName)
        setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.qualifiedName)
        setProperty(GROUP_ID_CONFIG, groupId)
        setProperty(AUTO_OFFSET_RESET_CONFIG, "earliest")
    }

    val consumer = KafkaConsumer<String, String>(props)

    consumer.subscribe(listOf(TOPIC_NAME))

    while (true) {
        val records: ConsumerRecords<String, String> = consumer.poll(Duration.ofSeconds(100))

        records.forEach {
            println("""
                Consumed a record:
                    - key: ${it.key()}
                    - value: ${it.value()}
                    - topic: ${it.topic()}
                    - partition: ${it.partition()}
                    - offset: ${it.offset()}
            """.trimIndent())
        }
    }
}