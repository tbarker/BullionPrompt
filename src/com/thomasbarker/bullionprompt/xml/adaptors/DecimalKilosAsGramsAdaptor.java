package com.thomasbarker.bullionprompt.xml.adaptors;

public final class DecimalKilosAsGramsAdaptor extends AbstractDecimalAdaptor {

	@Override
	public int getMinorPerMajor() { return 1000; }

}