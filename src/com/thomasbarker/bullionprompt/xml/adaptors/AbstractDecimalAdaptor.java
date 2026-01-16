package com.thomasbarker.bullionprompt.xml.adaptors;

import java.math.BigDecimal;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public abstract class AbstractDecimalAdaptor extends XmlAdapter<BigDecimal, Long>  {

	public abstract int getMinorPerMajor();

	@Override
	public BigDecimal marshal( Long number ) throws Exception {
		return new BigDecimal( number ).divide( new BigDecimal ( getMinorPerMajor() ) );
	}

	@Override
	public Long unmarshal( BigDecimal decimal ) throws Exception {
		return decimal.multiply( new BigDecimal ( getMinorPerMajor() ) ).longValue();
	}

}