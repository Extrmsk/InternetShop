package com.lemanov.internetshop.domain.exception;

@SuppressWarnings("serial")
public class NotEnoughGoodsException extends Exception {

	public NotEnoughGoodsException() {
		super();
	}
	
	public NotEnoughGoodsException(String message) {
		super(message);
	}

}
