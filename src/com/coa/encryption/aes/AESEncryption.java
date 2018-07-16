// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:41:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.encryption.aes;

import com.coa.encryption.EncryptionException;
import com.coa.encryption.KeyStoreParameters;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.SecretKeyEntry;
import java.security.cert.CertificateException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class AESEncryption {

   private static Cipher aesCipher;
   private static SecretKey aesKey;
   private static String sKeyPassword;
   private static String sKeyStoreLocation;
   private static String sKeyAlias;
   private static boolean initialised = false;


   public AESEncryption() throws EncryptionException {
      try {
         aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
         if(aesCipher == null) {
            throw new EncryptionException("Cipher has evaluated to null");
         } else {
            KeyStoreParameters nsae = new KeyStoreParameters();
            setKeyPassword(nsae.getPassword());
            setKeyStoreLocation(nsae.getLocation());
            setKeyAlias(nsae.getKeyalias());
            setKeyStoreCredentials();
            if(isInitialised()) {
               setKey();
            }

         }
      } catch (NoSuchPaddingException var2) {
         throw new EncryptionException("Invalid Padding specified", var2);
      } catch (NoSuchAlgorithmException var3) {
         throw new EncryptionException("Invalid Algorithm specified", var3);
      }
   }

   private byte[] encrypt(byte[] toEncrypt) throws EncryptionException {
      try {
         aesCipher.init(1, aesKey);
         byte[] bpe = aesCipher.doFinal(toEncrypt);
         return bpe;
      } catch (InvalidKeyException var3) {
         throw new EncryptionException("Invalid cipher key detected", var3);
      } catch (IllegalBlockSizeException var4) {
         throw new EncryptionException("Illegal Block Size detected", var4);
      } catch (BadPaddingException var5) {
         throw new EncryptionException("Bad Padding detected", var5);
      }
   }

   private byte[] decrypt(byte[] toDecrypt) throws EncryptionException {
      try {
         aesCipher.init(2, aesKey);
         byte[] bpe = aesCipher.doFinal(toDecrypt);
         return bpe;
      } catch (InvalidKeyException var3) {
         throw new EncryptionException("Invalid cipher key detected", var3);
      } catch (IllegalBlockSizeException var4) {
         throw new EncryptionException("Illegal Block Size detected", var4);
      } catch (BadPaddingException var5) {
         throw new EncryptionException("Bad Padding detected", var5);
      }
   }

   public String encryptString(String stringToEncrypt) throws EncryptionException {
      byte[] cleartext = stringToEncrypt.getBytes();
      byte[] encryptedText = this.encrypt(cleartext);
      return DatatypeConverter.printBase64Binary(encryptedText);
   }

   public String decryptString(String stringToDecrypt) throws EncryptionException {
      try {
         byte[] ioe = DatatypeConverter.parseBase64Binary(stringToDecrypt);
         byte[] clearText = this.decrypt(ioe);
         return new String(clearText);
      } catch(Exception e){
    	  throw new EncryptionException("Exception occurred decoding buffer", e);
      }
   }

   public byte[] encryptByteArray(byte[] arrayToEncrypt) throws EncryptionException {
      return this.encrypt(arrayToEncrypt);
   }

   public byte[] decryptByteArray(byte[] arrayToDecrypt) throws EncryptionException {
      return this.decrypt(arrayToDecrypt);
   }

   public static void setKeyPassword(String keyPassword) {
      sKeyPassword = keyPassword;
      setKeyStoreCredentials();
   }

   public static void setKeyStoreLocation(String keyStoreLocation) {
      sKeyStoreLocation = keyStoreLocation;
      setKeyStoreCredentials();
   }

   public static void setKeyAlias(String keyAlias) {
      sKeyAlias = keyAlias;
      setKeyStoreCredentials();
   }

   public static boolean isInitialised() {
      return initialised;
   }

   private static void setInitialised(boolean initialised) {
      initialised = initialised;
   }

   private static void setKeyStoreCredentials() {
      boolean passwordOK = false;
      boolean keyStoreLocationOK = false;
      boolean keyAliasOK = false;
      if(sKeyPassword != null && !sKeyPassword.equals("")) {
         passwordOK = true;
      }

      if(sKeyStoreLocation != null && !sKeyStoreLocation.equals("")) {
         keyStoreLocationOK = true;
      }

      if(sKeyAlias != null && !sKeyAlias.equals("")) {
         keyAliasOK = true;
      }

      if(passwordOK && keyStoreLocationOK && keyAliasOK) {
         setInitialised(true);
         setKey();
      } else {
         setInitialised(false);
      }

   }

   public static void setKey() throws EncryptionException {
      if(isInitialised()) {
         try {
            KeyStore uee = KeyStore.getInstance("JCEKS");
            FileInputStream in = new FileInputStream(sKeyStoreLocation);
            char[] passwd = sKeyPassword.toCharArray();
            uee.load(in, passwd);
            new SecretKeyEntry(new SecretKeySpec(sKeyPassword.getBytes(), "JCEKS"));
            SecretKeyEntry skEntry = (SecretKeyEntry)uee.getEntry(sKeyAlias, new PasswordProtection(passwd));
            aesKey = skEntry.getSecretKey();
         } catch (KeyStoreException var4) {
            throw new EncryptionException("Unable to get an instance of a JCEKS KeyStore", var4);
         } catch (FileNotFoundException var5) {
            throw new EncryptionException("Unable to open a FileInputStream to KeyStore location", var5);
         } catch (IOException var6) {
            throw new EncryptionException("Unable to load KeyStore", var6);
         } catch (CertificateException var7) {
            throw new EncryptionException("CertificateExcpetion raised when loading KeyStore", var7);
         } catch (NoSuchAlgorithmException var8) {
            throw new EncryptionException("NoSuchAlgorithmException raised when loading KeyStore", var8);
         } catch (UnrecoverableEntryException var9) {
            throw new EncryptionException("Unable to recover Entry from KeyStore", var9);
         }
      }

   }

}
