// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.model.SecurityAccessExpressionParser;
import com.cedar.cp.dto.model.SecurityAccessTokenTypes;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecurityAccessExpressionLexer extends SecurityAccessTokenTypes {

   private Pattern mPattern;
   private Matcher mMatcher;
   private String mCurrentToken;
   private static final String sREGULAR_EXPRESSION = "([A-Z]|[a-z]|[0-9]|[_\\-@:])+|\\+|\\&|\\(|\\)|\\!";
   private Map mTokenToIdMap = new HashMap();


   public static void main(String[] args) {
      String input = "(t_4 + t5) & t1 ";
      System.out.println("Test lexer...");
      SecurityAccessExpressionLexer lexer = new SecurityAccessExpressionLexer(input);

      String token;
      while((token = lexer.next()) != null) {
         System.out.println("TOKEN[" + token + "]");
      }

      System.out.println("start to use peter\'s parser");
      SecurityAccessExpressionParser parser = new SecurityAccessExpressionParser(input);

      try {
         parser.parse();
      } catch (ParseException var6) {
         var6.printStackTrace();
      }

      System.out.println("---------------------------");
   }

   public SecurityAccessExpressionLexer(String input) {
      this.mTokenToIdMap.put("!", new Integer(1));
      this.mTokenToIdMap.put("+", new Integer(3));
      this.mTokenToIdMap.put("&", new Integer(2));
      this.mTokenToIdMap.put("+", new Integer(3));
      this.mTokenToIdMap.put("(", new Integer(4));
      this.mTokenToIdMap.put(")", new Integer(5));
      this.mPattern = Pattern.compile("([A-Z]|[a-z]|[0-9]|[_\\-@:])+|\\+|\\&|\\(|\\)|\\!");
      this.mMatcher = this.mPattern.matcher(input);
   }

   public String next() {
      return this.getNextToken();
   }

   private String getNextToken() {
      if(this.mMatcher != null && this.mMatcher.find()) {
         return this.mCurrentToken = this.mMatcher.group();
      } else {
         this.mMatcher = null;
         return this.mCurrentToken = null;
      }
   }

   public int getTokenType() {
      if(this.mCurrentToken == null) {
         return 6;
      } else {
         Integer type = (Integer)this.mTokenToIdMap.get(this.mCurrentToken);
         return type != null?type.intValue():0;
      }
   }
}
