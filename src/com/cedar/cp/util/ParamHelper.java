// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class ParamHelper {

   protected boolean throwExceptionOnError = true;
   protected String[] mArgs;
   private int mArgIndex;
   protected String[][] mParamSpec;
   private int mParamSpecIndex;
   protected boolean[] mArgMatched;
   private boolean mArgError = false;
   protected boolean mHelpRequired = false;
   private boolean mKeywordFound;
   private String mArgValue;
   protected String mFullKeyword;
   protected String mShortKeyword;
   private transient Log mLog = new Log(this.getClass());


   public void setParamSpec(String[][] paramSpec) {
      this.mParamSpec = new String[paramSpec.length + 1][];

      for(int w = 0; w < paramSpec.length; ++w) {
         System.arraycopy(paramSpec, 0, this.mParamSpec, 0, paramSpec.length);
      }

      this.mParamSpec[paramSpec.length] = new String[]{"-h{elp}", "+ or -", "help needed ?", "-"};
      this.mHelpRequired = this.getBoolean("-h");
      if(this.mHelpRequired) {
         this.outputParameterDetails(false);
         System.exit(0);
      }

   }

   private void checkParmSpec(String keyword) throws Exception {
      this.mArgValue = null;
      this.mKeywordFound = false;
      this.mParamSpecIndex = 0;
      boolean curlyIndex = true;
      boolean foundInSpec = false;

      for(this.mParamSpecIndex = 0; this.mParamSpecIndex < this.mParamSpec.length; ++this.mParamSpecIndex) {
         String specKeyword = this.mParamSpec[this.mParamSpecIndex][0];
         this.setLongAndShortNames(specKeyword);
         if(keyword.equals(this.mShortKeyword) || keyword.equals(this.mParamSpec[this.mParamSpecIndex][0]) || keyword.equals(this.mFullKeyword)) {
            foundInSpec = true;
            break;
         }
      }

      if(!foundInSpec) {
         throw new Exception("");
      }
   }

   protected void setLongAndShortNames(String keyword) {
      boolean curlyIndex = true;
      int curlyIndex1 = keyword.indexOf("{");
      if(curlyIndex1 == -1) {
         this.mFullKeyword = keyword;
         this.mShortKeyword = keyword;
      } else if(!keyword.endsWith("}")) {
         this.mFullKeyword = keyword;
         this.mShortKeyword = keyword;
      } else {
         this.mShortKeyword = keyword.substring(0, curlyIndex1);
         this.mFullKeyword = keyword.substring(0, curlyIndex1) + keyword.substring(curlyIndex1 + 1, keyword.length() - 1);
      }

   }

   private boolean findKeyword(String keyword) {
      try {
         this.checkParmSpec(keyword);
      } catch (Exception var4) {
         String msg = "*** error: parameter \"" + keyword + "\" " + " not defined in internal parameter spec";
         if(this.throwExceptionOnError) {
            throw new IllegalArgumentException(msg);
         }

         System.err.println(msg);
         this.mKeywordFound = false;
         this.mArgError = true;
         return false;
      }

      for(this.mArgIndex = 0; this.mArgIndex < this.mArgs.length; ++this.mArgIndex) {
         if(!this.mArgMatched[this.mArgIndex]) {
            String currArg = this.mArgs[this.mArgIndex];
            if(currArg != null) {
               if(currArg.startsWith(this.mFullKeyword)) {
                  this.mArgMatched[this.mArgIndex] = true;
                  this.mShortKeyword = this.mFullKeyword;
               } else if(!this.mFullKeyword.equals(this.mShortKeyword) && currArg.startsWith(this.mShortKeyword)) {
                  this.mArgMatched[this.mArgIndex] = true;
                  this.mFullKeyword = this.mShortKeyword;
               }

               if(this.mArgMatched[this.mArgIndex]) {
                  this.mArgValue = currArg.substring(this.mFullKeyword.length());
                  if(this.mArgValue.length() == 0) {
                     this.mArgValue = null;
                  }

                  this.mKeywordFound = true;
                  return true;
               }
            }
         }
      }

      this.mKeywordFound = false;
      return false;
   }

   public String getString(String keyword) {
      return this.getString(keyword, (String)null);
   }

   public String getString(String keyword, String keywordOnlyDefault) {
      this.findKeyword(keyword);
      String msg;
      if(this.mKeywordFound && this.mArgValue == null) {
         ++this.mArgIndex;
         if(this.mArgIndex < this.mArgs.length) {
            msg = this.mArgs[this.mArgIndex];
            if(!this.mArgMatched[this.mArgIndex] && msg.charAt(0) != 45) {
               this.mArgMatched[this.mArgIndex] = true;
               this.mArgValue = msg;
            }
         }
      }

      if(this.mArgValue == null && this.mKeywordFound && keywordOnlyDefault != null) {
         return keywordOnlyDefault;
      } else if(this.mKeywordFound && this.mArgValue != null) {
         return this.mArgValue;
      } else if(this.mParamSpecIndex >= this.mParamSpec.length) {
         return null;
      } else if(this.mParamSpec[this.mParamSpecIndex].length > 3) {
         msg = this.mParamSpec[this.mParamSpecIndex][3];
         return msg;
      } else {
         msg = "*** error: parameter \"" + this.mParamSpec[this.mParamSpecIndex][0] + "\" not specified";
         if(this.throwExceptionOnError) {
            throw new IllegalArgumentException(msg);
         } else {
            System.err.println(msg);
            this.mArgError = true;
            return null;
         }
      }
   }

   public boolean getBoolean(String keyword) {
      String v = this.getString(keyword, "+");
      v = v.trim();
      if(v.length() == 1) {
         if(v.charAt(0) == 43) {
            return true;
         }

         if(v.charAt(0) == 45) {
            return false;
         }
      }

      this.invalid("value must be +(true), -(false) or nothing (true)");
      return false;
   }

   public long getLong(String keyword) {
      String s = this.getString(keyword);
      if(s == null) {
         return 0L;
      } else {
         long argNumValue = 0L;

         try {
            argNumValue = (new Long(s)).longValue();
         } catch (NumberFormatException var6) {
            this.invalid(s + "is not a valid number");
         }

         return argNumValue;
      }
   }

   public int getInt(String keyword) {
      long retValue = this.getLong(keyword);
      return (int)retValue;
   }

   public File getInputFile(String keyword) {
      this.getString(keyword);
      if(this.mArgValue == null) {
         return null;
      } else {
         File f = new File(this.mArgValue);
         if(!f.isFile()) {
            this.invalid(this.mArgValue + " is not a file");
         }

         return f;
      }
   }

   public File getOutputFile(String keyword) {
      this.getString(keyword);
      if(this.mArgValue == null) {
         return null;
      } else {
         File f = new File(this.mArgValue);

         try {
            f.createNewFile();
         } catch (IOException var4) {
            this.invalid(this.mArgValue + " " + var4.getMessage());
         }

         return f;
      }
   }

   public String[] getStringArray(String keyword) {
      boolean firstTime = true;
      ArrayList foundValues = new ArrayList();

      while(true) {
         this.findKeyword(keyword);
         if(!this.mKeywordFound) {
            String[] var5 = new String[foundValues.size()];
            foundValues.toArray(var5);
            return var5;
         }

         if(this.mArgValue != null && !this.mArgValue.equals("")) {
            foundValues.add(this.mArgValue);
         }

         ++this.mArgIndex;

         while(this.mArgIndex < this.mArgs.length && !this.mArgMatched[this.mArgIndex]) {
            String returnArray = this.mArgs[this.mArgIndex];
            if(returnArray.charAt(0) == 45) {
               break;
            }

            this.mArgMatched[this.mArgIndex] = true;
            foundValues.add(returnArray);
            ++this.mArgIndex;
         }
      }
   }

   public void invalid(String text) {
      String msg = "*** error: parameter \"" + this.mFullKeyword + "\" " + text;
      if(this.throwExceptionOnError) {
         throw new IllegalArgumentException(msg);
      } else {
         System.err.println(msg);
         this.mArgError = true;
      }
   }

   public void error(String text) {
      String msg = "*** error: " + text;
      if(this.throwExceptionOnError) {
         throw new IllegalArgumentException(msg);
      } else {
         this.mArgError = true;
      }
   }

   private void checkIfAnyUnused() {
      for(this.mArgIndex = 0; this.mArgIndex < this.mArgs.length; ++this.mArgIndex) {
         if(!this.mArgMatched[this.mArgIndex]) {
            String currArg = this.mArgs[this.mArgIndex];
            if(currArg != null) {
               String msg = "*** error: parameter \"" + currArg + "\" not recognised";
               if(this.throwExceptionOnError) {
                  throw new IllegalArgumentException(msg);
               }

               System.err.println(msg);
               this.mArgError = true;
               this.mArgMatched[this.mArgIndex] = true;
            }
         }
      }

   }

   public boolean checkIfError() {
      this.checkIfAnyUnused();
      if(!this.mArgError && !this.mHelpRequired) {
         return false;
      } else {
         if(this.mHelpRequired) {
            this.outputParameterDetails(true);
         }

         return true;
      }
   }

   public void outputParameterDetails(boolean toStdErr) {
      this.outputLine(toStdErr);
      this.outputLine("Parameters:", toStdErr);
      int longestKeyword = 0;
      int longestType = 0;

      int w;
      for(w = 0; w < this.mParamSpec.length; ++w) {
         if(this.mParamSpec[w][0].length() > longestKeyword) {
            longestKeyword = this.mParamSpec[w][0].length();
         }

         if(this.mParamSpec[w][1].length() > longestType) {
            longestType = this.mParamSpec[w][1].length();
         }
      }

      for(w = 0; w < this.mParamSpec.length; ++w) {
         String keyword;
         for(keyword = this.mParamSpec[w][0]; keyword.length() < longestKeyword; keyword = " " + keyword) {
            ;
         }

         String type;
         for(type = this.mParamSpec[w][1]; type.length() < longestType; type = type + " ") {
            ;
         }

         String descr = this.mParamSpec[w][2];
         String defaultValue = "";
         if(this.mParamSpec[w].length > 3) {
            defaultValue = this.mParamSpec[w][3];
         }

         String descrLine = "";
         descrLine = keyword + " -- " + type + " -- " + descr;
         this.outputLine(descrLine, toStdErr);
         if(defaultValue == null) {
            defaultValue = "null";
         }

         if(!defaultValue.equals("")) {
            for(descrLine = ""; descrLine.length() < longestKeyword + longestType + 7; descrLine = descrLine + " ") {
               ;
            }

            descrLine = descrLine + " (default: " + defaultValue + ")";
            this.outputLine(descrLine, toStdErr);
         }
      }

   }

   private void outputLine(String row, boolean toStdErr) {
      if(toStdErr) {
         if(row != null) {
            System.err.println(row);
         } else {
            System.err.println();
         }
      } else if(row != null) {
         this.mLog.debug(row);
      } else {
         this.mLog.debug("");
      }

   }

   private void outputLine(boolean toStdErr) {
      this.outputLine((String)null, toStdErr);
   }

   public int getArgsSize() {
      return this.mArgs.length;
   }
}
