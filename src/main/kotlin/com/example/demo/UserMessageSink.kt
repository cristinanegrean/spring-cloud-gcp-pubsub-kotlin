package com.example.demo

import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink

@EnableBinding(Sink::class)
class UserMessageSink {

	@StreamListener(Sink.INPUT)
	fun handleMessage(userMessage: UserMessage): Unit =
        print("Message arrived from $userMessage.getUsername : $userMessage.getBody at $userMessage.getCreatedAt")

}