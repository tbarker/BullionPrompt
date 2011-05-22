package com.thomasbarker.bullionprompt.model.enums;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum Security {

    AUXLN( "AUXLN", "London Gold"),
    AUXZU( "AUXZU", "Zurich Gold"),
    AGXLN( "AGXLN", "London Silver"),
    AUXNY( "AUXNY", "New York Gold"),
    EUR( "EUR", "Euro"),
    USD( "USD", "US Dollar"),
    GBP( "GBP", "Sterling");

    private final String code;
    private final String description;

    Security( String code, String desc ) {
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
