package com.cts.rabo.service.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cts.rabo.model.CsvReport;
import com.cts.rabo.model.StatementRecords;
import com.cts.rabo.model.StatementResponse;
import com.cts.rabo.model.Statements;
import com.cts.rabo.model.XmlReport;
import com.cts.rabo.service.FileParsingServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class FileParsingServiceTest {
	
	
	@InjectMocks
	FileParsingServiceImpl fileParsingService;

	@Mock
	FileParsingServiceImpl fileParsingServices;
	

	@Mock
	ValidatorService validatorService;
     
	StatementResponse statementResponse;

	@Mock
	List<CsvReport> csvReports;

	@Mock
	List<XmlReport> xmlReports;
  
	CsvReport csvReport;
	
	XmlReport xmlReport;
	

	
    MultipartFile[] files;
    
    
    List<StatementRecords> csvStatementRcd,xmlStatementRcd;
	StatementRecords record1,record2,record3,record4,record5;
	List<Statements> csvStatemnets,xmlStatemnets;
	Statements csvSt1,csvSt2,csvSt3,xmlst1,xmlst2;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        
        record1=new StatementRecords(new BigDecimal(112806),"NL27SNSB0917829871","Clothes for Willem Dekker",91.23,15.57,106.8);
		record2=new StatementRecords(new BigDecimal(112806),"NL69ABNA0433647324","Clothes for Richard de Vries",90.83,-10.91,79.92);
		record3=new StatementRecords(new BigDecimal(112806),"NL93ABNA0585619023","Tickets from Richard Bakker",102.12,45.87,147.99);
		
		record4=new StatementRecords(new BigDecimal(167875),"NL69ABNA0433647324","Tickets from Erik de Vries",90.83,2.6,79.92);
		record5=new StatementRecords(new BigDecimal(165102),"NL93ABNA0585619023","Tickets for Rik Theuß",102.12,5.87,147.99);
		
		csvStatementRcd=new ArrayList<StatementRecords>();
		csvStatementRcd.add(record1);
		csvStatementRcd.add(record2);
		csvStatementRcd.add(record3);
		
		
		xmlStatementRcd=new ArrayList<StatementRecords>();
		xmlStatementRcd.add(record4);
		xmlStatementRcd.add(record5);
		
		
        csvSt1=new Statements(new BigDecimal(112806),"Clothes from Jan Bakker");
        csvSt2=new Statements(new BigDecimal(112806),"Clothes for Richard de Vries");
        csvSt3=new Statements(new BigDecimal(112806),"Tickets from Richard Bakker");
        
        xmlst1=new Statements(new BigDecimal(167875),"Tickets from Erik de Vries");
        xmlst2=new Statements(new BigDecimal(165102),"Tickets for Rik Theuß");
        
        csvStatemnets=new ArrayList<Statements>();
		csvStatemnets.add(csvSt1);
		csvStatemnets.add(csvSt2);
		csvStatemnets.add(csvSt3);
		
		xmlStatemnets=new ArrayList<Statements>();
		xmlStatemnets.add(xmlst1);
		xmlStatemnets.add(xmlst2);
		
		csvReport=new CsvReport();
		csvReport.setFileName("records.csv");
		csvReport.setDuplicateRef(csvStatemnets);
		
		xmlReport=new XmlReport();
		xmlReport.setFileName("records.xml");
		xmlReport.setInvalidEndBanlance(xmlStatemnets);
		statementResponse=new StatementResponse();
		
		
    }
	
    
	@Test
    public void divideFileTypeTest() throws Exception {
		
	    
	    MultipartFile csv = new MockMultipartFile("records.csv",
				"records.csv", "text/csv", new FileInputStream(new File("src/test/resources/records.csv")));

		MultipartFile xml = new MockMultipartFile("records.csv",
				"records.xml", "application/xml", new FileInputStream(new File("src/test/resources/records.xml")));
	    
	    List<MultipartFile> xmlFiles=new ArrayList<>();
		xmlFiles.add(xml);

		List<MultipartFile> csvFiles=new ArrayList<>();
		csvFiles.add(csv);

		List<MultipartFile> files=new ArrayList<>();
		files.addAll(xmlFiles);
		files.addAll(csvFiles);

	    StatementResponse empList = fileParsingService.divideFileType(files);

		//Assert.assertSame("failure statementResponse are not equal", statementResponse, empList);
	   
	    Assert.assertNotNull(empList);
	    
    }


	@Test
	public void parsingXMLTest() throws Exception {



		MultipartFile xml = new MockMultipartFile("records.xml",
				"records.xml", "application/xml", new FileInputStream(new File("src/test/resources/records.xml")));

		List<MultipartFile> files=new ArrayList<>();
		files.add(xml);


		List<XmlReport> empList = fileParsingService.parsingXML(files);

		Assert.assertNotNull(empList);


		assertEquals(1, empList.size());

	}
	
	
	
	@Test
	public void parsingCSVTest() throws Exception {



		MultipartFile csv = new MockMultipartFile("records.csv",
				"records.csv", "text/csv", new FileInputStream(new File("src/test/resources/records.csv")));

		List<MultipartFile> files=new ArrayList<>();
		files.add(csv);
		

		List<CsvReport> empList = fileParsingService.parsingCSV(files);


		Assert.assertNotNull(empList);

	}
	
	


}
