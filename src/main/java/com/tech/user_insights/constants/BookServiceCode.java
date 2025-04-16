package com.tech.user_insights.constants;

public enum BookServiceCode {
	SVC001("SVC001", "Your request has been process successfully."),
	SVC002("SVC002", "Title must not be null or empty."),
	SVC003("SVC003", "Title must be in valid format."),
	SVC004("SVC004", "Author name must not be null or empty."),
	SVC005("SVC005", "Author name must be in valid format."),
	SVC006("SVC006", "Price must not be null or empty."),
	SVC007("SVC007", "Price must be greater than equal to 0."),
	SVC008("SVC008", "Stock not provided."),
	SVC009("SVC009", "Category not provided."),
	SVC010("SVC010", "Description not provided."),
	SVC011("SVC011", "Image url not provided."),
	SVC012("SVC012", "Author or title doesn't exit."),
	SVC013("SVC013", "Invalid details."),
	SVC100("SVC100", "Unexpected Error!");

	String code;
	String message;

	private BookServiceCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
