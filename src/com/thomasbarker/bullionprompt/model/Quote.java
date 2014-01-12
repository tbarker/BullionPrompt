package com.thomasbarker.bullionprompt.model;

import com.thomasbarker.bullionprompt.model.enums.Security;

public final class Quote {

    long ask;
    long bid;

    String currency;
    Security security;

    String considerationCurrency;
    
    public long getAsk() {
        return ask;
    }

    public long getBid() {
        return bid;
    }

    public String getConsiderationCurrency() {
        return currency;
    }

    public Security getSecurity() {
        return security;
    }

    public void setAsk(long ask) {
        this.ask = ask;
    }

    public void setBid(long bid) {
       this.bid = bid;
    }

    public void setConsiderationCurrency(String considerationCurrency) {
        this.considerationCurrency = considerationCurrency;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

}
