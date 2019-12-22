package com.cts.rabo.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * 
 * @author ilankumaran
 *
 */
public class StatementRecords {

	@JacksonXmlProperty(localName = "reference", isAttribute = true)
	private BigDecimal refrence;

	@JacksonXmlProperty(localName = "accountNumber")
	private String accountNo;

	@JacksonXmlProperty(localName = "description")
	private String description;

	@JacksonXmlProperty(localName = "startBalance")
	private BigDecimal startBalance;

	@JacksonXmlProperty(localName = "mutation")
	private BigDecimal mutation;

	@JacksonXmlProperty(localName = "endBalance")
	private BigDecimal endBalance;

	public StatementRecords(BigDecimal refrence, String accountNo, String description, BigDecimal startBalance,
			BigDecimal mutation, BigDecimal endBalance) {
		this.refrence = refrence;
		this.accountNo = accountNo;
		this.description = description;
		this.startBalance = startBalance;
		this.mutation = mutation;
		this.endBalance = endBalance;
	}

	public StatementRecords() {
		// default constructor
	}

	public BigDecimal getRefrence() {
		return refrence;
	}

	public void setRefrence(BigDecimal refrence) {
		this.refrence = refrence;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}

	public BigDecimal getMutation() {
		return mutation;
	}

	public void setMutation(BigDecimal mutation) {
		this.mutation = mutation;
	}

	public BigDecimal getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}

	public String toString() {
		return this.refrence + " , " + this.accountNo + " , " + this.description + " , " + this.startBalance + " , "
				+ this.mutation + " , " + this.endBalance;
	}

}
