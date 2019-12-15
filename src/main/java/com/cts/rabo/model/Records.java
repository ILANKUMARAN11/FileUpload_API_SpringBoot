package com.cts.rabo.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 
 * @author ilankumaran
 *
 */
@JacksonXmlRootElement(localName = "records")
public class Records
{
	@JacksonXmlProperty(localName = "record")
	@JacksonXmlElementWrapper(useWrapping = false)
    private StatementRecords[] statementRecords;


    public StatementRecords[] getStatementRecords() {
		return statementRecords;
	}

	public void setStatementRecords(StatementRecords[] statementRecords) {
		this.statementRecords = statementRecords;
	}


	@Override
    public String toString()
    {
        return "ClassPojo [record = "+statementRecords.toString()+"]";
    }
}
	
