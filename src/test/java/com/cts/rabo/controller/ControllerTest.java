package com.cts.rabo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cts.rabo.model.Reports;
import com.cts.rabo.model.StatementRecords;
import com.cts.rabo.model.Statements;
import com.cts.rabo.service.FileTypeService;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

	@InjectMocks
	StatementController statementcontroller;

	@Mock
	FileTypeService fileTypeService;

	Reports xmlReport;

	List<StatementRecords> csvStatementRcd;
	List<StatementRecords> xmlStatementRcd;
	StatementRecords record1;
	StatementRecords record2;
	StatementRecords record3;
	StatementRecords record4;
	StatementRecords record5;
	List<Statements> csvStatements;
	List<Statements> xmlStatements;
	Statements csvSt1;
	Statements csvSt2;
	Statements csvSt3;
	Statements xmlst1;
	Statements xmlst2;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		record1 = new StatementRecords(new BigDecimal(112806), "NL27SNSB0917829871", "Clothes for Willem Dekker",
				BigDecimal.valueOf(91.23), BigDecimal.valueOf(15.57), BigDecimal.valueOf(106.8));
		record2 = new StatementRecords(new BigDecimal(112806), "NL69ABNA0433647324", "Clothes for Richard de VriesB",
				BigDecimal.valueOf(90.83), BigDecimal.valueOf(-10.91), BigDecimal.valueOf(79.92));
		record3 = new StatementRecords(new BigDecimal(112806), "NL93ABNA0585619023", "Tickets from Richard BakkerC",
				BigDecimal.valueOf(102.12), BigDecimal.valueOf(45.87), BigDecimal.valueOf(147.99));

		record4 = new StatementRecords(new BigDecimal(112806), "NL69ABNA0433647324", "Clothes for Richard de Vries",
				BigDecimal.valueOf(90.83), BigDecimal.valueOf(12.6), BigDecimal.valueOf(79.92));
		record5 = new StatementRecords(new BigDecimal(112806), "NL93ABNA0585619023", "Tickets from Richard Bakker",
				BigDecimal.valueOf(102.12), BigDecimal.valueOf(-45.87), BigDecimal.valueOf(147.99));

		csvStatementRcd = new ArrayList<>();
		csvStatementRcd.add(record1);
		csvStatementRcd.add(record2);
		csvStatementRcd.add(record3);

		xmlStatementRcd = new ArrayList<>();
		xmlStatementRcd.add(record4);
		xmlStatementRcd.add(record5);

		csvSt1 = new Statements(new BigDecimal(112806), "Clothes from Jan Bakker");
		csvSt2 = new Statements(new BigDecimal(112806), "Clothes for Richard de Vries");
		csvSt3 = new Statements(new BigDecimal(112806), "Tickets from Richard Bakker");
		xmlst1 = new Statements(new BigDecimal(167875), "Tickets from Erik de Vries");
		xmlst2 = new Statements(new BigDecimal(165102), "Tickets for Rik Theuß");

		csvStatements = new ArrayList<>();
		csvStatements.add(csvSt1);
		csvStatements.add(csvSt2);
		csvStatements.add(csvSt3);

		xmlStatements = new ArrayList<>();
		xmlStatements.add(xmlst1);
		xmlStatements.add(xmlst2);

		xmlReport = new Reports();
		xmlReport.setInvalidEndBanlance(xmlStatements);
	}

	@Test
	public void uploadMultipleFilesTest() throws IOException {

		String csvFile = "records.csv";
		String xmlFile = "records.xml";
		MultipartFile xml = new MockMultipartFile(xmlFile, xmlFile, "application/xml",
				new FileInputStream(new File("src/test/resources/records.xml")));

		MultipartFile csv = new MockMultipartFile(csvFile, csvFile, "text/csv",
				new FileInputStream(new File("src/test/resources/records.csv")));



		List<MultipartFile> files = new ArrayList<>();
		files.add(csv);
		files.add(xml);

		when(fileTypeService.divideFileType(files)).thenReturn(xmlReport);

		ResponseEntity<Reports> empList = statementcontroller.uploadMultipleFiles(files);

		Assert.assertNotNull(empList.getBody());

		assertThat(empList.getStatusCodeValue()).isEqualTo(200);

		Assert.assertEquals("failure statementResponse are not equal", xmlReport, xmlReport);

	}

}
