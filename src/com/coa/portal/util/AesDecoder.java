// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:41:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.portal.util;

import com.coa.encryption.aes.AESEncryption;
import com.coa.portal.client.PortalPrincipal;
import com.coa.portal.util.AbstractDecoderStrategy;
import com.coa.portal.util.DecodeException;
import com.coa.portal.util.PortalEncoding;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AesDecoder extends AbstractDecoderStrategy {

   private static final String THIS_ENCODING_TYPE = PortalEncoding.AES.getPrefix();


   public PortalPrincipal decode(String ticket) throws DecodeException {
      try {
         AESEncryption e = new AESEncryption();
         String decodedTicket = e.decryptString(ticket);
         return this.decodeStandardPortalTicket(decodedTicket);
      } catch (DecodeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new DecodeException("Unable to decode AES string. Exception message = " + var5.getMessage());
      }
   }

   public String encode(PortalPrincipal principal, int minutesToExpiry) throws DecodeException {
      String detailsToEncode = this.encodeStandardPortalPrincipal(principal, minutesToExpiry);
      AESEncryption encryption = new AESEncryption();
      String encryptedString = encryption.encryptString(detailsToEncode);

      try {
         return URLEncoder.encode(encryptedString, "UTF-8");
      } catch (UnsupportedEncodingException var7) {
         throw new DecodeException("Can\'t url encode", var7);
      }
   }

}
