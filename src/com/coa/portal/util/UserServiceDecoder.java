// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:41:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.portal.util;

import com.coa.portal.client.PortalPrincipal;
import com.coa.portal.util.AbstractDecoderStrategy;
import com.coa.portal.util.DecodeException;
import com.coa.portal.util.crypto.Cryptography;

public class UserServiceDecoder extends AbstractDecoderStrategy {

   public PortalPrincipal decode(String ticket) throws DecodeException {
      String decodedTicket = Cryptography.decrypt(ticket, "cedar_secret");
      return this.decodeStandardPortalTicket(decodedTicket);
   }

   public String encode(PortalPrincipal principal, int minutesToExpiry) throws DecodeException {
      String ticket = this.encodeStandardPortalPrincipal(principal, minutesToExpiry);
      return Cryptography.encrypt(ticket, "cedar_secret");
   }
}
