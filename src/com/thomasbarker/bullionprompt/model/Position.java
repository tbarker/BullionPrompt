package com.thomasbarker.bullionprompt.model;

import java.util.Currency;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;

import com.thomasbarker.bullionprompt.model.Position;
import com.thomasbarker.bullionprompt.model.enums.Security;
import com.thomasbarker.bullionprompt.xml.adaptors.CurrencyAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalKilosAsGramsAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalMoneyAsMinorsAdaptor;

@XmlRootElement( name = "clientPosition" )
@XmlAccessorType( XmlAccessType.FIELD )
@Data
public final class Position {

	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalKilosAsGramsAdaptor.class )
	private Long available;
	@XmlAttribute( name = "classNarrative" ) public String narrative;
	@XmlAttribute( name = "securityId" ) private Security security;
	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalKilosAsGramsAdaptor.class )
	private Long total;
	@XmlAttribute( name = "totalValuation" )
	@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	private Long valuation;
	@XmlAttribute
	@XmlJavaTypeAdapter( CurrencyAdaptor.class )
	private Currency valuationCurrency;

	public Long getValuation() {
		return this.valuation * ( "CURRENCY".equals( narrative ) ? 10 : 1 );
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((available == null) ? 0 : available.hashCode());
		result = prime * result + ((narrative == null) ? 0 : narrative.hashCode());
		result = prime * result + ((security == null) ? 0 : security.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		result = prime * result + ((valuation == null) ? 0 : valuation.hashCode());
		result = prime * result + ((valuationCurrency == null) ? 0 : valuationCurrency.hashCode());
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
		if( !(obj instanceof Position) ) {
			return false;
		}
		Position other = (Position) obj;
		if( available == null ) {
			if( other.available != null ) {
				return false;
			}
		} else if( !available.equals(other.available) ) {
			return false;
		}
		if( narrative == null ) {
			if( other.narrative != null ) {
				return false;
			}
		} else if( !narrative.equals(other.narrative) ) {
			return false;
		}
		if( security != other.security ) {
			return false;
		}
		if( total == null ) {
			if( other.total != null ) {
				return false;
			}
		} else if( !total.equals(other.total) ) {
			return false;
		}
		if( valuation == null ) {
			if( other.valuation != null ) {
				return false;
			}
		} else if( !valuation.equals(other.valuation) ) {
			return false;
		}
		if( valuationCurrency == null ) {
			if( other.valuationCurrency != null ) {
				return false;
			}
		} else if( !valuationCurrency.equals(other.valuationCurrency) ) {
			return false;
		}
		return true;
	}

}
