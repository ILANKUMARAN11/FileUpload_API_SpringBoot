package com.cts.rabo.service.util;

import java.util.List;

import com.cts.rabo.model.StatementRecords;
import com.cts.rabo.model.Statements;

public interface ValidatorService {
	/**
	 * 
	 * @param statemnets List of all Transaction records.
	 * @return Extracting Duplicate Reference ID .
	 */
	List<Statements> extractDupicateRef(List<StatementRecords> statemnets);
	
	/**
	 * 
	 * @param statemnets List of all Transaction records.
	 * @return Extracting InValid End Balance.
	 */
	List<Statements> validateEndBalance(List<StatementRecords> statemnets);

}
