// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:41:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.portal.util;

import com.coa.portal.client.PortalPrincipal;
import com.coa.portal.util.AbstractDecoderStrategy;
import com.coa.portal.util.DecodeException;

public class NoneDecoder extends AbstractDecoderStrategy {

   public PortalPrincipal decode(String ticket) throws DecodeException {
      return this.decodeStandardPortalTicket(ticket);
   }

   public String encode(PortalPrincipal principal, int minutesToExpiry) throws DecodeException {
      return this.encodeStandardPortalPrincipal(principal, minutesToExpiry);
   }
}
