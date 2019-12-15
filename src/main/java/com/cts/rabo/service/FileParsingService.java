package com.cts.rabo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cts.rabo.model.CsvReport;
import com.cts.rabo.model.StatementResponse;
import com.cts.rabo.model.XmlReport;


/**
 * 
 * @author ilankumaran
 *
 */
public interface FileParsingService {
	
	/**
	 * 
	 * @param multipartFiles Splitting XML and CSV. 
	 * @return Duplicate reference and invalid end balance from XML and CSV.
	 */
	StatementResponse divideFileType(List<MultipartFile> multipartFiles);
	
	/**
	 * 
	 * @param xmlLst List of XML files parsing.
	 * @return Duplicate reference and invalid end balance from XML.
	 */
	List<XmlReport> parsingXML(List<MultipartFile> xmlLst);
	
	/**
	 * 
	 * @param crvList List of CSV files parsing.
	 * @return Duplicate reference and invalid end balance from CSV.
	 */
	List<CsvReport> parsingCSV(List<MultipartFile> crvList);

}
