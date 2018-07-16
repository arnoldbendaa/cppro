// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:41:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.portal.util;

import com.coa.portal.client.PortalPrincipal;
import com.coa.portal.util.AbstractDecoderStrategy;
import com.coa.portal.util.DecodeException;
import com.coa.portal.util.crypto.libSquirrel;

public class SquirrelDecoder extends AbstractDecoderStrategy {

   public PortalPrincipal decode(String ticket) throws DecodeException {
      try {
         String e = libSquirrel.unSquirrel(ticket);
         return this.decodeStandardPortalTicket(e);
      } catch (DecodeException var3) {
         throw var3;
      } catch (Exception var4) {
         throw new DecodeException("Unable to decode squirrel string. Exception message = " + var4.getMessage());
      }
   }

   public String encode(PortalPrincipal principal, int minutesToExpiry) throws DecodeException {
      String detailsToEncode = this.encodeStandardPortalPrincipal(principal, minutesToExpiry);
      int requiredEncryptionLength = detailsToEncode.length() * 2 + 20;
      return libSquirrel.squirrel(detailsToEncode, requiredEncryptionLength);
   }
}
