package com.cedar.cp.api.xmlform.convert;

import java.io.InputStream;
import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.ValidationException;

public interface ExcelIOProcess extends BusinessProcess {

	int convertAllXlsToJsonTask() throws ValidationException;
	
	String convertXlsToJson(byte[] bytes, String password, Boolean... flags) throws ValidationException;
	
	InputStream convertJsonToXls(String json, String password, Boolean... flags) throws ValidationException;
	
	InputStream convertJsonToXlsx(String json, String password, Boolean... flags) throws ValidationException;
}
