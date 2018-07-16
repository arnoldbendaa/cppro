package com.cedar.cp.ejb.impl.xmlform.convert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DeflaterOutputStream;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.xmlform.convert.ConvertXlsToJsonTaskRequest;
import com.cedar.cp.ejb.base.common.util.HttpUtils;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.task.TaskMessageFactory;

public class ExcelIOEditorSessionSEJB extends AbstractSession {

    private static final long serialVersionUID = 3873714562444777749L;
    private transient Log mLog = new Log(this.getClass());
    private transient SessionContext mSessionContext;

    public void ejbCreate() throws EJBException {
    }

    public void ejbRemove() {
    }

    public void setSessionContext(SessionContext context) {
        this.mSessionContext = context;
    }

    public SessionContext getmessionContext() {
        return mSessionContext;
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    public int convertAllXlsToJsonTask(int userId) throws EJBException {
        try {
            ConvertXlsToJsonTaskRequest request = new ConvertXlsToJsonTaskRequest();
            int taskId = TaskMessageFactory.issueNewTask(new InitialContext(), false, request, userId, 0);
            this.mLog.debug("ExcelIO", "taskId=" + taskId);
            return taskId;
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException(e);
        }
    }

    @SuppressWarnings("deprecation")
    public String convertXlsToJson(URI address, byte[] bytes, String password, Boolean... flags) throws EJBException {
        try {
            String url = address.toString() + "/toJSON";
            
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            ContentBody cb = new ByteArrayBody(bytes, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "form.xls");
            entity.addPart("file", cb);

            HttpEntity responseEntity = HttpUtils.doPost(url, entity);
            String json = EntityUtils.toString(responseEntity, "UTF-8");
            responseEntity.consumeContent();
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException("Error converting xls to json!", e);
        }
    }

    public InputStream convertJsonToXls(URI serviceURI, String json, String password, Boolean... flags) throws EJBException, ValidationException {
        return convertJsonToExcel(serviceURI, json, password, "xls", flags);
    }

    public InputStream convertJsonToXlsx(URI serviceURI, String json, String password, Boolean... flags) throws EJBException, ValidationException {
        return convertJsonToExcel(serviceURI, json, password, "xlsx", flags);
    }

    @SuppressWarnings("deprecation")
    public InputStream convertJsonToExcel(URI serviceURI, String json, String password, String exportFileType, Boolean... flags) throws EJBException, ValidationException {
        try {
            String url = serviceURI.toString() + "/toExcel/" + exportFileType;
            byte[] compressJson = compress(json);
            
//            HttpPost post = new HttpPost(url);
//            post.setHeader("User-Agent", "Mozilla/5.0 ( compatible ) ");
//            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
//            urlParameters.add(new BasicNameValuePair("type", "application/json"));
//            urlParameters.add(new BasicNameValuePair("jsonString", json));
//            post.setEntity(new UrlEncodedFormEntity(urlParameters));
//
//            HttpClient client = new DefaultHttpClient();
//            HttpResponse response = client.execute(post);
//
//            // check response
//            StatusLine statusLine = response.getStatusLine();
//            if (statusLine.getStatusCode() != 200) {
//                throw new ValidationException("ExcelIO Service response: " + statusLine.getStatusCode() + " " + statusLine.getReasonPhrase());
//            }
//
//            return response.getEntity().getContent();

            
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            ContentBody cb = new ByteArrayBody(compressJson,"application/zip","json.zip");
            entity.addPart("file", cb);
            
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(entity);
            
            HttpEntity responseEntity = HttpUtils.doPost(url, entity);
            
            return responseEntity.getContent();
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException("Error converting json to xls!", e);
        }
    }

    private static byte[] compress(String text) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            OutputStream out = new DeflaterOutputStream(baos);
            out.write(text.getBytes("UTF-8"));
            out.close();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        return baos.toByteArray();
    }
}
