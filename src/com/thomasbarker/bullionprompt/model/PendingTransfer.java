package com.thomasbarker.bullionprompt.model;

import com.thomasbarker.bullionprompt.xml.adaptors.CurrencyAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalKilosAsGramsAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.DecimalMoneyAsMinorsAdaptor;
import com.thomasbarker.bullionprompt.xml.adaptors.UtcDateAdaptor;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Currency;
import java.util.Date;

@XmlAccessorType( XmlAccessType.FIELD )
@Data
public final class PendingTransfer {

	@XmlAttribute
	private String type;

	@XmlAttribute
	private String lowestLedger;

	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalKilosAsGramsAdaptor.class )
	private Long balance;

	@XmlAttribute
	@XmlJavaTypeAdapter( UtcDateAdaptor.class )
	private Date dueDate;

	@XmlAttribute
	@XmlJavaTypeAdapter( DecimalMoneyAsMinorsAdaptor.class )
	private Long valuation;

	@XmlAttribute
	@XmlJavaTypeAdapter( CurrencyAdaptor.class )
	private Currency valuationCurrency;
}
