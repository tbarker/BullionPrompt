package com.thomasbarker.bullionprompt.xml.adaptors;

public final class DecimalMoneyAsMinorsAdaptor extends AbstractDecimalAdaptor {

	@Override
	public int getMinorPerMajor() { return 100; }

}
