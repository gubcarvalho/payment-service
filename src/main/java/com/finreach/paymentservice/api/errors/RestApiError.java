package com.finreach.paymentservice.api.errors;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RestApiError {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	 
	private String message;
	
	private String localizedMessage;
	
	private RestApiError() {
		this.timestamp = LocalDateTime.now();
	}
	
	public RestApiError(String message, Throwable ex) {
		this();
		this.message = message;
		this.localizedMessage = ex.getLocalizedMessage();
	}
	
	public RestApiError(Throwable ex) {
		this();
		this.message = ex.getMessage();
		this.localizedMessage = ex.getLocalizedMessage();
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getLocalizedMessage() {
		return localizedMessage;
	}

}
