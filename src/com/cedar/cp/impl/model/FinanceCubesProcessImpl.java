// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extsys.TransferMonitor;
import com.cedar.cp.api.model.FinanceCubeEditorSession;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.FinanceCubesProcess;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.api.model.FinanceCubeEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.FinanceCubeEditorSessionImpl;
import com.cedar.cp.impl.model.FinanceCubesProcessImpl$1;
import com.cedar.cp.impl.model.FinanceCubesProcessImpl$DataFileDetails;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Pair;
import com.cedar.cp.util.Timer;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FinanceCubesProcessImpl extends BusinessProcessImpl implements FinanceCubesProcess {

   private static final int FILE_TRANSFER_BLOCK_SIZE = 2048;
   private Log mLog = new Log(this.getClass());


   public FinanceCubesProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      FinanceCubeEditorSessionServer es = new FinanceCubeEditorSessionServer(this.getConnection());

      try {
         es.delete(primaryKey);
      } catch (ValidationException var5) {
         throw var5;
      } catch (CPException var6) {
         throw new RuntimeException("can\'t delete " + primaryKey, var6);
      }

      if(timer != null) {
         timer.logDebug("deleteObject", primaryKey);
      }

   }

   public FinanceCubeEditorSession getFinanceCubeEditorSession(Object key) throws ValidationException {
      FinanceCubeEditorSessionImpl sess = new FinanceCubeEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllFinanceCubes() {
      try {
         return this.getConnection().getListHelper().getAllFinanceCubes();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllFinanceCubes", var2);
      }
   }
   
   public EntityList getAllFinanceCubesForLoggedUser() {
	      try {
	         return this.getConnection().getListHelper().getAllFinanceCubesForLoggedUser();
	      } catch (Exception var2) {
	         var2.printStackTrace();
	         throw new RuntimeException("can\'t get AllFinanceCubes", var2);
	      }
	   }

   public EntityList getAllSimpleFinanceCubes() {
      try {
         return this.getConnection().getListHelper().getAllSimpleFinanceCubes();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllSimpleFinanceCubes", var2);
      }
   }

   public EntityList getAllDataTypesAttachedToFinanceCubeForModel(int param1) {
      try {
         return this.getConnection().getListHelper().getAllDataTypesAttachedToFinanceCubeForModel(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllDataTypesAttachedToFinanceCubeForModel", var3);
      }
   }

   public EntityList getFinanceCubesForModel(int param1) {
      try {
         return this.getConnection().getListHelper().getFinanceCubesForModel(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get FinanceCubesForModel", var3);
      }
   }

   public EntityList getFinanceCubeDimensionsAndHierachies(int param1) {
      try {
         return this.getConnection().getListHelper().getFinanceCubeDimensionsAndHierachies(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get FinanceCubeDimensionsAndHierachies", var3);
      }
   }

   public EntityList getFinanceCubeAllDimensionsAndHierachies(int param1) {
      try {
         return this.getConnection().getListHelper().getFinanceCubeAllDimensionsAndHierachies(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get FinanceCubeAllDimensionsAndHierachies", var3);
      }
   }

   public EntityList getAllFinanceCubesWeb() {
      try {
         return this.getConnection().getListHelper().getAllFinanceCubesWeb();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllFinanceCubesWeb", var2);
      }
   }

   public EntityList getAllFinanceCubesWebForModel(int param1) {
      try {
         return this.getConnection().getListHelper().getAllFinanceCubesWebForModel(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllFinanceCubesWebForModel", var3);
      }
   }

   public EntityList getAllFinanceCubesWebForUser(int param1) {
      try {
         return this.getConnection().getListHelper().getAllFinanceCubesWebForUser(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllFinanceCubesWebForUser", var3);
      }
   }

   public EntityList getFinanceCubeDetails(int param1) {
      try {
         return this.getConnection().getListHelper().getFinanceCubeDetails(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get FinanceCubeDetails", var3);
      }
   }

   public EntityList getFinanceCubesUsingDataType(short param1) {
      try {
         return this.getConnection().getListHelper().getFinanceCubesUsingDataType(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get FinanceCubesUsingDataType", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing FinanceCube";
      return ret;
   }

   protected int getProcessID() {
      return 12;
   }

   public FinanceCubeEditorSession getFinanceCubeEditorSession(int model_id, int fc_id) throws ValidationException {
      ModelPK modKey = new ModelPK(model_id);
      FinanceCubePK fcKey = new FinanceCubePK(fc_id);
      FinanceCubeCK cKey = new FinanceCubeCK(modKey, fcKey);
      FinanceCubeEditorSessionImpl sess = new FinanceCubeEditorSessionImpl(this, cKey);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public int issueRebuildTask(List<EntityRef> rebuildList) throws ValidationException {
      FinanceCubeEditorSessionServer es = new FinanceCubeEditorSessionServer(this.getConnection());
      return es.issueCubeRebuildTask(this.getConnection().getUserContext().getUserId(), rebuildList);
   }

   public int issueCheckIntegrityTask(List<EntityRef> checkIntegrityList) throws ValidationException {
      FinanceCubeEditorSessionServer es = new FinanceCubeEditorSessionServer(this.getConnection());
      return es.issueCheckIntegrityTask(this.getConnection().getUserContext().getUserId(), checkIntegrityList);
   }

   public int getFinanceCubeId(Object key) {
      if(key instanceof FinanceCubeRef) {
         key = ((FinanceCubeRef)key).getPrimaryKey();
      }

      if(key instanceof FinanceCubeCK) {
         key = ((FinanceCubeCK)key).getFinanceCubePK();
      }

      if(key instanceof FinanceCubePK) {
         return ((FinanceCubePK)key).getFinanceCubeId();
      } else {
         throw new IllegalArgumentException("Unexepected finance cube key type:" + key);
      }
   }

   public int issueCellCalcImportTask(URL url, TransferMonitor monitor) throws ValidationException {
      FinanceCubeEditorSessionServer ss = new FinanceCubeEditorSessionServer(this.getConnection());
      if(url != null) {
         URL serverURL = this.transferFileToServer(url, ss, monitor);
         return ss.issueCellCalcImportTask(url.toExternalForm(), serverURL.toExternalForm(), 0);
      } else {
         return -1;
      }
   }

   public int issueDynamicCellCalcImportTask(URL url, TransferMonitor monitor) throws ValidationException {
      FinanceCubeEditorSessionServer ss = new FinanceCubeEditorSessionServer(this.getConnection());
      if(url != null) {
         URL serverURL = this.transferFileToServer(url, ss, monitor);
         FinanceCubesProcessImpl$DataFileDetails dataFileDetails = this.scanForDataFile(url, 250);
         if(dataFileDetails != null) {
            dataFileDetails.mServerURL = this.transferFileToServer(dataFileDetails.mURL, ss, monitor);
         }

         ArrayList clientServerURLs = new ArrayList();
         clientServerURLs.add(new Pair(url.toExternalForm(), serverURL.toExternalForm()));
         if(dataFileDetails != null) {
            clientServerURLs.add(new Pair(dataFileDetails.mURL.toExternalForm(), dataFileDetails.mServerURL.toExternalForm()));
         }

         return ss.issueDynamicCellCalcImportTask(clientServerURLs, 0);
      } else {
         return -1;
      }
   }

   private URL transferFileToServer(URL url, FinanceCubeEditorSessionServer ss, TransferMonitor monitor) throws ValidationException {
      BufferedInputStream is = null;
      URL serverUrl = null;

      try {
         is = new BufferedInputStream(url.openStream());
         byte[] e = new byte[2048];
         int totalBytesTransferred = 0;
         boolean bytesRead = false;

         do {
            int bytesRead1;
            if((bytesRead1 = is.read(e)) <= 0) {
               URL data1 = serverUrl;
               return data1;
            }

            byte[] data = new byte[bytesRead1];
            System.arraycopy(e, 0, data, 0, bytesRead1);
            if(serverUrl == null) {
               serverUrl = ss.initiateTransfer(data);
            } else {
               ss.appendToFile(serverUrl, data);
            }

            totalBytesTransferred += data.length;
         } while(monitor == null || monitor.continueTransfer(totalBytesTransferred));

         throw new ValidationException("User terminated transfer.");
      } catch (IOException var18) {
         throw new ValidationException("Failed to transfer file:" + var18.getMessage());
      } finally {
         try {
            if(is != null) {
               is.close();
            }
         } catch (IOException var17) {
            throw new ValidationException("Failed to close input stream:" + var17.getMessage());
         }

      }
   }

   private FinanceCubesProcessImpl$DataFileDetails scanForDataFile(URL url, int maxLinesToScan) throws ValidationException {
      BufferedReader br = null;
      Object serverUrl = null;

      FinanceCubesProcessImpl$DataFileDetails var25;
      try {
         br = new BufferedReader(new FileReader(url.getFile()));
         String e = null;
         int linesRead = 0;
         int dataElementLine = -1;
         String csvFileName = null;
         String encoding = null;
         String excelFileName = null;

         label203: {
            do {
               do {
                  if((e = br.readLine()) == null || linesRead >= maxLinesToScan) {
                     break label203;
                  }

                  ++linesRead;
                  if(e.contains("<data ")) {
                     dataElementLine = linesRead;
                  }
               } while(dataElementLine == -1);
            } while(!e.contains("csvFile") && !e.contains("excelFile"));

            int dfd = e.indexOf("csvFile");
            Map excelFile = getAttributeValues(e, dfd);
            csvFileName = (String)excelFile.get("csvFile");
            encoding = (String)excelFile.get("encoding");
            excelFileName = (String)excelFile.get("excelFile");
         }

         if(csvFileName != null || excelFileName != null) {
            var25 = new FinanceCubesProcessImpl$DataFileDetails((FinanceCubesProcessImpl$1)null);
            File e1;
            String srcXMLFileDir;
            File var26;
            if(csvFileName != null) {
               var25.mCSV = true;
               var26 = new File(csvFileName);
               if(!var26.exists()) {
                  e1 = new File(url.getFile());
                  srcXMLFileDir = e1.getParent();
                  var26 = new File(srcXMLFileDir + (srcXMLFileDir.length() > 0 && srcXMLFileDir.charAt(srcXMLFileDir.length() - 1) != File.pathSeparatorChar?Character.valueOf(File.separatorChar):"") + var26.getName());
                  csvFileName = var26.getCanonicalPath();
               }

               var25.mURL = (new File(csvFileName)).toURI().toURL();
               var25.mEncoding = encoding;
            }

            if(excelFileName != null) {
               var26 = new File(excelFileName);
               if(!var26.exists()) {
                  e1 = new File(url.getFile());
                  srcXMLFileDir = e1.getParent();
                  var26 = new File(srcXMLFileDir + (srcXMLFileDir.length() > 0 && srcXMLFileDir.charAt(srcXMLFileDir.length() - 1) != File.pathSeparatorChar?Character.valueOf(File.separatorChar):"") + var26.getName());
                  excelFileName = var26.getCanonicalPath();
               }

               var25.mURL = (new File(excelFileName)).toURI().toURL();
            }

            FinanceCubesProcessImpl$DataFileDetails var27 = var25;
            return var27;
         }

         var25 = null;
      } catch (IOException var23) {
         throw new ValidationException("Failed to open file to check for additonal data files:" + var23.getMessage());
      } finally {
         try {
            if(br != null) {
               br.close();
            }
         } catch (IOException var22) {
            throw new ValidationException("Failed to close input stream:" + var22.getMessage());
         }

      }

      return var25;
   }

   private static Map<String, String> getAttributeValues(String line, int startIndex) {
      HashMap result = new HashMap();
      String regexp = "([^ =]+?)=\\\"([^\"]*?)\\\"|([^ =]+?)=([^ ]+)";
      Pattern pattern = Pattern.compile(regexp);
      Matcher matcher = pattern.matcher(line);

      while(matcher.find()) {
         String key = null;
         String value = null;

         for(int i = 1; i <= matcher.groupCount(); ++i) {
            String s = matcher.group(i);
            if(s != null) {
               if(i % 2 > 0) {
                  key = s;
               } else {
                  value = s;
               }
            }
         }

         if(key != null && value != null) {
            result.put(key, value);
         }
      }

      return result;
   }
}
