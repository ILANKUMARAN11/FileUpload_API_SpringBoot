package com.cts.rabo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.cts.rabo.exception.RaboFileFormatException;
import com.cts.rabo.exception.RaboRuntimeException;
import com.cts.rabo.exception.handler.RestExceptionHandler;
import com.cts.rabo.model.ApiErrorResponse;
import com.cts.rabo.model.Reports;
import com.cts.rabo.model.StatementRecords;
import com.cts.rabo.model.Statements;
import com.cts.rabo.service.impl.FileParsingServiceImpl;
import com.cts.rabo.service.impl.FileTypeServiceImpl;
import com.cts.rabo.service.impl.ValidatorServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ServiceTest {

	@InjectMocks
	FileParsingServiceImpl fileParsingService;

	@Mock
	FileParsingServiceImpl fileParsingServiceImpl;

	@Mock
	ValidatorService validatorService;

	@Mock
	List<Reports> reportsList;

	@Mock
	Reports reports;

	@Mock
	List<MultipartFile> filesd;

	@InjectMocks
	ValidatorServiceImpl validatorServices;

	List<StatementRecords> statementRcds;
	StatementRecords record1;
	StatementRecords record2;

	@InjectMocks
	RestExceptionHandler restExceptionHandler;

	@Mock
	WebRequest request;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		record1 = new StatementRecords(new BigDecimal(194261), "NL91RABO0315273637", "Clothes from Jan Bakker",
				BigDecimal.valueOf(11.0), BigDecimal.valueOf(1.0), BigDecimal.valueOf(31.1));
		record2 = new StatementRecords(new BigDecimal(194261), "NL91RABO0315273637", "Clothes from ILAN",
				BigDecimal.valueOf(1.1), BigDecimal.valueOf(4.1), BigDecimal.valueOf(31.0));

		statementRcds = new ArrayList<>();
		statementRcds.add(record1);
		statementRcds.add(record2);

	}

	@InjectMocks
	FileTypeServiceImpl fileTypeServiceImpl;

	@Test
	public void divideFileTypeTest() throws IOException {

		String csvFile = "records.csv";
		String xmlFile = "records.xml";

		MultipartFile csv = new MockMultipartFile(csvFile, csvFile, "text/csv",
				new FileInputStream(new File("src/test/resources/records.csv")));

		MultipartFile xml = new MockMultipartFile(xmlFile, xmlFile, "application/xml",
				new FileInputStream(new File("src/test/resources/records.xml")));

		List<MultipartFile> xmlFiles = new ArrayList<>();
		xmlFiles.add(xml);

		List<MultipartFile> csvFiles = new ArrayList<>();
		csvFiles.add(csv);

		List<MultipartFile> files = new ArrayList<>();
		files.addAll(xmlFiles);
		files.addAll(csvFiles);

		when(fileParsingServiceImpl.parsingCSV(csvFiles)).thenReturn(Optional.ofNullable(reports));
		when(fileParsingServiceImpl.parsingXML(xmlFiles)).thenReturn(Optional.ofNullable(reports));
		Reports empList = fileTypeServiceImpl.divideFileType(files);

		Assert.assertNotNull(empList);

	}

	@Test(expected = RaboFileFormatException.class)
	public void negativeFileTypeTest() throws IOException {

		MultipartFile csv = new MockMultipartFile("index.html", "index.html", "text/html",
				new FileInputStream(new File("src/test/resources/index.html")));

		List<MultipartFile> files = new ArrayList<>();
		files.add(csv);

		Reports report = fileTypeServiceImpl.divideFileType(files);

		assertNull(report);

	}

	@Test
	public void parsingXMLTest() throws IOException {

		String xmlFile = "records.xml";
		MultipartFile xml = new MockMultipartFile(xmlFile, xmlFile, "application/xml",
				new FileInputStream(new File("src/test/resources/records.xml")));

		List<MultipartFile> files = new ArrayList<>();
		files.add(xml);

		Optional<Reports> empList = fileParsingService.parsingXML(files);

		Assert.assertTrue(empList.isPresent());

	}

	@Test
	public void parsingCSVTest() throws IOException {

		String csvFile = "records.csv";

		MultipartFile csv = new MockMultipartFile(csvFile, csvFile, "text/csv",
				new FileInputStream(new File("src/test/resources/records.csv")));

		List<MultipartFile> files = new ArrayList<>();
		files.add(csv);

		Optional<Reports> empList = fileParsingService.parsingCSV(files);

		Assert.assertTrue(empList.isPresent());

	}

	@Test
	public void extractDuplicateRefTest() {
		List<Statements> empList = validatorServices.duplicateReference(statementRcds);

		assertEquals(2, empList.size());
	}

	@Test
	public void validateEndBalanceTest() {
		List<Statements> empList = validatorServices.invalidEndBalance(statementRcds);

		assertEquals(2, empList.size());
	}

	@Test
	public void invalidFileExceptionTest() {

		ResponseEntity<ApiErrorResponse> rabo = restExceptionHandler
				.invalidFileException(new RaboFileFormatException("Test", 403), request);
		Assert.assertTrue(rabo.getBody().getMessage().equals("Test"));
	}

	@Test
	public void raboExceptionTest() {

		ResponseEntity<ApiErrorResponse> rabo = restExceptionHandler
				.raboException(new RaboRuntimeException("Test", 403), request);
		Assert.assertTrue(rabo.getBody().getMessage().equals("Test"));
	}

	@Test
	public void globalExceptionTest() {

		ResponseEntity<ApiErrorResponse> rabo = restExceptionHandler.globalException(new Throwable("Test"), request);
		Assert.assertTrue(rabo.getBody().getMessage().equals("Test"));
	}

}
