// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube.flatform;

import com.cedar.cp.ejb.base.cube.flatform.WorkbookUpdate;
import com.cedar.cp.ejb.base.cube.flatform.WorkbookUpdateRuleSet;
import java.io.Reader;
import org.apache.commons.digester.Digester;

public class WorkbookUpdateXMLReader {

   public static final int ERROR_LEVEL = 0;
   public static final int DEBUG_LEVEL = 1;
   private Digester mConfigDigester = null;
   private WorkbookUpdate mWorkbookUpdate;


   public void init() {
      this.initConfigDigester();
   }

   public void destroy() {
      this.mConfigDigester = null;
   }

   public void parseConfigFile(Reader input) throws Exception {
      this.mConfigDigester.parse(input);
   }

   protected Digester initConfigDigester() {
      if(this.mConfigDigester != null) {
         return this.mConfigDigester;
      } else {
         boolean validating = false;
         this.mConfigDigester = new Digester();
         this.mConfigDigester.setNamespaceAware(false);
         this.mConfigDigester.setValidating(validating);
         this.mConfigDigester.setUseContextClassLoader(true);
         this.mConfigDigester.addRuleSet(new WorkbookUpdateRuleSet());
         this.mConfigDigester.push(this.mWorkbookUpdate = new WorkbookUpdate());
         return this.mConfigDigester;
      }
   }

   public WorkbookUpdate getWorkbookUpdate() {
      return this.mWorkbookUpdate;
   }
}
