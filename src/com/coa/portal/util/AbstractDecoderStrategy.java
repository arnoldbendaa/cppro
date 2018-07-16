// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:41:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.portal.util;

import com.coa.portal.client.Application;
import com.coa.portal.client.PortalPrincipal;
import com.coa.portal.util.DecodeException;
import com.coa.portal.util.EncodingExpiredException;
import com.coa.portal.util.PortalDecoderStrategy;
import com.coa.portal.util.PortalPrincipalImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;

public abstract class AbstractDecoderStrategy implements PortalDecoderStrategy {

   protected static final SimpleDateFormat sDateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
   protected static final String DELIMETER = ",";


   public PortalPrincipal decodeStandardPortalTicket(String ticket) throws DecodeException {
      String[] tokens = ticket.split(",");
      if(tokens.length < 4) {
         throw new DecodeException("Not enough tokens in encoded content");
      } else {
         try {
            String e = tokens[0];
            String applicationUserId = tokens[1];
            String applicationPassword = tokens[2];
            String app = tokens[3];
            if(tokens.length > 4) {
               String application = tokens[4];
               this.checkExpiry(application);
            }

            if(app != null && app.length() == 1) {
               app = "0" + app;
            }

            Application application1 = Application.getApplication(app);
            return new PortalPrincipalImpl(e, applicationUserId, applicationPassword, application1);
         } catch (NoSuchElementException var8) {
            throw new DecodeException("Unable to parse decoded string correctly, number of tokens expected was 3");
         } catch (NumberFormatException var9) {
            throw new DecodeException("Unable to determine application or timestamp from decoded string");
         }
      }
   }

   public String encodeStandardPortalPrincipal(PortalPrincipal principal, int minutesToExpiry) throws DecodeException {
      StringBuilder toEncode = new StringBuilder();
      toEncode.append(principal.getPortalUserId());
      toEncode.append(",");
      toEncode.append(principal.getApplicationUserId());
      toEncode.append(",");
      toEncode.append(principal.getApplicationPassword());
      toEncode.append(",");
      toEncode.append(principal.getApplication().getId());
      if(minutesToExpiry > 0) {
         toEncode.append(",");
         toEncode.append(this.addExpiryTime(minutesToExpiry));
      }

      return toEncode.toString();
   }

   protected void checkExpiry(String expiryTimestampStr) throws DecodeException {
      try {
         Date pe = sDateFormatter.parse(expiryTimestampStr);
         Date now = new Date();
         if(now.after(pe)) {
            throw new EncodingExpiredException("The encoding has passed its expiry date of " + pe);
         }
      } catch (ParseException var4) {
         throw new DecodeException("Invalid expiry date format in encoded string = " + expiryTimestampStr);
      }
   }

   protected String addExpiryTime(int minutesToExpiry) {
      GregorianCalendar gc = new GregorianCalendar();
      gc.add(12, minutesToExpiry);
      return sDateFormatter.format(gc.getTime());
   }

}
