package com.cts.rabo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.cts.rabo.model.Reports;

/**
 * 
 * @author ilankumaran
 *
 */
public interface FileParsingService {


	/**
	 * 
	 * @param xmlLst List of MultiPart File as xmlList.
	 * @return return reports of XML.
	 */
	Optional<Reports> parsingXML(List<MultipartFile> xmlLst);

	/**
	 * 
	 * @param crvList List of MultiPart File as crvList.
	 * @return return reports of CSV.
	 */
	Optional<Reports> parsingCSV(List<MultipartFile> crvList);

}
