// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SqlBuilder {

   private List<String> mLines = new ArrayList();


   public SqlBuilder() {}

   public SqlBuilder(String ... lines) {
      this.addLines(lines);
   }

   public void addLines(String ... lines) {
      if(lines != null) {
         String[] arr$ = lines;
         int len$ = lines.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String line = arr$[i$];
            this.mLines.add(line);
         }
      }

   }

   public void addLines(SqlBuilder sqlBuilder) {
      if(sqlBuilder != null) {
         Iterator i$ = sqlBuilder.getLines().iterator();

         while(i$.hasNext()) {
            String line = (String)i$.next();
            this.mLines.add(line);
         }
      }

   }

   public void addRepeatingLines(int repeats, String separator, String ... lines) {
      if(lines != null) {
         this.addLines(getRepeatingLines(repeats, separator, lines));
      }
   }

   public void addLine(String line) {
      if(line != null) {
         this.mLines.add(line);
      }

   }

   public static String[] getRepeatingLines(int repeats, String separator, String ... lines) {
      ArrayList localLines = new ArrayList();

      for(int repeat = 0; repeat < repeats; ++repeat) {
         String[] arr$ = lines;
         int len$ = lines.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String line = arr$[i$];
            line = line.replaceAll("\\$\\{index\\}", String.valueOf(repeat));
            line = line.replaceAll("\\$\\{num\\}", String.valueOf(repeat + 1));
            if(separator != null) {
               if(repeat == 0) {
                  line = line.replaceAll("\\$\\{separator\\}", fill(' ', separator.length()));
               } else {
                  line = line.replaceAll("\\$\\{separator\\}", separator);
               }
            }

            localLines.add(line);
         }
      }

      return (String[])localLines.toArray(new String[0]);
   }

   public void substitute(String ... substitutions) {
      for(int i = 0; i < this.mLines.size(); ++i) {
         String line = (String)this.mLines.get(i);

         for(int j = 0; j < substitutions.length; j += 2) {
            String keyword = this.adjustForRegex(substitutions[j]);
            String value = this.adjustForRegex(substitutions[j + 1]);
            if(value == null) {
               value = "";
            }

            line = line.replaceAll(keyword, value);
         }

         this.mLines.set(i, line);
      }

   }

   public void substitute(String repeatName, int repeatValue, String ... substitutions) {
      String[] subs = new String[substitutions.length];
      String repeatNameRegExp = this.adjustForRegex(repeatName);

      for(int i = 0; i < subs.length; i += 2) {
         subs[i] = substitutions[i].replaceAll(repeatNameRegExp, String.valueOf(repeatValue));
         subs[i + 1] = substitutions[i + 1];
      }

      this.substitute(subs);
   }

   public void substituteLines(String marker, String ... lines) {
      this.substituteLines(marker, new SqlBuilder(lines));
   }

   public void substituteLines(String marker, String separator, List lines) {
      ArrayList localLines = new ArrayList();

      for(int lineNum = 0; lineNum < lines.size(); ++lineNum) {
         String line = lines.get(lineNum).toString();
         line = line.replaceAll("\\$\\{index\\}", String.valueOf(lineNum));
         line = line.replaceAll("\\$\\{num\\}", String.valueOf(lineNum + 1));
         if(separator != null) {
            if(lineNum == 0) {
               line = line.replaceAll("\\$\\{separator\\}", fill(' ', separator.length()));
            } else {
               line = line.replaceAll("\\$\\{separator\\}", separator);
            }
         }

         localLines.add(line);
      }

      this.substituteLines(marker, (String[])localLines.toArray(new String[0]));
   }

   public void substituteRepeatingLines(String marker, int repeats, String separator, String ... lines) {
      this.substituteLines(marker, getRepeatingLines(repeats, separator, lines));
   }

   public void substituteLines(String marker, SqlBuilder sqlBuilder) {
      if(sqlBuilder == null) {
         this.substitute(new String[]{marker, null});
      } else {
         for(int i = 0; i < this.mLines.size(); ++i) {
            String line = (String)this.mLines.get(i);
            int ptr = line.indexOf(marker);
            if(ptr > -1) {
               this.mLines.remove(i);
               String linePrefix = line.substring(0, ptr);
               int insertIndex = i;

               for(int j = 0; j < sqlBuilder.getLines().size(); ++j) {
                  String insertLine = (String)sqlBuilder.getLines().get(j);
                  String newLine;
                  if(linePrefix.length() == 0) {
                     newLine = insertLine;
                  } else {
                     newLine = linePrefix + insertLine;
                  }

                  this.mLines.add(insertIndex++, newLine);
                  linePrefix = fill(' ', linePrefix.length());
               }

               i += sqlBuilder.getLines().size() - 1;
            }
         }

      }
   }

   public static String repeatString(String source, String separator, int repeats) {
      String line = "";

      for(int i = 0; i < repeats; ++i) {
         line = line + source;
         line = line.replaceAll("\\$\\{index\\}", String.valueOf(i));
         line = line.replaceAll("\\$\\{num\\}", String.valueOf(i + 1));
         if(i == 0) {
            line = line.replaceAll("\\$\\{separator\\}", "");
         } else {
            line = line.replaceAll("\\$\\{separator\\}", separator);
         }
      }

      return line;
   }

   public static String repeatString(String source, String separator, List substitutions) {
      return repeatString(source, separator, substitutions.toArray(new Object[0]));
   }

   public static String repeatString(String source, String separator, Object[] substitutions) {
      String line = "";

      for(int i = 0; i < substitutions.length; ++i) {
         line = line + source;
         line = line.replaceAll("\\$\\{index\\}", String.valueOf(i));
         line = line.replaceAll("\\$\\{num\\}", String.valueOf(i + 1));
         line = line.replaceAll("\\$\\{sub\\}", substitutions[i].toString());
         if(i == 0) {
            line = line.replaceAll("\\$\\{separator\\}", "");
         } else {
            line = line.replaceAll("\\$\\{separator\\}", separator);
         }
      }

      return line;
   }

   public List<String> getLines() {
      return this.mLines;
   }

   public String toString() {
      return this.toStringBuffer().toString();
   }

   public StringBuffer toStringBuffer() {
      StringBuffer sb = new StringBuffer();
      Iterator i$ = this.mLines.iterator();

      while(i$.hasNext()) {
         String line = (String)i$.next();
         if(line.trim().length() > 0) {
            if(sb.length() > 0) {
               sb.append("\n");
            }

            sb.append(line);
         }
      }

      return sb;
   }

   public StringBuilder toStringBuilder() {
      StringBuilder sb = new StringBuilder();
      Iterator i$ = this.mLines.iterator();

      while(i$.hasNext()) {
         String line = (String)i$.next();
         if(line.trim().length() > 0) {
            if(sb.length() > 0) {
               sb.append("\n");
            }

            sb.append(line);
         }
      }

      return sb;
   }

   private String adjustForRegex(String str) {
      if(str == null) {
         return null;
      } else {
         StringBuffer sb = new StringBuffer(str.length() + 6);

         for(int i = 0; i < str.length(); ++i) {
            String c = str.substring(i, i + 1);
            if(c.equals("$") || c.equals("{") || c.equals("}")) {
               sb.append("\\");
            }

            sb.append(c);
         }

         return sb.toString();
      }
   }

   private static String fill(char c, int num) {
      StringBuffer sb = new StringBuffer(num);

      for(int i = 0; i < num; ++i) {
         sb.append(c);
      }

      return sb.toString();
   }
}
