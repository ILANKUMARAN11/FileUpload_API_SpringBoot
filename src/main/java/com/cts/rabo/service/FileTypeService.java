package com.cts.rabo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cts.rabo.model.Reports;

/**
 *
 * @author ilankumaran
 *
 */
public interface FileTypeService {

	/**
	 * 
	 * @param multipartFiles List of MultiPart File as XML and CSV.
	 * @return return reports of XML and CSV.
	 */
	Reports divideFileType(List<MultipartFile> multipartFiles);

}
