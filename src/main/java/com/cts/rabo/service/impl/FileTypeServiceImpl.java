package com.cts.rabo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cts.rabo.constants.RaboConstants;
import com.cts.rabo.exception.RaboFileFormatException;
import com.cts.rabo.model.Reports;
import com.cts.rabo.service.FileParsingService;
import com.cts.rabo.service.FileTypeService;

/**
 * 
 * @author ilankumaran
 *
 */
@Service
public class FileTypeServiceImpl implements FileTypeService {

	private static Logger logger = LoggerFactory.getLogger(FileTypeServiceImpl.class);

	@Autowired
	FileParsingService fileParsingService;

	/**
	 * 
	 * @param multipartFiles List of MultiPart File as XML and CSV.
	 * @return return reports of XML and CSV.
	 */
	@Override
	public Reports divideFileType(List<MultipartFile> multipartFiles) {

		logger.trace("processing inside splitting CSV and XML");

		Reports reports = new Reports();

		boolean match = multipartFiles.stream()
				.anyMatch(s -> s.getContentType().equals(RaboConstants.TEXT_CSV)
						|| s.getContentType().equals(RaboConstants.TEXT_XML)
						|| s.getContentType().equals(RaboConstants.APP_XML));

		if (match) {
			List<MultipartFile> crvList = multipartFiles.parallelStream()
					.filter(p -> p.getContentType().equals(RaboConstants.TEXT_CSV)).collect(Collectors.toList());

			List<MultipartFile> xmlLst = multipartFiles.parallelStream()
					.filter(p -> p.getContentType().equals(RaboConstants.TEXT_XML)
							|| p.getContentType().equals(RaboConstants.APP_XML))
					.collect(Collectors.toList());

			Optional<Reports> xmlReports = fileParsingService.parsingXML(xmlLst);
			if (xmlReports.isPresent()) {
				reports = xmlReports.get();
				logger.info("XML reports {} ", reports);
			}

			Optional<Reports> csvReports = fileParsingService.parsingCSV(crvList);
			if (csvReports.isPresent()) {
				Reports csvReport = csvReports.get();
				logger.info("CSV reports {} ", csvReport);
				reports.getDuplicateRef().addAll(csvReport.getDuplicateRef());
				reports.getInvalidEndBanlance().addAll(csvReport.getInvalidEndBanlance());
			}
		} else {
			logger.debug("Not a CSV or XML file format");
			throw new RaboFileFormatException(RaboConstants.INVALID_CONTENT_TYPE, RaboConstants.FILE_IO_CODE);
		}

		return reports;
	}

}
