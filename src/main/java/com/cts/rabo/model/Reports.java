package com.cts.rabo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author ilankumaran
 *
 */
@JsonInclude(Include.NON_NULL)
public class Reports {


	@JsonInclude(Include.NON_EMPTY)
	private List<Statements> duplicateRef;

	@JsonInclude(Include.NON_EMPTY)
	private List<Statements> invalidEndBanlance;

	public Reports() {
		// default constructor
	}



	@JsonProperty("Duplicate Reference")
	public List<Statements> getDuplicateRef() {
		return duplicateRef;
	}

	public void setDuplicateRef(List<Statements> duplicateRef) {
		this.duplicateRef = duplicateRef;
	}

	@JsonProperty("Invalid End Balance")
	public List<Statements> getInvalidEndBanlance() {
		return invalidEndBanlance;
	}

	public void setInvalidEndBanlance(List<Statements> invalidEndBanlance) {
		this.invalidEndBanlance = invalidEndBanlance;
	}

	@Override
	public String toString() {
		return "Reports{" +
				" duplicateRef=" + duplicateRef.toString() +
				", invalidEndBanlance=" + invalidEndBanlance.toString() +
				'}';
	}
}
