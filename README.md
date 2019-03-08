# Setup and requirements

Google Account (Gmail or Google Apps) and access to Google Cloud Platform console (console.cloud.google.com), see: https://cloud.google.com/free/ 
whether you're for a free tier $300 credit with an expiry of one year. 

Activate the Cloud Shell to provision the Google Cloud Resources used by the application:

```
$ gcloud config set project <PROJECT_ID>
$ gcloud services enable pubsub.googleapis.com
$ gcloud services enable sqladmin.googleapis.com
$ gcloud pubsub topics create registrations
$ gcloud pubsub subscriptions create registrations-sub --topic=registrations
$ gcloud pubsub topics create posts
$ gcloud sql instances create codelab-instance --region=europe-west4
$ gcloud sql databases create registrants --instance codelab-instance
```

Note that when you create sql instances with ```gcloud sql instances create``` the default database engine is ```MySQL 5.7```. 

# Run application

In the Cloud Shell, checkout the code:

```
$ git clone https://github.com/cristinanegrean/spring-cloud-gcp-pubsub-kotlin.git
$ cd spring-cloud-gcp-pubsub-kotlin/ 
``` 

Launch the code editor (pencil icon in Cloud Shell). In the Code Editor explore the project's files, locate and open ```src/main/resources/application.properties```.
Edit ```spring.cloud.gcp.sql.instance-connection-name``` replace <PROJECT_ID>. At this point you can run the application from Cloud Shell terminal panel:

```
$ ./mvnw spring-boot:run
```

Open the Web Preview (eye icon in Cloud Shell) to visualise the application form used to interact with Google Cloud Pub/Sub.

# Concepts demonstrated
* Publishing to topics and consuming messages from subscriptions asynch (both polling and streaming way) using Spring Cloud GCP Pub/Sub integration
* Storing data to managed MySQL database using Spring Cloud GCP SQL integration
* Kotlin with Spring Boot Data JPA and Spring Web MVC

For further info see (original codelab)[https://codelabs.developers.google.com/codelabs/cloud-spring-cloud-gcp-kotlin] which I have based my own playground on, with some modifications to try out consuming from Pub/Sub in a streaming way.
