package com.thomasbarker.bullionprompt.model;

import java.util.Currency;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;

import com.thomasbarker.bullionprompt.model.enums.ActionIndicator;
import com.thomasbarker.bullionprompt.model.enums.OrderStatus;
import com.thomasbarker.bullionprompt.model.enums.OrderType;
import com.thomasbarker.bullionprompt.model.enums.Security;
import com.thomasbarker.bullionprompt.xml.adaptors.BVDateAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.CurrencyAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalKilosAsGramsAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalMoneyAsMinorsAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.UtcDateAdaptor;

@XmlRootElement( name = "order" )
@XmlAccessorType( XmlAccessType.FIELD )
@Data
public final class Order {

	@XmlAttribute private ActionIndicator actionIndicator;
	@XmlAttribute private String clientTransRef;
	@XmlAttribute
	@XmlJavaTypeAdapter( CurrencyAdaptor.class )
	private Currency considerationCurrency;
	@XmlAttribute
	@XmlJavaTypeAdapter( UtcDateAdaptor.class )
	private Date goodUntil;
	@XmlAttribute
	@XmlJavaTypeAdapter( UtcDateAdaptor.class )
	private Date lastModified;
	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	private Long limit;
	@XmlAttribute( name = "orderId" ) private Long id;
	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalKilosAsGramsAdaptor.class )
	private Long quantity;
	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalKilosAsGramsAdaptor.class )
	private Long quantityMatched;
	@XmlAttribute( name = "securityId" ) private Security security;
	@XmlAttribute( name = "statusCode" ) private OrderStatus status;
	@XmlAttribute( name = "typeCode" ) private OrderType type;
	@XmlAttribute( name = "orderTime" )
	@XmlJavaTypeAdapter( BVDateAdaptor.class )
	private Date time;
	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	private Long totalCommission;
	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	private Long totalConsideration;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actionIndicator == null) ? 0 : actionIndicator.hashCode());
		result = prime * result + ((clientTransRef == null) ? 0 : clientTransRef.hashCode());
		result = prime * result + ((considerationCurrency == null) ? 0 : considerationCurrency.hashCode());
		result = prime * result + ((goodUntil == null) ? 0 : goodUntil.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastModified == null) ? 0 : lastModified.hashCode());
		result = prime * result + ((limit == null) ? 0 : limit.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((quantityMatched == null) ? 0 : quantityMatched.hashCode());
		result = prime * result + ((security == null) ? 0 : security.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((totalCommission == null) ? 0 : totalCommission.hashCode());
		result = prime * result + ((totalConsideration == null) ? 0 : totalConsideration.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if( !(obj instanceof Order) ) {
			return false;
		}
		Order other = (Order) obj;
		if( actionIndicator != other.actionIndicator ) {
			return false;
		}
		if( clientTransRef == null ) {
			if( other.clientTransRef != null ) {
				return false;
			}
		} else if( !clientTransRef.equals(other.clientTransRef) ) {
			return false;
		}
		if( considerationCurrency == null ) {
			if( other.considerationCurrency != null ) {
				return false;
			}
		} else if( !considerationCurrency.equals(other.considerationCurrency) ) {
			return false;
		}
		if( goodUntil == null ) {
			if( other.goodUntil != null ) {
				return false;
			}
		} else if( !goodUntil.equals(other.goodUntil) ) {
			return false;
		}
		if( id == null ) {
			if( other.id != null ) {
				return false;
			}
		} else if( !id.equals(other.id) ) {
			return false;
		}
		if( lastModified == null ) {
			if( other.lastModified != null ) {
				return false;
			}
		} else if( !lastModified.equals(other.lastModified) ) {
			return false;
		}
		if( limit == null ) {
			if( other.limit != null ) {
				return false;
			}
		} else if( !limit.equals(other.limit) ) {
			return false;
		}
		if( quantity == null ) {
			if( other.quantity != null ) {
				return false;
			}
		} else if( !quantity.equals(other.quantity) ) {
			return false;
		}
		if( quantityMatched == null ) {
			if( other.quantityMatched != null ) {
				return false;
			}
		} else if( !quantityMatched.equals(other.quantityMatched) ) {
			return false;
		}
		if( security != other.security ) {
			return false;
		}
		if( status != other.status ) {
			return false;
		}
		if( time == null ) {
			if( other.time != null ) {
				return false;
			}
		} else if( !time.equals(other.time) ) {
			return false;
		}
		if( totalCommission == null ) {
			if( other.totalCommission != null ) {
				return false;
			}
		} else if( !totalCommission.equals(other.totalCommission) ) {
			return false;
		}
		if( totalConsideration == null ) {
			if( other.totalConsideration != null ) {
				return false;
			}
		} else if( !totalConsideration.equals(other.totalConsideration) ) {
			return false;
		}
		if( type != other.type ) {
			return false;
		}
		return true;
	}

}
