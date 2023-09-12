package com.mongo.example.mongodemo.models.apimodel;

public class Light {
	private String head;

	//lower => 1 upper => 2 OFF=> 0"
	public Light() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Light(String head) {
		super();
		this.head = head;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	@Override
	public String toString() {
		return "Light [head=" + head + "]";
	}
	
	
}
