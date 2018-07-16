/*    */package com.cedar.cp.util.flatform.model;

/*    */
/*    */import java.util.HashMap;
/*    */
import java.util.Map;

/*    */
/*    */public enum CellErrorValue
/*    */{
	/* 11 */DIV_ZERO {public String getText() {return "#DIV/0!";}},
	/*    */
	/* 15 */VALUE {public String getText() {return "#VALUE!";}},
	/*    */
	/* 19 */NAME {public String getText() {return "#NAME?";}},
	/*    */
	/* 23 */NA {public String getText() {return "#N/A";}},
	/*    */
	/* 27 */REF {public String getText() {return "#REF!";}},
	/*    */
	/* 31 */NUM {public String getText() {return "#NUM!";}},
	/*    */
	/* 35 */NULL {public String getText() {return "#NULL!";}};

//	private String text;
//	CellErrorValue(String text){
//		this.text=text;
//	}
	
	/*    */
	/*    */private static Map<String, CellErrorValue> sErrors;

	/*    */
	/*    */public abstract String getText();

	/*    */
	/*    */public static boolean isErrorString(String value)
	/*    */{
	/* 49 */return sErrors.get(value.toUpperCase()) != null;
	/*    */}

	/*    */
	/*    */public static CellErrorValue getError(String s)
	/*    */{
	/* 59 */return (CellErrorValue) sErrors.get(s.toUpperCase());
	/*    */}

	/*    */static {
		/* 62 */sErrors = new HashMap();
		/*    */
		/* 65 */sErrors.put(DIV_ZERO.getText(), DIV_ZERO);
		/* 66 */sErrors.put(VALUE.getText(), VALUE);
		/* 67 */sErrors.put(NAME.getText(), NAME);
		/* 68 */sErrors.put(NA.getText(), NA);
		/* 69 */sErrors.put(REF.getText(), REF);
		/* 70 */sErrors.put(NUM.getText(), NUM);
		/* 71 */sErrors.put(NULL.getText(), NULL);
	/*    */}
	/*    */
}

/*
 * Location: /home/oracle/coa/cp.ear/cp.ear_orginal/cp-util.jar Qualified Name:
 * com.cedar.cp.util.flatform.model.CellErrorValue JD-Core Version: 0.6.0
 */