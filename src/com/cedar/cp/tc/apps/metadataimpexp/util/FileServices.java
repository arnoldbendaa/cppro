// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class FileServices {

	/**
	 * Write bytes data to file
	 * 
	 * @param file
	 * @param data
	 * @throws IOException
	 */
	public static void writeDataToFile(File file, byte[] data) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
		bufferedOutputStream.write(data);
		bufferedOutputStream.flush();
		bufferedOutputStream.close();
	}

   public static void writeDataToFile(File file, String data) throws IOException {
      PrintWriter printWriter = new PrintWriter(new FileWriter(file));
      BufferedWriter buffWriter = new BufferedWriter(printWriter);
      buffWriter.write(data);
      buffWriter.flush();
      if(printWriter.checkError()) {
         throw new IOException();
      } else {
         if(printWriter != null) {
            printWriter.close();
            buffWriter.close();
         }

      }
   }

   public static void writeDataToFile(String fileName, String data) throws IOException {
      File file = new File(fileName);
      writeDataToFile(file, data);
   }

   public static File createTemplateFile(String fileName) throws IOException {
      File f = File.createTempFile(fileName, "");
      f.deleteOnExit();
      return f;
   }
}
