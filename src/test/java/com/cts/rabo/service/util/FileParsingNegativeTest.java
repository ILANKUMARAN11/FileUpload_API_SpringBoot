package com.cts.rabo.service.util;

import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cts.rabo.function.ParsingExpression;
import com.cts.rabo.model.CsvReport;
import com.cts.rabo.model.StatementResponse;
import com.cts.rabo.model.exception.RaboFileFormatException;
import com.cts.rabo.model.exception.RaboRuntimeException;
import com.cts.rabo.service.FileParsingServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class FileParsingNegativeTest {

	@InjectMocks
	FileParsingServiceImpl fileParsingService;

	@Mock
	FileParsingServiceImpl fileParsingServices;

	@Mock
	List<MultipartFile> filesd;

	@Mock
	ParsingExpression<List<MultipartFile>, List<CsvReport>> parsing;

	@Before
	public void preTestSetup() throws FileNotFoundException {

	}

//	@Test(expected = RaboRuntimeException.class)
//	public void testAdd() throws Exception {
//
//
//		MultipartFile csv = new MockMultipartFile("records.csv", "records.csv", "text/csv", new FileInputStream(new File("src/test/resources/records.csv")));
//		List<MultipartFile> files = new ArrayList<MultipartFile>();
//		files.add(csv);
//		
//		File fileMock = createMock(File.class);
//
//		
//		when(fileParsingService.parsingCSV(files)).thenThrow(IOException.class);
//		
//
//		List<CsvReport> empList = fileParsingService.parsingCSV(files);
//
//	}
	
	@Test(expected = RaboFileFormatException.class)
	public void divideFileTypeTest() throws Exception {


		MultipartFile csv = new MockMultipartFile("index.html", "index.html", "text/html", new FileInputStream(new File("src/test/resources/index.html")));

		List<MultipartFile> files = new ArrayList<MultipartFile>();
		files.add(csv);

		StatementResponse statementResponse = fileParsingService.divideFileType(files);

	}

}
