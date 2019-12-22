package com.cts.rabo.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cts.rabo.constants.RaboConstants;
import com.cts.rabo.exception.RaboRuntimeException;
import com.cts.rabo.function.ParsingExpression;
import com.cts.rabo.model.Records;
import com.cts.rabo.model.Reports;
import com.cts.rabo.model.StatementRecords;
import com.cts.rabo.service.FileParsingService;
import com.cts.rabo.service.ValidatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.opencsv.CSVReader;

/**
 * 
 * @author ilankumaran
 *
 */
@Service
public class FileParsingServiceImpl implements FileParsingService {

	private static Logger logger = LoggerFactory.getLogger(FileParsingServiceImpl.class);

	@Autowired
	ValidatorService validatorService;

	/**
	 * 
	 * @param xmlLst List of MultiPart File as xmlList.
	 * @return return reports of XML.
	 */
	public Optional<Reports> parsingXML(List<MultipartFile> xmlLst) {
		logger.trace("processing inside XML parsing service ");
		Optional<Reports> xmlReports = Optional.empty();

		ParsingExpression<List<MultipartFile>, Optional<Reports>> parsing = new ParsingExpression<List<MultipartFile>, Optional<Reports>>() {
			Reports xmlReports = new Reports();

			@Override
			public Optional<Reports> action(List<MultipartFile> t) throws RaboRuntimeException {
				return t.stream().map(p -> {
					List<StatementRecords> statemnets = null;

					ObjectMapper objectMapper = new XmlMapper();
					// Reads from XML and converts to POJO
					Records records;
					try {
						records = objectMapper.readValue(p.getInputStream(), Records.class);
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
						throw new RaboRuntimeException(RaboConstants.FILE_IO, RaboConstants.FILE_IO_CODE);
					}

					statemnets = Arrays.asList(records.getStatementRecords());
					logger.info("List of XML Statement Records :: ".concat(statemnets.toString()));
					xmlReports.setDuplicateRef(validatorService.duplicateReference(statemnets));
					xmlReports.setInvalidEndBanlance(validatorService.invalidEndBalance(statemnets));
					return xmlReports;
				}).findFirst();
			}
		};
		Optional<Reports> optionalReports = parsing.action(xmlLst);
		if (optionalReports.isPresent()) {
			xmlReports = Optional.ofNullable(optionalReports.get());
		}
		return xmlReports;
	}

	/**
	 * 
	 * @param crvList List of MultiPart File as crvList.
	 * @return return reports of CSV.
	 */
	public Optional<Reports> parsingCSV(List<MultipartFile> crvList) {
		logger.trace("processing inside CSV parsing service");
		Optional<Reports> csvReports = Optional.empty();

		ParsingExpression<List<MultipartFile>, Optional<Reports>> parsing = new ParsingExpression<List<MultipartFile>, Optional<Reports>>() {
			Reports csvReports = new Reports();

			@Override
			public Optional<Reports> action(List<MultipartFile> t) throws RaboRuntimeException {
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
							rcd.setStartBalance(new BigDecimal(p[3].trim()));
							rcd.setMutation(new BigDecimal(p[4].trim()));
							rcd.setEndBalance(new BigDecimal(p[5].trim()));
							logger.info("CSV Statement Records :: ".concat(rcd.toString()));
							return rcd;
						}).collect(Collectors.toList());

						csvReader.close();
						reader.close();
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
						throw new RaboRuntimeException(RaboConstants.FILE_IO, RaboConstants.FILE_IO_CODE);
					}
					csvReports.setDuplicateRef(validatorService.duplicateReference(statemnets));
					csvReports.setInvalidEndBanlance(validatorService.invalidEndBalance(statemnets));
					return csvReports;
				}).findFirst();
			}
		};

		Optional<Reports> optionalReports = parsing.action(crvList);
		if (optionalReports.isPresent()) {
			csvReports = Optional.ofNullable(optionalReports.get());
		}
		return csvReports;
	}

}
