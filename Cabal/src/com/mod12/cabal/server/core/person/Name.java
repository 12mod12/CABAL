package com.mod12.cabal.server.core.person;

import java.util.List;

import com.mod12.cabal.common.util.RandomUtil;

public class Name {

	private String firstName;					
	private String lastName;
	
	private static boolean firstNameMaleInitalized = false;
	private static boolean firstNameFemaleInitalized = false;
	private static boolean lastNameInitalized = false;
	private static List<String> firstNamesMale;
	private static List<String> firstNamesFemale;
	private static List<String> lastNames;

	public Name(String firstName, String lastName) {
		this.firstName = firstName;		
		this.lastName = lastName;						
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
	
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public static boolean initializeFirstMaleNames(List<String> generatedFirstNames) {
		if (firstNameMaleInitalized == false) {
			firstNamesMale = generatedFirstNames;
			firstNameMaleInitalized = true;
			return true;
		}
		else{
			return false;
		}
	}
	
	public static boolean initializeFirstFemaleNames(List<String> generatedFirstNames) {
		if (firstNameFemaleInitalized == false) {
			firstNamesFemale = generatedFirstNames;
			firstNameFemaleInitalized = true;
			return true;
		}
		else{
			return false;
		}
	}
	
	public static boolean initializeLastNames(List<String> generatedLastNames) {
		if (lastNameInitalized == false) {
			lastNames = generatedLastNames;
			lastNameInitalized = true;
			return true;
		}
		else{
			return false;
		}
	}
	
	public static List<String> getFirstMaleNames() {
		return firstNamesMale;
	}
	
	public static List<String> getFirstFemaleNames() { 
		return firstNamesFemale;
	}
	
	public static List<String> getLastNames() {
		return lastNames;
	}
	
	public static Name generateMaleName() {
		if (!firstNameMaleInitalized || !lastNameInitalized) {
			return null;
		}
		else{
			String first = firstNamesMale.get(RandomUtil.nextInt(firstNamesMale.size()));
			String last = lastNames.get(RandomUtil.nextInt(lastNames.size()));
			Name result = new Name(first,last);
			return result;
		}		
	}	
	
	public static Name generateFemaleName() {
		if (!firstNameFemaleInitalized || !lastNameInitalized) {
			return null;
		}
		else{
			String first = firstNamesFemale.get(RandomUtil.nextInt(firstNamesFemale.size()));
			String last = lastNames.get(RandomUtil.nextInt(lastNames.size()));
			Name result = new Name(first,last);
			return result;
		}		
	}

	public static Name generateName(boolean isFemale) {
		if (isFemale) return generateFemaleName();
		else return generateMaleName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj.getClass() == String.class){
			String test = firstName + " " + lastName;
			return test.equals(obj);
		}
		if (getClass() != obj.getClass())
			return false;
		Name other = (Name) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}
	
}
