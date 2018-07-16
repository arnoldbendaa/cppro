// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlreport.reader;

import com.cedar.cp.util.xmlreport.ReportConfig;
import com.cedar.cp.util.xmlreport.reader.ConfigRuleSet;
import java.io.Reader;
import org.apache.commons.digester.Digester;

public class XMLReader {

   public static final int ERROR_LEVEL = 0;
   public static final int DEBUG_LEVEL = 1;
   private Digester mConfigDigester = null;
   private ReportConfig mReportConfig = null;


   public void init(String reportId) {
      this.mReportConfig = new ReportConfig(reportId);
      this.initConfigDigester();
   }

   public ReportConfig getReportConfig() {
      return this.mReportConfig;
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
         this.mConfigDigester.push(this.mReportConfig);
         this.mConfigDigester.setNamespaceAware(true);
         this.mConfigDigester.setValidating(validating);
         this.mConfigDigester.setUseContextClassLoader(true);
         this.mConfigDigester.addRuleSet(new ConfigRuleSet());
         return this.mConfigDigester;
      }
   }
}
