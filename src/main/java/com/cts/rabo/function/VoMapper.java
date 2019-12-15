package com.cts.rabo.function;

import java.util.function.Function;

import com.cts.rabo.model.StatementRecords;
import com.cts.rabo.model.Statements;

/**
 * 
 * @author ilankumaran
 *
 */
public class VoMapper {

	/**
	 * StatementRecords to Response JSON POJO conversion.
	 */
	public Function<StatementRecords, Statements> entityToVo = new Function<StatementRecords, Statements>() {

		public Statements apply(StatementRecords t) {
			Statements requiredObj = new Statements();
			requiredObj.setRefrence(t.getRefrence());
			requiredObj.setDescription(t.getDescription());
			return requiredObj;
		}
	};

}
