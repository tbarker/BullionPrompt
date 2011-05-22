package com.thomasbarker.bullionprompt.cli.converter;

import java.lang.reflect.ParameterizedType;

import com.beust.jcommander.IStringConverter;

@SuppressWarnings("unchecked")
public class EnumParameterConverter< T extends Enum<?> >
	implements IStringConverter<T>
{

	public T convert( String enumText ) {
		return (T) Enum.valueOf( returnedClass(), enumText );
	}

	private Class returnedClass() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class) parameterizedType.getActualTypeArguments()[0];
	}

}
