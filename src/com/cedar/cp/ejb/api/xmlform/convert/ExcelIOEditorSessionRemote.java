package com.cedar.cp.ejb.api.xmlform.convert;

import com.cedar.cp.api.base.ValidationException;

import java.io.InputStream;
import java.net.URI;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ExcelIOEditorSessionRemote extends EJBObject {

	int convertAllXlsToJsonTask(int userId) throws ValidationException, RemoteException;

    String convertXlsToJson(URI address, byte[] bytes, String password, Boolean... flags) throws ValidationException, RemoteException;

    InputStream convertJsonToXls(URI uri, String json, String password, Boolean[] flags) throws ValidationException, RemoteException;

    InputStream convertJsonToXlsx(URI uri, String json, String password, Boolean[] flags) throws ValidationException, RemoteException;
}
