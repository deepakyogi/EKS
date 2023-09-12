package com.mongo.example.mongodemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;
import com.mongo.example.mongodemo.aws.AwsMqttHelper;
import com.mongo.example.mongodemo.aws.TestTopicListener;
import com.mongo.example.mongodemo.aws.myConstants;
import com.mongo.example.mongodemo.repository.CarRepository;
import com.mongo.example.mongodemo.services.vehiclecommservice.CarInterface;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableSwagger2
//@EnableWebMvc
@SpringBootApplication
@EnableMongoRepositories
@ConfigurationProperties(prefix = "aws")
public class SpringBootSmartVehicleApplication {

//	@Autowired
//	static
//	Connection cn;

//	@Autowired
//	private static CarRepository carRepo;
//	AWSIotMessage message;
//	TestTopicListener testTopicListener;
//	@Autowired
//	public static CarInterface repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSmartVehicleApplication.class, args);
		System.out.println("***********Spring boot app started********** ");

		AwsMqttHelper.instance.connect();
		AWSIotQos qos = AWSIotQos.QOS0;
		AWSIotTopic topic = new TestTopicListener(myConstants.TOPIC_ACTIONS_RESPONSE, qos);
		AWSIotTopic topic1 = new TestTopicListener(myConstants.TOPIC_VEHICLE_STATE, qos);

		AwsMqttHelper.instance.subscribe(topic);
		AwsMqttHelper.instance.subscribe(topic1);

//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        context.scan("com.mongo.example.mongodemo");
//        context.refresh();
//         repository = context.getBean(CarRepository.class);
//
//         context.close();	

	}

//	 public void run(String... strings) throws Exception {
//		 testTopicListener.onMessage( message);
//	    }
}
