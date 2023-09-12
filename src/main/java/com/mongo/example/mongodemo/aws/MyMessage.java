package com.mongo.example.mongodemo.aws;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;

public class MyMessage  extends AWSIotMessage   {
	
	
	  public MyMessage(String topic, AWSIotQos qos, String payload) {
	        super(topic, qos, payload);
	    }

	    @Override
	    public void onSuccess() {
	        System.out.println(" message published successfully");
	    }

	    @Override
	    public void onFailure() {
	       System.out.println("failue");
	    }

	    @Override
	    public void onTimeout() {
	        System.out.println("timeout");
	    }
	}

	
