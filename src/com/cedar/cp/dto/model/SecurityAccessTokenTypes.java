// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import java.util.HashMap;
import java.util.Map;

public class SecurityAccessTokenTypes {

   public static final int TOKEN_IDENTIFIER = 0;
   public static final int TOKEN_NOT = 1;
   public static final int TOKEN_AND = 2;
   public static final int TOKEN_OR = 3;
   public static final int TOKEN_OPEN_PARENTHESIS = 4;
   public static final int TOKEN_CLOSE_PARENTHESIS = 5;
   public static final int TOKEN_END = 6;
   private Map mIdToTokenMap = new HashMap();


   public SecurityAccessTokenTypes() {
      this.mIdToTokenMap.put(new Integer(0), "Range-Identifier");
      this.mIdToTokenMap.put(new Integer(1), "!");
      this.mIdToTokenMap.put(new Integer(2), "&");
      this.mIdToTokenMap.put(new Integer(3), "+");
      this.mIdToTokenMap.put(new Integer(4), "(");
      this.mIdToTokenMap.put(new Integer(5), ")");
      this.mIdToTokenMap.put(new Integer(6), "<END>");
   }

   public String getTokenText(int tokenType) {
      return (String)this.mIdToTokenMap.get(new Integer(tokenType));
   }
}
