// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:41:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.portal.client;

import com.coa.portal.client.PortalPrincipal;
import com.coa.portal.util.DecodeException;
import com.coa.portal.util.PortalDecoderStrategy;
import com.coa.portal.util.PortalEncoding;

public final class PortalTicket {

   public static final int APPLICATION_ID_LENGTH = 2;


   public static PortalPrincipal decode(String applicationId, String ticket) throws DecodeException {
      PortalEncoding encoding = PortalEncoding.getEncoding(ticket);
      PortalDecoderStrategy decoder = encoding.getDecoderStrategy();
      String applicationIdFromTicket = ticket.substring(0, 2);
      String encodedTicket = ticket.substring(2);
      if(!applicationId.equals(applicationIdFromTicket)) {
         throw new DecodeException("The applicationID in the portal ticket does not match the applicationID of the application requesting this decode.");
      } else {
         return decoder.decode(encodedTicket);
      }
   }

   public static String encode(PortalPrincipal principal, int minutesToExpiry) throws DecodeException {
      PortalEncoding encoding = principal.getApplication().getEncoding();
      PortalDecoderStrategy decoder = encoding.getDecoderStrategy();
      StringBuilder ticket = new StringBuilder(principal.getApplication().getId());
      ticket.append(decoder.encode(principal, minutesToExpiry));
      return ticket.toString();
   }
}
