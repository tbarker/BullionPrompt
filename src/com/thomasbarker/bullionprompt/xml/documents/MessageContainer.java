package com.thomasbarker.bullionprompt.xml.documents;

import java.util.List;

public interface MessageContainer<T> {

	public T getContent();

	public List<String> getErrors();
}
