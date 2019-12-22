package com.cts.rabo.service;

import java.util.List;

import com.cts.rabo.model.StatementRecords;
import com.cts.rabo.model.Statements;

/**
 * 
 * @author ilankumaran
 *
 */
public interface ValidatorService {

	/**
	 * 
	 * @param statemnets List of all Transaction records.
	 * @return Extracting Duplicate Reference ID .
	 */
	List<Statements> duplicateReference(List<StatementRecords> statemnets);

	/**
	 * 
	 * @param statemnets List of all Transaction records.
	 * @return Extracting InValid End Balance.
	 */
	List<Statements> invalidEndBalance(List<StatementRecords> statemnets);

}
