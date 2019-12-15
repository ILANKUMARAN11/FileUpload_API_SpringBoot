package com.cts.rabo.function;

import com.cts.rabo.model.exception.RaboRuntimeException;

@FunctionalInterface
public interface ParsingExpression<T,R> {
	
	R action(T t) throws RaboRuntimeException;

}
