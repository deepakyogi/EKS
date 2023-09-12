package com.mongo.example.mongodemo.models.apimodel;


public class Side {
	private int left;
	private int right;

	
	public Side() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Side(int left, int right) {
		super();
		this.left = left;
		this.right = right;
	}


	public int getLeft() {
		return left;
	}


	public void setLeft(int left) {
		this.left = left;
	}


	public int getRight() {
		return right;
	}


	public void setRight(int right) {
		this.right = right;
	}


	@Override
	public String toString() {
		return "Mirror [left=" + left + ", right=" + right + "]";
	}

	
}
