// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlimp;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.cedar.cp.ejb.impl.base.impexp.ImpExpUtils;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemDAO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemEVO;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemXMLDefLoader;
import com.cedar.cp.util.xlsimport.ExcelGenericImport;
import com.cedar.cp.util.xlsimport.ExcelGenericImportException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;

public class ExternalSystemLoaderEngine {

   public void loadExternalSystemDefinition(Connection connection, EntityRef externalSystemRef, String importSource, PrintWriter stdoutLogWriter, PrintWriter stderrLogWriter) throws ValidationException, Exception {
      ExternalSystemXMLDefLoader loader = new ExternalSystemXMLDefLoader();
      ExternalSystemDAO extSysDAO = new ExternalSystemDAO();
      ExternalSystemPK externalSystemPK = (ExternalSystemPK)externalSystemRef.getPrimaryKey();
      ExternalSystemEVO extSysEVO = extSysDAO.getDetails(externalSystemPK, "");
      if(extSysEVO == null) {
         throw new ValidationException("Failed to locate external system entry:" + externalSystemRef);
      } else {
         String src = importSource != null?importSource:extSysEVO.getImportSource();
         File tmpWorkFile = null;
         stdoutLogWriter.println("Importing external system defintion from : " + src);
         if(ImpExpUtils.isSpreadsheet(src)) {
            stdoutLogWriter.println("Converting from Excel format to XML...");
            tmpWorkFile = this.convertToXML(src, extSysEVO);
            src = tmpWorkFile.toURI().toURL().toExternalForm();
         }

         InputStreamReader reader = new InputStreamReader(this.getInputStream(src));

         try {
            loader.setAllConstraintsDeferred(false);
            loader.loadExternalSystem(reader, stdoutLogWriter, stderrLogWriter);
            reader.close();
            reader = null;
            if(importSource != null) {
               File f = new File((new URL(importSource.replace(" ", "%20"))).toURI());
               f.delete();
            }
         } finally {
            if(reader != null) {
               reader.close();
            }

            if(tmpWorkFile != null) {
               tmpWorkFile.delete();
            }

         }

      }
   }

   private InputStream getInputStream(String importSource) throws ValidationException {
      if(importSource != null && importSource.trim().length() != 0) {
         try {
            return new BufferedInputStream((new URL(importSource)).openStream());
         } catch (MalformedURLException var3) {
            throw new ValidationException("Malformed URL:" + importSource);
         } catch (IOException var4) {
            throw new ValidationException("File not found:" + importSource);
         }
      } else {
         throw new ValidationException("No import source.");
      }
   }

   private String queryWorkDir(String importSource) throws ValidationException {
      try {
         if(importSource != null) {
            URL io = new URL(importSource);
            if(io.getProtocol().startsWith("file")) {
               String dir = ImpExpUtils.extractDirectory(io);
               if(dir != null) {
                  File importSourceDir = new File(dir);
                  if(importSourceDir.exists()) {
                     return importSourceDir.getCanonicalPath();
                  }
               }
            }
         }

         return System.getProperty("user.home");
      } catch (MalformedURLException var5) {
         throw new ValidationException("External system\'s import source is an invalid URL:" + importSource);
      } catch (IOException var6) {
         throw new IllegalStateException("Failed to get canoncial path for external systems import source url:" + importSource);
      }
   }

   private File convertToXML(String srcURL, ExternalSystemEVO extSysEVO) throws ValidationException, IOException, ExcelGenericImportException {
      String workDir = this.queryWorkDir(extSysEVO.getImportSource());
      File outputFile = File.createTempFile("cpie", ".xml", new File(workDir));
      PrintStream ps = null;
      InputStream is = null;

      File var7;
      try {
         ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(outputFile)));
         is = this.getInputStream(srcURL);
         (new ExcelGenericImport()).processStream(is, ps);
         var7 = outputFile;
      } finally {
         if(ps != null) {
            ps.close();
         }

         if(is != null) {
            is.close();
         }

      }

      return var7;
   }
}
