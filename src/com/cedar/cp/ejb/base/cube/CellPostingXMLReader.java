// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import com.cedar.cp.ejb.base.cube.CellPostingRuleSet;
import com.cedar.cp.ejb.base.cube.CubeUpdate;
import java.io.Reader;
import org.apache.commons.digester.Digester;

public class CellPostingXMLReader {

   public static final int ERROR_LEVEL = 0;
   public static final int DEBUG_LEVEL = 1;
   private Digester mConfigDigester = null;
   private CubeUpdate mCubeUpdate;


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
         this.mConfigDigester.addRuleSet(new CellPostingRuleSet());
         this.mConfigDigester.push(this.mCubeUpdate = new CubeUpdate());
         return this.mConfigDigester;
      }
   }

   public CubeUpdate getCubeUpdate() {
      return this.mCubeUpdate;
   }
}
