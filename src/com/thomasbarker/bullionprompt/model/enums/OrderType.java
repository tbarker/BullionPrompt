package com.thomasbarker.bullionprompt.model.enums;

import jakarta.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum OrderType {

	TIL_CANCEL( "TIL_CANCEL" ),
	TIL_TIME( "TIL_TIME" ),
	IMMEDIATE( "IMMEDIATE" ),
	FILL_KILL( "FILL_KILL" );

    private final String code;

    OrderType( String code ) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
