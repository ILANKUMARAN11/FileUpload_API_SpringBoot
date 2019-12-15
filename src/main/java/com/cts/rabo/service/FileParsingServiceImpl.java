package com.cts.rabo.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cts.rabo.constants.RaboConstants;
import com.cts.rabo.function.ParsingExpression;
import com.cts.rabo.model.CsvReport;
import com.cts.rabo.model.Records;
import com.cts.rabo.model.StatementRecords;
import com.cts.rabo.model.StatementResponse;
import com.cts.rabo.model.XmlReport;
import com.cts.rabo.model.exception.InvalidFileException;
import com.cts.rabo.model.exception.RaboRuntimeException;
import com.cts.rabo.service.util.ValidatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.opencsv.CSVReader;

@Service
public class FileParsingServiceImpl implements FileParsingService {

	private static Logger LOGGER = LoggerFactory.getLogger(FileParsingServiceImpl.class);

	@Autowired
	ValidatorService validatorService;

	/**
	 * 
	 * @param multipartFiles Splitting XML and CSV. 
	 * @return Duplicate reference and invalid end balance from XML and CSV.
	 */
	@Override
	public StatementResponse divideFileType(List<MultipartFile> multipartFiles) {

		LOGGER.debug("processing inside splitting CSV and XML");

		StatementResponse statementResponse = null;

		boolean match = multipartFiles.stream().anyMatch(s -> s.getContentType().equals(RaboConstants.textCsv)
				|| s.getContentType().equals(RaboConstants.textXml) || s.getContentType().equals(RaboConstants.appXml));

		if (match) {
			List<MultipartFile> crvList = multipartFiles.parallelStream()
					.filter(p -> p.getContentType().equals(RaboConstants.textCsv)).collect(Collectors.toList());

			List<MultipartFile> xmlLst = multipartFiles.parallelStream()
					.filter(p -> p.getContentType().equals(RaboConstants.textXml)
							|| p.getContentType().equals(RaboConstants.appXml))
					.collect(Collectors.toList());

			statementResponse = new StatementResponse();
			if (xmlLst != null && !xmlLst.isEmpty()) {
				statementResponse.setXmlReport(parsingXML(xmlLst));
			}
			if (crvList != null && !crvList.isEmpty()) {
				statementResponse.setCsvReport(parsingCSV(crvList));
			}
		}else {
			throw new InvalidFileException("Invalid file Content-Type format");
		}

		return statementResponse;

	}

	/**
	 * 
	 * @param xmlLst List of XML files parsing.
	 * @return Duplicate reference and invalid end balance from XML.
	 */
	public List<XmlReport> parsingXML(List<MultipartFile> xmlLst) {

		LOGGER.debug("processing inside XML parsing service ");

		ParsingExpression<List<MultipartFile>, List<XmlReport>> parsing = new ParsingExpression<List<MultipartFile>, List<XmlReport>>() {

			@Override
			public List<XmlReport> action(List<MultipartFile> t) throws RaboRuntimeException {
				return t.stream().map(p -> {
					List<StatementRecords> statemnets = null;

					ObjectMapper objectMapper = new XmlMapper();
					// Reads from XML and converts to POJO
					Records records;
					try {
						records = objectMapper.readValue(p.getInputStream(), Records.class);
					} catch (IOException e) {
						LOGGER.error(e.getMessage(), e);

						RaboRuntimeException rabo = new RaboRuntimeException(RaboConstants.fileIo);
						rabo.setStatus(RaboConstants.fileIoCode);
						throw rabo;
					}

					statemnets = Arrays.asList(records.getStatementRecords());

					XmlReport xmlReport = new XmlReport();
					xmlReport.setFileName(p.getOriginalFilename());
					xmlReport.setDuplicateRef(validatorService.extractDupicateRef(statemnets));
					xmlReport.setInvalidEndBanlance(validatorService.validateEndBalance(statemnets));

					return xmlReport;
				}).collect(Collectors.toList());
			}
		};

		return parsing.action(xmlLst);

	}

	/**
	 * 
	 * @param crvList List of CSV files parsing.
	 * @return Duplicate reference and invalid end balance from CSV.
	 */
	public List<CsvReport> parsingCSV(List<MultipartFile> crvList) throws RaboRuntimeException{

		LOGGER.debug("processing inside CSV parsing service");

		ParsingExpression<List<MultipartFile>, List<CsvReport>> parsing = new ParsingExpression<List<MultipartFile>, List<CsvReport>>() {
		
			@Override
			public List<CsvReport> action(List<MultipartFile> t) throws RaboRuntimeException {
				return t.stream().map(j -> {

					List<StatementRecords> statemnets = null;
					CSVReader csvReader = null;
					Reader reader = null;
					try {
						reader = new InputStreamReader(j.getInputStream());
						csvReader = new CSVReader(reader);

						List<String[]> records = csvReader.readAll();

						statemnets = records.stream().skip(1).map(p -> {
							StatementRecords rcd = new StatementRecords();
							rcd.setRefrence(new BigDecimal(p[0].trim()));
							rcd.setAccountNo(p[1].trim());
							rcd.setDescription(p[2].trim());
							rcd.setStartBalance(Double.valueOf(p[3].trim()));
							rcd.setMutation(Double.valueOf(p[4].trim()));
							rcd.setEndBalance(Double.valueOf(p[5].trim()));
							return rcd;
						}).collect(Collectors.toList());

						csvReader.close();
						reader.close();

					} catch (IOException e) {

						LOGGER.error(e.getMessage(), e);

						RaboRuntimeException rabo = new RaboRuntimeException(RaboConstants.fileIo);
						rabo.setStatus(RaboConstants.fileIoCode);
						throw rabo;
					}
					CsvReport csvReport = new CsvReport();
					csvReport.setFileName(j.getOriginalFilename());
					csvReport.setDuplicateRef(validatorService.extractDupicateRef(statemnets));
					csvReport.setInvalidEndBanlance(validatorService.validateEndBalance(statemnets));

					return csvReport;
				}).collect(Collectors.toList());
			}
		};

		return parsing.action(crvList);
	}

}
