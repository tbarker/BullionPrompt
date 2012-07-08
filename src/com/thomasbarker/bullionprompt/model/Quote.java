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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (ask ^ (ask >>> 32));
		result = prime * result + (int) (bid ^ (bid >>> 32));
		result = prime * result + ((considerationCurrency == null) ? 0 : considerationCurrency.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((security == null) ? 0 : security.hashCode());
		return result;
	}
	@Override
	public boolean equals( Object obj ) {
		if( this == obj ) {
			return true;
		}
		if( obj == null ) {
			return false;
		}
		if( !(obj instanceof Quote) ) {
			return false;
		}
		Quote other = (Quote) obj;
		if( ask != other.ask ) {
			return false;
		}
		if( bid != other.bid ) {
			return false;
		}
		if( considerationCurrency == null ) {
			if( other.considerationCurrency != null ) {
				return false;
			}
		} else if( !considerationCurrency.equals(other.considerationCurrency) ) {
			return false;
		}
		if( currency == null ) {
			if( other.currency != null ) {
				return false;
			}
		} else if( !currency.equals(other.currency) ) {
			return false;
		}
		if( security != other.security ) {
			return false;
		}
		return true;
	}

}
