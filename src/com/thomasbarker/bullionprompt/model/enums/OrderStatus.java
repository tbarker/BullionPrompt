package com.thomasbarker.bullionprompt.model.enums;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum OrderStatus {

	OPEN( "OPEN", "Order is open." ),
	DONE( "DONE", "Order has closed." ),
	EXPIRED( "EXPIRED", "Order closed by expiring."),
	CANCELLED( "CANCELLED", "Order was cancelled."),
	KILLED( "KILLED", "Order was killed because it could not be filled."),
	NOFUNDS( "NOFUNDS", "Order was rejected due to insufficient funds."),
	BADLIMIT( "BADLIMIT", "Order was rejected due to limit too high/low."),
	SILVER_RESTRICTED( "SILVER_RESTRICTED", "This account may not trade silver." ),
	QUEUED( "QUEUED", "Order is queued awaiting processing."),
	TO_BE_SETTLED( "TO_BE_SETTLED", "Order is awaiting settlement." ),
	AGIP_ENABLED( "AGIP_ENABLED", "AGIP is enabled on this account." );

	private final String code;
	private final String description;

	OrderStatus( String code, String desc ) {
		this.code = code;
		this.description = desc;
	}

	public String getCode() {
		return this.code;
	}

	public String getDescription() {
		return this.description;
	}

}
