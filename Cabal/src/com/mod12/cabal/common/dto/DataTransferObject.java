package com.mod12.cabal.common.dto;

public abstract class DataTransferObject {

	protected static final String DELIM = "&&&";
	protected static final String TUPLE_DELIM = ":::";
	
	
	public abstract String marshall();
	
}
