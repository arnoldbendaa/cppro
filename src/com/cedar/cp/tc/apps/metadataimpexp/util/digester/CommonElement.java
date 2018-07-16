// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util.digester;

import java.util.StringTokenizer;

public abstract class CommonElement {

   protected String mElementName;


   protected String replaceSpecialXMLCharacter(String input) {
      StringBuilder result = new StringBuilder(input.length());
      String delim = "&<>";
      StringTokenizer stringToken = new StringTokenizer(input, delim, true);

      while(stringToken.hasMoreTokens()) {
         String s = stringToken.nextToken();
         if(s.equals("&")) {
            result.append("<![CDATA[&]]>");
         } else if(s.equals(">")) {
            result.append("<![CDATA[>]]>");
         } else if(s.equals("<")) {
            result.append("<![CDATA[<]]>");
         } else {
            result.append(s);
         }
      }

      return result.toString();
   }
}
