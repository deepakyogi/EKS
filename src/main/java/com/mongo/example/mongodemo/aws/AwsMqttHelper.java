package com.mongo.example.mongodemo.aws;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.amazonaws.services.iot.client.AWSIotDevice;
import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;
import com.fasterxml.jackson.core.JsonProcessingException;


public class AwsMqttHelper {

	String clientEndpoint = "a1bdsweekonwkv-ats.iot.ap-south-1.amazonaws.com";
	
	String clientId = "ttttttr";

	String awsAccessKeyId = "AKIAWKIHKGX7RHWUXQH5 ";

	String awsSecretAccessKey = "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";

	AWSIotMqttClient client ;

//	@Value("${aws.endPoint}")
//	String clientEndpoint;
//
//	@Value("${aws.clientid}")
//	String clientId ;
//
//	@Value("${aws.accessKeyId}")
//	String awsAccessKeyId ;
//
//	@Value("${aws.secretAccessKey}")
//	String awsSecretAccessKey ;

	public static AwsMqttHelper instance = new AwsMqttHelper();

//	 public static AwsMqttHelper getInstance()
//	    {
//	        if (instance == null)
//	        	instance = new AwsMqttHelper();
//	  
//	        return instance;
//	    }

	@SuppressWarnings("deprecation")
	public void connect() {
		// AWS IAM credentials could be retrieved from AWS Cognito, STS, or other secure
		// sources
		client = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey, null);
		// optional parameters can be set before connect()
		try {
			AWSIotDevice device = new AWSIotDevice("mqtttest");
			client.attach(device);
			client.connect();
		} catch (AWSIotException e) {
			e.printStackTrace();
		}
		System.out.println("connected");
	}

	public void publish(String topic, String payload) throws AWSIotException, JsonProcessingException {
		System.out.println("in puib");

		AWSIotQos qos = AWSIotQos.QOS0;

		long timeout = 3000;

		AWSIotMessage message = new AWSIotMessage(topic, qos, payload);

		client.publish(message, timeout);

		System.out.println("published");
	}

	public void subscribe(AWSIotTopic topic) {
		System.out.println("in sub");
		try {
			System.out.println("in sub try");
			client.subscribe(topic);
		} catch (AWSIotException e) {
			e.printStackTrace();
		}
	}
}
