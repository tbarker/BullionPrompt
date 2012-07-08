package com.thomasbarker.bullionprompt.model;

import java.util.Currency;

import lombok.Data;

import com.thomasbarker.bullionprompt.model.enums.ActionIndicator;
import com.thomasbarker.bullionprompt.model.enums.Security;

@Data
public final class Price {

	private ActionIndicator actionIndicator;
	private long price;
	private long quantity;
	private Security security;
	private Currency considerationCurrency;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actionIndicator == null) ? 0 : actionIndicator.hashCode());
		result = prime * result + ((considerationCurrency == null) ? 0 : considerationCurrency.hashCode());
		result = prime * result + (int) (price ^ (price >>> 32));
		result = prime * result + (int) (quantity ^ (quantity >>> 32));
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
		if( !(obj instanceof Price) ) {
			return false;
		}
		Price other = (Price) obj;
		if( actionIndicator != other.actionIndicator ) {
			return false;
		}
		if( considerationCurrency == null ) {
			if( other.considerationCurrency != null ) {
				return false;
			}
		} else if( !considerationCurrency.equals(other.considerationCurrency) ) {
			return false;
		}
		if( price != other.price ) {
			return false;
		}
		if( quantity != other.quantity ) {
			return false;
		}
		if( security != other.security ) {
			return false;
		}
		return true;
	}

}
