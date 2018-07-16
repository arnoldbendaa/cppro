// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.CSVFileReader$CSVParser;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVFileReader implements Iterator {

   private LineNumberReader mLineNumberReader;
   private String mNextLine;
   private List<String> mTokens;
   private CSVFileReader$CSVParser mParser;


   public CSVFileReader(String filename) throws FileNotFoundException {
      this((Reader)(new InputStreamReader(new FileInputStream(filename))));
   }

   public CSVFileReader(URL fileURL, String encoding) throws IOException {
      this((Reader)(new InputStreamReader(fileURL.openStream(), encoding)));
   }

   public CSVFileReader(Reader r) {
      this.mTokens = new ArrayList();
      this.mParser = new CSVFileReader$CSVParser();
      this.mLineNumberReader = new LineNumberReader(r);
   }

   public void close() throws Exception {
      if(this.mLineNumberReader != null) {
         this.mLineNumberReader.close();
      }

   }

   public Iterator iterator() {
      return this;
   }

   public boolean hasNext() {
      try {
         if(this.mNextLine == null) {
            this.mNextLine = this.mLineNumberReader.readLine();
            return this.mNextLine != null;
         } else {
            return true;
         }
      } catch (IOException var2) {
         throw new RuntimeException(var2.getMessage(), var2);
      }
   }

   public Object next() {
      if(this.hasNext()) {
         String[] values = this.splitLine(this.mNextLine);
         this.mNextLine = null;
         return values;
      } else {
         return null;
      }
   }

   private String[] splitLine(String line) {
      this.mParser.init(line);
      this.mTokens.clear();

      while(this.mParser.hasMoreTokens()) {
         this.mTokens.add(this.mParser.nextToken());
      }

      return (String[])this.mTokens.toArray(new String[0]);
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   public static void main(String[] args) throws Exception {
      String line = "<data csvFile=\"c:\\projects\\myData.csv\" encoding=\"UTF-8\" />";
      String regexp = "([^ =]+?)=\\\"([^\"]*?)\\\"|([^ =]+?)=([^ ]+)";
      Pattern pattern = Pattern.compile(regexp);
      Matcher matcher = pattern.matcher(line);

      while(matcher.find()) {
         String key = null;
         String value = null;

         for(int i = 1; i <= matcher.groupCount(); ++i) {
            String s = matcher.group(i);
            if(s != null) {
               if(i % 2 > 0) {
                  key = s;
               } else {
                  value = s;
               }
            }
         }

         System.out.println("Key:[" + key + "] Value:[" + value + "]");
      }

   }
}
