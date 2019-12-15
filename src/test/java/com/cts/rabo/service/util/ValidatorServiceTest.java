package com.cts.rabo.service.util;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.cts.rabo.model.StatementRecords;
import com.cts.rabo.model.Statements;

@RunWith(MockitoJUnitRunner.class)
public class ValidatorServiceTest {
	
	
	
	@InjectMocks
	ValidatorServiceImpl validatorService;
	
	List<StatementRecords> statementRcds;
	List<Statements> statemnets;
	StatementRecords record1, record2;
	Statements st1,st2;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        
        record1=new StatementRecords(new BigDecimal(194261),"NL91RABO0315273637","Clothes from Jan Bakker",11d,-21d,31d);
		record2=new StatementRecords(new BigDecimal(194261),"NL91RABO0315273637","Clothes from ILAN",1d,21d,31d);
		
		statementRcds=new ArrayList<StatementRecords>();
		statementRcds.add(record1);
		statementRcds.add(record2);
		
		st1=new Statements(new BigDecimal(194261),"Clothes from Jan Bakker");
        st2=new Statements(new BigDecimal(194261),"Clothes from ILAN");
        
        statemnets=new ArrayList<Statements>();
		statemnets.add(st1);
		statemnets.add(st2);
    }
	
	
	
	
	
	@Test
	public void extractDupicateRefTest()
	{
		List<Statements> empList = validatorService.extractDupicateRef(statementRcds);
		
		assertEquals(2, empList.size());
		
	}
	
	
	
	@Test
	public void validateEndBalanceTest()
	{
		List<Statements> empList = validatorService.validateEndBalance(statementRcds);
		
		assertEquals(2, empList.size());
	}
	
	

}
