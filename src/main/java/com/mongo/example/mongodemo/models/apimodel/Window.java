package com.mongo.example.mongodemo.models.apimodel;

public class Window {
	private Side front;
	private Side rear;
	
	public Window() {
		super();
	}
	
	public Window(Side front, Side rear) {
		super();
		this.front = front;
		this.rear = rear;
	}
	
	public Side getFront() {
		return front;
	}
	public void setFront(Side front) {
		this.front = front;
	}
	public Side getRear() {
		return rear;
	}
	public void setRear(Side rear) {
		this.rear = rear;
	}
	
	@Override
	public String toString() {
		return "Window [front=" + front + ", rear=" + rear + "]";
	}

}
