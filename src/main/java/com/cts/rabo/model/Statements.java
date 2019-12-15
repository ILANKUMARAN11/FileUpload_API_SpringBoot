package com.cts.rabo.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * 
 * @author ilankumaran
 *
 */
@JsonInclude(Include.NON_NULL)
public class Statements {

	@JacksonXmlProperty(localName = "reference", isAttribute = true)
	private BigDecimal refrence;

	@JacksonXmlProperty(localName = "description")
	private String description;

	public Statements() {
	}

	public Statements(BigDecimal refrence, String description) {
		this.refrence = refrence;
		this.description = description;
	}

	public BigDecimal getRefrence() {
		return refrence;
	}

	public void setRefrence(BigDecimal refrence) {
		this.refrence = refrence;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
