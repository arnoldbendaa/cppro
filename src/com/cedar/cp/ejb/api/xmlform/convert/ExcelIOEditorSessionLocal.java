package com.cedar.cp.ejb.api.xmlform.convert;

import java.io.InputStream;
import java.net.URI;

import com.cedar.cp.api.base.ValidationException;

import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface ExcelIOEditorSessionLocal extends EJBLocalObject {

	int convertAllXlsToJsonTask(int userId) throws ValidationException, EJBException;

    String convertXlsToJson(URI address, byte[] bytes, String password, Boolean... flags) throws ValidationException, EJBException;

    InputStream convertJsonToXls(URI uri, String json, String password, Boolean[] flags) throws ValidationException, EJBException;
    
    InputStream convertJsonToXlsx(URI uri, String json, String password, Boolean[] flags) throws ValidationException, EJBException;
}
