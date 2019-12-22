package com.cts.rabo.function;

import com.cts.rabo.exception.RaboRuntimeException;

/**
 * 
 * @author ilankumaran
 *
 * @param <T> custom functional interface for file parsing
 * @param <R> out of Statements pojo.
 */
@FunctionalInterface
public interface ParsingExpression<T, R> {

	/**
	 * 
	 * @param t
	 * @return
	 * @throws RaboRuntimeException
	 */
	R action(T t);

}
