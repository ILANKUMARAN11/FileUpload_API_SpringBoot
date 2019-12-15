package com.cts.rabo.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class StatementRecords {
	
	
	@JacksonXmlProperty(localName = "reference", isAttribute = true)
	private BigDecimal refrence;
	
	@JacksonXmlProperty(localName = "accountNumber")
	private String accountNo;
	
	@JacksonXmlProperty(localName = "description")
	private String description;
	
	@JacksonXmlProperty(localName = "startBalance")
	private Double startBalance;
	
	@JacksonXmlProperty(localName = "mutation")
	private Double mutation;
	
	@JacksonXmlProperty(localName = "endBalance")
	private Double endBalance;
	
	
	public StatementRecords(BigDecimal refrence,String accountNo,String description,Double startBalance,Double mutation,Double endBalance){
		this.refrence=refrence;
		this.accountNo=accountNo;
		this.description=description;
		this.startBalance=startBalance;
		this.mutation=mutation;
		this.endBalance=endBalance;
	}
	
	public StatementRecords(){}

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

	public Double getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(Double startBalance) {
		this.startBalance = startBalance;
	}

	public Double getMutation() {
		return mutation;
	}

	public void setMutation(Double mutation) {
		this.mutation = mutation;
	}

	public Double getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(Double endBalance) {
		this.endBalance = endBalance;
	}

	
	public String toString() {
		return this.refrence+" , "+this.accountNo+" , "+this.description+" , "+this.startBalance+" , "+this.mutation+" , "+this.endBalance;
	}
	
	

}
