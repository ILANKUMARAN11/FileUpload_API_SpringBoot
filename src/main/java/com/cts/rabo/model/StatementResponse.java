package com.cts.rabo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class StatementResponse {
	
	private List<CsvReport> csvReport;
	
	private List<XmlReport> xmlReport;
	
	public StatementResponse(){}

	@JsonProperty("CSV Reports")
	public List<CsvReport> getCsvReport() {
		return csvReport;
	}

	public void setCsvReport(List<CsvReport> csvReport) {
		this.csvReport = csvReport;
	}

	@JsonProperty("XML Reports")
	public List<XmlReport> getXmlReport() {
		return xmlReport;
	}

	public void setXmlReport(List<XmlReport> xmlReport) {
		this.xmlReport = xmlReport;
	}

	
	
	
	
	
	

}
