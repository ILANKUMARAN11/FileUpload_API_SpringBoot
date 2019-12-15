package com.cts.rabo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cts.rabo.model.StatementResponse;
import com.cts.rabo.service.FileParsingService;

@RestController
@RequestMapping("/customer")
public class Statementcontroller {

	private static Logger LOGGER = LoggerFactory.getLogger(Statementcontroller.class);

	@Autowired
	FileParsingService fileParsingService;

	/**
	 * 
	 * @param files List of multiple XML or CSV files as input to API.
	 * @return Duplicate reference number and invalid end balance Report as Response.
	 */
	@PostMapping(value = "/multi/statements/record.rabo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<StatementResponse> uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files) {
		
		LOGGER.debug("processing inside Controller"); 

		return ResponseEntity.ok(fileParsingService.divideFileType(files));
	}

}
