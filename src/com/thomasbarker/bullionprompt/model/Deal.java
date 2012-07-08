package com.thomasbarker.bullionprompt.model;

import java.util.Currency;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;

import com.thomasbarker.bullionprompt.model.enums.Security;
import com.thomasbarker.bullionprompt.xml.adaptors.CurrencyAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalKilosAsGramsAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalMoneyAsMinorsAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.TimestampDateAdaptor;

@XmlRootElement( name = "deal" )
@XmlAccessorType( XmlAccessType.FIELD )
@Data
public class Deal {

	@XmlAttribute
	@XmlJavaTypeAdapter( CurrencyAdaptor.class )
	private Currency considerationCurrency;

	@XmlAttribute( name = "securityId" ) private Security security;

	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalKilosAsGramsAdaptor.class )
	private Long quantity;

	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	private Long pricePerUnit;

	@XmlAttribute
	@XmlJavaTypeAdapter( TimestampDateAdaptor.class )
	private Date dealTime;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((considerationCurrency == null) ? 0 : considerationCurrency.hashCode());
		result = prime * result + ((dealTime == null) ? 0 : dealTime.hashCode());
		result = prime * result + ((pricePerUnit == null) ? 0 : pricePerUnit.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
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
		if( !(obj instanceof Deal) ) {
			return false;
		}
		Deal other = (Deal) obj;
		if( considerationCurrency == null ) {
			if( other.considerationCurrency != null ) {
				return false;
			}
		} else if( !considerationCurrency.equals(other.considerationCurrency) ) {
			return false;
		}
		if( dealTime == null ) {
			if( other.dealTime != null ) {
				return false;
			}
		} else if( !dealTime.equals(other.dealTime) ) {
			return false;
		}
		if( pricePerUnit == null ) {
			if( other.pricePerUnit != null ) {
				return false;
			}
		} else if( !pricePerUnit.equals(other.pricePerUnit) ) {
			return false;
		}
		if( quantity == null ) {
			if( other.quantity != null ) {
				return false;
			}
		} else if( !quantity.equals(other.quantity) ) {
			return false;
		}
		if( security != other.security ) {
			return false;
		}
		return true;
	}

}
