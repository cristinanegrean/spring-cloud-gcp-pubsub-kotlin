package com.example.demo

import java.time.LocalDateTime

import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.web.servlet.ModelAndView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.support.GenericMessage

@EnableBinding(Source::class)
@RestController
class PersonController(val pubSubTemplate: PubSubTemplate, val personRepository: PersonRepository, val source: Source) {

    val REGISTRATION_TOPIC = "registrations"
    val REGISTRATION_MESSAGE = "Your registration has been completed"
    
    @PostMapping("/registerPerson")
    fun registerPerson(
            @RequestParam("firstName") firstName: String,
            @RequestParam("lastName") lastName: String,
            @RequestParam("email") email: String): RedirectView {
        pubSubTemplate.publish(REGISTRATION_TOPIC, Person(firstName, lastName, email))

        val userMessage = UserMessage(REGISTRATION_MESSAGE, firstName, LocalDateTime.now())
        val message = GenericMessage<UserMessage>(userMessage)
		source.output().send(message)

        return RedirectView("/")
    }

    @GetMapping("/registrants")
    fun getRegistrants(): ModelAndView {
        val personsList = personRepository.findAll().toList()
        return ModelAndView("registrants", mapOf("personsList" to personsList))
    }
}