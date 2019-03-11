package com.example.demo

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate
import org.springframework.cloud.gcp.pubsub.integration.AckMode
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders
import org.springframework.cloud.gcp.pubsub.support.converter.JacksonPubSubMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.handler.annotation.Header

@SpringBootApplication
class DemoApplication {

  private val REGISTRANT_SUBSCRIPTION = "registrations-sub"

  @Autowired
  private lateinit var personRepository: PersonRepository
  
  @Bean
  fun pubsubInputChannel() = DirectChannel()

  @Bean
  fun messageChannelAdapter(
      @Qualifier("pubsubInputChannel") inputChannel: MessageChannel,
      pubSubTemplate: PubSubTemplate): PubSubInboundChannelAdapter {

    val adapter = PubSubInboundChannelAdapter(
        pubSubTemplate, REGISTRANT_SUBSCRIPTION)
    adapter.outputChannel = inputChannel
    adapter.ackMode = AckMode.MANUAL
    adapter.payloadType = Person::class.java
    return adapter
  }

  @ServiceActivator(inputChannel = "pubsubInputChannel")
  fun messageReceiver(
      payload: Person,
      @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) message: BasicAcknowledgeablePubsubMessage) {
    personRepository.save(payload)
    print("Message arrived! Payload: $payload")
    message.ack()
  }

  @Bean
  fun jacksonPubSubMessageConverter(objectMapper: ObjectMapper) = JacksonPubSubMessageConverter(objectMapper)
}

fun main(args: Array<String>) {
        runApplication<DemoApplication>(*args)
}