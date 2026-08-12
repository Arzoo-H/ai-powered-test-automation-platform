package com.selenium.testng.enums;

public enum Menu {

	ADMIN("Admin"), 
	PIM("PIM"), 
	LEAVE("Leave"), 
	TIME("Time"), 
	PERFORMANCE("Performance"), 
	DIRECTORY("Directory"),
	MAINTENANCE("Maintenance"), 
	CLAIM("Claim"), 
	BUZZ("Buzz");

	private final String value;

	// setting up enum constructor
	Menu(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}