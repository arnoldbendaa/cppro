// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util;

import com.cedar.cp.tc.apps.metadataimpexp.util.digester.MetaDataXMLDigesterUtils;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.metadata.MetaData;
import com.cedar.cp.tc.apps.metadataimpexp.util.exception.InvalidImportfileFormatException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipServices {

   public static void zipDirectory(String srcDirectory, String desZipFile) throws IllegalArgumentException, IOException {
      File dir = new File(srcDirectory);
      if(!dir.isDirectory()) {
         throw new IllegalArgumentException(srcDirectory + " is not a directory");
      } else {
         String[] zipEntries = dir.list();
         zipFiles(zipEntries, zipEntries, desZipFile);
      }
   }

   public static void zipFiles(String[] srcFiles, String[] zipEntryFiles, String desZipFile) throws IllegalArgumentException, IOException {
      byte[] dataBuff = new byte[65536];
      ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(desZipFile));

      for(int i = 0; i < srcFiles.length; ++i) {
         File f = new File(srcFiles[i]);
         BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
         ZipEntry zipEntry = new ZipEntry(zipEntryFiles[i]);
         zipOut.putNextEntry(zipEntry);

         int byteRead;
         while((byteRead = bis.read(dataBuff)) != -1) {
            zipOut.write(dataBuff, 0, byteRead);
         }

         bis.close();
      }

      zipOut.close();
   }

   public static void zipFiles(Map<String, File> files, String desZipFile) throws IllegalArgumentException, IOException {
      String[] zipEntryFiles = new String[files.keySet().size()];
      files.keySet().toArray(zipEntryFiles);
      File[] srcFiles = new File[files.values().size()];
      files.values().toArray(srcFiles);
      zipFiles(srcFiles, zipEntryFiles, desZipFile);
   }

   public static void zipFiles(File[] srcFiles, String[] zipEntryFiles, String desZipFile) throws IllegalArgumentException, IOException {
      byte[] dataBuff = new byte[65536];
      ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(desZipFile));

      for(int i = 0; i < srcFiles.length; ++i) {
         BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFiles[i]));
         ZipEntry zipEntry = new ZipEntry(zipEntryFiles[i]);
         zipOut.putNextEntry(zipEntry);

         int byteRead;
         while((byteRead = bis.read(dataBuff)) != -1) {
            zipOut.write(dataBuff, 0, byteRead);
         }

         bis.close();
      }

      zipOut.close();
   }

   public static void verifyImportZipFile(de.schlichtherle.truezip.file.TFile zipFile) throws Exception {
      if(zipFile == null) {
         throw new InvalidImportfileFormatException("please select file to import");
      } else {
         String lookupXMLFilePath = zipFile.getAbsolutePath() + "/" + "lokuptbl.xml";
         String formsXMLFilePath = zipFile.getAbsolutePath() + "/" + "xmlforms.xml";
         MetaData metaData = MetaDataXMLDigesterUtils.parseMetaData(zipFile);
         if(metaData != null) {
            de.schlichtherle.truezip.file.TFile formFile;
            boolean isFormFileExist;
            Set xmlFormList;
            if(metaData.getLookupTableName() != null && metaData.getLookupTableName().trim().length() > 0) {
               formFile = new de.schlichtherle.truezip.file.TFile(lookupXMLFilePath);
               isFormFileExist = formFile != null && formFile.isFile();
               if(!isFormFileExist) {
                  throw new InvalidImportfileFormatException("LookupTable meta-data does not exist");
               }

               xmlFormList = metaData.getLookupTableExportFileNameList();
               checkDataFile(xmlFormList, zipFile);
            }

            if(metaData.getXmlFormFileName() != null && metaData.getXmlFormFileName().trim().length() > 0) {
               formFile = new de.schlichtherle.truezip.file.TFile(formsXMLFilePath);
               isFormFileExist = formFile != null && formFile.isFile();
               if(!isFormFileExist) {
                  throw new InvalidImportfileFormatException("XMLForm meta-data does not exist");
               }

               xmlFormList = metaData.getXmlFormDefFileNameList();
               checkDataFile(xmlFormList, zipFile);
            }
         }

      }
   }

   private static void checkDataFile(Set<String> fileNameList, de.schlichtherle.truezip.file.TFile zipFile) throws InvalidImportfileFormatException {
      if(fileNameList != null && fileNameList.size() > 0) {
         Iterator i$ = fileNameList.iterator();

         while(i$.hasNext()) {
            String filename = (String)i$.next();
            de.schlichtherle.truezip.file.TFile fileToCheck = new de.schlichtherle.truezip.file.TFile(zipFile.getAbsolutePath() + "/" + filename);
            if(fileToCheck == null || !fileToCheck.isFile()) {
               throw new InvalidImportfileFormatException("Some data files are missing");
            }
         }
      }

   }

   public static void main(String[] args) {}
}
