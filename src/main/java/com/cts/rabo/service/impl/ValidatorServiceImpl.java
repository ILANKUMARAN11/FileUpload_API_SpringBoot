package com.cts.rabo.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cts.rabo.constants.RaboConstants;
import com.cts.rabo.model.StatementRecords;
import com.cts.rabo.model.Statements;
import com.cts.rabo.service.ValidatorService;

/**
 * 
 * @author ilankumaran
 *
 */
@Service
public class ValidatorServiceImpl implements ValidatorService {

	private static Logger logger = LoggerFactory.getLogger(ValidatorServiceImpl.class);

	/**
	 * 
	 * @param statemnets List of all Transaction records.
	 * @return Extracting Duplicate Reference ID .
	 */
	@Override
	public List<Statements> duplicateReference(List<StatementRecords> statemnets) {
		logger.trace("processing inside Extracting Duplicate Reference ID");

		return statemnets.stream().collect(Collectors.groupingBy(StatementRecords::getRefrence)).entrySet().stream()
				.filter(e -> e.getValue().size() > 1).flatMap(e -> e.getValue().stream()).map(t -> {
					Statements requiredObj = new Statements();
					requiredObj.setRefrence(t.getRefrence());
					requiredObj.setDescription(t.getDescription());
					return requiredObj;
				}).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param statemnets List of all Transaction records.
	 * @return Extracting InValid End Balance.
	 */
	@Override
	public List<Statements> invalidEndBalance(List<StatementRecords> statemnets) {
		logger.trace("Extracting InValid End Balance");

		return statemnets.stream().filter(e -> {
			logger.info(RaboConstants.START_BALANCE.concat(e.getStartBalance().toString()));
			logger.info(RaboConstants.MUTATION.concat(e.getMutation().toString()));
			BigDecimal spent = e.getStartBalance().add(e.getMutation());
			logger.debug(RaboConstants.SUM_OF_MUTATION.concat(spent.toString()));
			logger.debug(RaboConstants.END_BALANCE.concat(e.getEndBalance().toString()));
			BigDecimal endBalance = e.getEndBalance();
			return spent.compareTo(endBalance) != RaboConstants.ZERO_COMPARING;
		}).map(t -> {
			Statements requiredObj = new Statements();
			requiredObj.setRefrence(t.getRefrence());
			requiredObj.setDescription(t.getDescription());
			return requiredObj;
		}).collect(Collectors.toList());
	}

}
