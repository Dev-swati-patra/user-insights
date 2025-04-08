package com.tech.user_insights.constants;

public enum ServiceCode {
	
	SVC001("SVC001", "Your request has been process successfully."),
	SVC002("SVC002", "User Name must not be null or empty."),
	SVC003("SVC003", "User Name already exists."),
	SVC004("SVC004", "User Email must not be null or empty."),
	SVC005("SVC005", "User Email must be in valid format."),
	SVC006("SVC006", "User Password must not be null or empty"),
	SVC007("SVC007", "User Password must be contain one uppercase letter/n one lowercase letter/n one digit/n one special character/n minimum eight character."),
	SVC008("SVC008", "User full name must not be null or empty."),
	SVC009("SVC009", "User full name must be in valid format."),
	SVC010("SVC010", "District name must not be null or empty."),
	SVC011("SVC011", "District name does not exist."),
	SVC012("SVC012", "State name must not be null or empty."),
	SVC013("SVC013", "State name does not exist."),
	SVC014("SVC014", "Country name must not be null or empty."),
	SVC015("SVC015", "Country name does not exist."),
	SVC016("SVC016", "Address must not be null or empty."),
	SVC017("SVC017", "Address must be in valid format."),
	SVC018("SVC018", "Age must not be null or empty."),
	SVC019("SVC019", "Phone Number must not be null or empty."),
	SVC020("SVC020", "Either Pancard or Passport or Aadhaar must be present."),
	SVC021("SVC021", "Pancard must be valid."),
	SVC022("SVC022", "Asdhaar must be valid."),
	SVC023("SVC023", "Incorrect password!"),
	SVC024("SVC024", "Newpassword and confirmpassword must be same."),
	SVC025("SVC025", "Please provide valid details."),
//	SVC026("SVC026", "Either user name or user email is required."),
	SVC026("SVC026", "User name does not exist."),
	SVC027("SVC027", "OTP does not match."),
	SVC028("SVC028", "New password and confirm password must be required."),
	SVC029("SVC029", "User name must be required."),
	SVC100("SVC100", "Unexpected Error!");

	String code;
	String message;

	private ServiceCode(String code, String message) {
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
