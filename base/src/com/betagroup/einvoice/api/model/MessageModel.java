package com.betagroup.einvoice.api.model;

public class MessageModel {
	String type; // Used in validation API
	String category;
	String code;
	String message;
//	String status; // Used in validation API
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "MessageModel [type=" + type + ", category=" + category + ", code=" + code + ", message=" + message
				+ "]";
	}
	
	
}
