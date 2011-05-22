package com.thomasbarker.bullionprompt.xml.adaptors;

public final class UtcDateAdaptor extends AbstractDateAdaptor  {

	@Override
	protected String getFormat() { return "yyyy-MM-dd hh:mm:ss ZZZ"; }

}
