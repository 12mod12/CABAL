package com.mod12.cabal.server.core.person;


public class ComplexName extends Name {

	private String middleName;					
	private String suffix;
	private String prefix;
	private String title;

	public ComplexName(String firstName, String middleName, String lastName,
			String prefix, String suffix, String title) {
		super(firstName, lastName);		
		this.middleName = middleName;		
		this.prefix = prefix;
		this.suffix = suffix;
		this.title = title;
	}

	@Override
	public String toString() {
		return prefix + " " + this.getFirstName() + " " + middleName + " " + this.getLastName() + " " + suffix + ", " + title;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the suffice
	 */
	public String getSuffice() {
		return suffix;
	}

	/**
	 * @param suffice the suffice to set
	 */
	public void setSuffice(String suffice) {
		this.suffix = suffice;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
}
