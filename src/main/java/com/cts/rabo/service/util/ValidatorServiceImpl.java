package com.cts.rabo.service.util;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cts.rabo.function.VoMapper;
import com.cts.rabo.model.StatementRecords;
import com.cts.rabo.model.Statements;

@Service
public class ValidatorServiceImpl extends VoMapper implements ValidatorService{
	
	private static Logger LOGGER = LoggerFactory.getLogger(ValidatorServiceImpl.class);
	
	/**
	 * 
	 * @param statemnets List of all Transaction records.
	 * @return Extracting Duplicate Reference ID .
	 */
	@Override
	public List<Statements> extractDupicateRef(List<StatementRecords> statemnets)
	{
		LOGGER.debug("processing inside Extracting Duplicate Reference ID");
		
 		return statemnets.stream()
				  .collect(Collectors.groupingBy(StatementRecords::getRefrence))
				  .entrySet().stream()
				  .filter(e->e.getValue().size() > 1)
				  .flatMap(e->e.getValue().stream())
				  .map(d->entityToVo.apply(d))
				  .collect(Collectors.toList());
		
	}
	
	/**
	 * 
	 * @param statemnets List of all Transaction records.
	 * @return Extracting InValid End Balance.
	 */
	@Override
	public List<Statements> validateEndBalance(List<StatementRecords> statemnets)
	{
		LOGGER.debug("Extracting InValid End Balance");
		
		Predicate<StatementRecords> p=new Predicate<StatementRecords>() {
			
			@Override
			public boolean test(StatementRecords e) {
				DecimalFormat df = new DecimalFormat("0.00");
				double spent=Double.parseDouble(df.format(e.getStartBalance()+e.getMutation()));
				double endBalance=e.getEndBalance();
				return spent!=endBalance;
			}
		};
 		
 	
 		return statemnets.stream()
				  .filter(p).map(d->entityToVo.apply(d)).collect(Collectors.toList());
 		
		
	}

}
