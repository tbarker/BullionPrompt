package com.thomasbarker.bullionprompt.model.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum
public enum ActionIndicator {

	@XmlEnumValue( "B" )
    BUY( "B" ),

    @XmlEnumValue( "S" )
    SELL( "S" );

    private final String code;

    ActionIndicator( String code ) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}
