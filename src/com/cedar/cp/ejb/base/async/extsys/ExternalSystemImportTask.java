// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.extsys;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.extsys.ExternalSystemImportTaskRequest;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.extsys.ExternalSystemImportTask$1;
import com.cedar.cp.ejb.base.async.extsys.ExternalSystemImportTask$2;
import com.cedar.cp.ejb.base.async.extsys.ExternalSystemImportTask$ExternalSystemImportCheckpoint;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemLoaderEngine;
import com.cedar.cp.util.Log;
import java.io.PrintWriter;
import javax.naming.InitialContext;
import org.xml.sax.SAXException;

public class ExternalSystemImportTask extends AbstractTask {

   private transient InitialContext mInitialContext;
   private transient Log mLog = new Log(this.getClass());


   public int getReportType() {
      return 10;
   }

   public String getEntityName() {
      return "External System Definition Import";
   }

   public void runUnitOfWork(InitialContext initialContext) throws Exception {
      if(this.getCheckpoint() == null) {
         this.setCheckpoint(new ExternalSystemImportTask$ExternalSystemImportCheckpoint());
      }

      ExternalSystemRef extSysRef = ((ExternalSystemImportTaskRequest)this.getRequest()).getExternalSystemRef();
      String sourceURL = ((ExternalSystemImportTaskRequest)this.getRequest()).getSourceURL();
      ExternalSystemLoaderEngine engine = new ExternalSystemLoaderEngine();

      try {
         engine.loadExternalSystemDefinition(this.getConnection(), extSysRef, sourceURL, new PrintWriter(new ExternalSystemImportTask$1(this)), new PrintWriter(new ExternalSystemImportTask$2(this)));
      } catch (ValidationException var6) {
         throw new RuntimeException(var6);
      } catch (SAXException var7) {
         throw new RuntimeException(var7);
      } catch (CPException var8) {
         throw new RuntimeException(var8);
      }

      this.setCheckpoint((TaskCheckpoint)null);
   }
}
