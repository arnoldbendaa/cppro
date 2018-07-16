// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:41:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.encryption.portal;

import com.coa.encryption.EncryptionException;
import com.coa.encryption.aes.AESEncryption;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PortalTicketEncrypt {

   public static void main(String[] args) {
      if(args.length != 3 && args.length != 4) {
         System.out.println("Usage : java -jar coa-encryption.jar <password> <string> <action> [.jce file]");
         System.out.println("Where action is either ENCRYPT or DECRYPT");
      } else {
         String password = args[0];
         String stringToUse = args[1];
         String encryptionAction = args[2];
         String keystoreFile = args.length > 3?args[3]:"COASolutions.jce";
         AESEncryption encrypter = new AESEncryption();
         AESEncryption.setKeyAlias("aeskey");
         AESEncryption.setKeyPassword(password);
         AESEncryption.setKeyStoreLocation(keystoreFile);
         if(AESEncryption.isInitialised()) {
            try {
               if(encryptionAction.equals("ENCRYPT")) {
                  int uce = stringToUse.lastIndexOf(",");
                  String applicationId = stringToUse.substring(uce - 2, uce);
                  if(applicationId.startsWith(",")) {
                     applicationId = "0" + applicationId.substring(1);
                  }

                  System.out.print(URLEncoder.encode(applicationId + encrypter.encryptString(stringToUse), "UTF-8"));
               } else if(encryptionAction.equals("DECRYPT")) {
                  System.out.print(encrypter.decryptString(stringToUse));
               } else {
                  System.err.print("ERROR::Unkonwn encryption action, use ENCRYPT or DECRYPT");
               }
            } catch (EncryptionException var8) {
               var8.printStackTrace(System.err);
               System.err.print("ERROR::EncryptionException");
            } catch (UnsupportedEncodingException var9) {
               var9.printStackTrace(System.err);
               System.err.print("ERROR::EncryptionException");
            }
         } else {
            System.err.print("ERROR::Unable to initialise AESEncryption");
         }
      }

   }
}
