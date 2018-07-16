// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:41:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.encryption;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Iterator;
import java.util.List;

public class KeyStoreParameters {

   private static KeyStoreParameters thisInstance = null;
   private String location;
   private String password;
   private String keyalias;


   public static synchronized KeyStoreParameters getInstance() {
      if(thisInstance == null) {
         thisInstance = new KeyStoreParameters();
      }

      return thisInstance;
   }

   public KeyStoreParameters() {
      RuntimeMXBean mxbean = ManagementFactory.getRuntimeMXBean();
      List inputArguments = mxbean.getInputArguments();
      if(inputArguments != null) {
         Iterator i$ = inputArguments.iterator();

         while(i$.hasNext()) {
            Object object = i$.next();
            String compareString = (String)object;
            if(compareString.indexOf("keystore.keyalias") != -1) {
               this.keyalias = this.theValueOf(compareString);
            } else if(compareString.indexOf("keystore.location") != -1) {
               this.location = this.theValueOf(compareString);
            } else if(compareString.indexOf("keystore.password") != -1) {
               this.password = this.theValueOf(compareString);
            }
         }
      }

   }

   private String theValueOf(String theString) {
      return theString.substring(theString.indexOf("=") + 1);
   }

   public String getLocation() {
      return this.location;
   }

   public String getPassword() {
      return this.password;
   }

   public String getKeyalias() {
      return this.keyalias;
   }

}
