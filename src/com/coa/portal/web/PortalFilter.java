// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:41:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.portal.web;

import com.coa.portal.client.PortalPrincipal;
import com.coa.portal.client.PortalTicket;
import com.coa.portal.util.DecodeException;
import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

public abstract class PortalFilter implements Filter {

   protected static final String TICKET_NAME = "portalTicket";


   protected PortalPrincipal getPortalPrincipal(String applicationId, ServletRequest request) throws ServletException {
      String ticketInfo = request.getParameter("portalTicket");
      if(ticketInfo != null && ticketInfo.trim().length() >= 1) {
         try {
            return PortalTicket.decode(applicationId, ticketInfo);
         } catch (DecodeException var5) {
            throw new ServletException("Invalid portal ticket", var5);
         }
      } else {
         return null;
      }
   }
}
