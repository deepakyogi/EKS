// package com.mongo.example.mongodemo.aws;
//
//import org.springframework.context.annotation.Configuration;
//
//import com.amazonaws.services.iot.client.AWSIotDevice;
//import com.amazonaws.services.iot.client.AWSIotException;
//import com.amazonaws.services.iot.client.AWSIotMqttClient;
//
//
//@Configuration
//public class Connection {
//
//	String clientEndpoint = "a36o9z4m44q7dt-ats.iot.us-east-1.amazonaws.com"; // use value returned by describe-endpoint
//																				// --endpoint-type "iot:Data-ATS"
//	String clientId = "mqtng"; // replace with your own client ID. Use unique client IDs for concurrent
//								// connections.
//
//	String awsAccessKeyId = "AKIA6DWPUD7YD5MF2RFR ";
//// PKCS#1 or PKCS#8 PEM encoded private key file
//	String awsSecretAccessKey = "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";
//	AWSIotMqttClient client = null;
//	@SuppressWarnings("deprecation")
//	public void connect() {
//		// AWS IAM credentials could be retrieved from AWS Cognito, STS, or other secure
//		// sources
//		 client = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey,
//				null);
//			// optional parameters can be set before connect()
//			try {
//				System.out.println("try");
//				AWSIotDevice device = new AWSIotDevice(clientId);
//                client.attach(device);
//				client.connect();
//				System.out.println("try1");
//			} catch (AWSIotException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println("connection testing ");
//		}	
//}
