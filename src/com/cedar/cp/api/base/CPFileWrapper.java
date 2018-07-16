// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.base;

import com.cedar.cp.api.base.CPClientFileWrapper;
import java.io.File;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.ontoware.rdf2go.model.node.URI;
import org.semanticdesktop.aperture.mime.identifier.MimeTypeIdentifier;
import org.semanticdesktop.aperture.mime.identifier.magic.MagicMimeTypeIdentifier;

public class CPFileWrapper extends CPClientFileWrapper implements Serializable {

   private static MimeTypeIdentifier sMimeIdentifier;


   public CPFileWrapper(File f) {
      super(f);
   }

   public CPFileWrapper(byte[] data, String name) {
      super(data, name);
   }

   public String getMimeType(HttpServletRequest theRequest) {
      if(this.getMimeType() != null && this.getMimeType().length() > 0) {
         return this.getMimeType();
      } else {
         String result;
         try {
            result = sMimeIdentifier.identify(this.getData(), this.getName(), (URI)null);
         } catch (Throwable var5) {
            String fileName = this.getName();
            result = theRequest.getSession().getServletContext().getMimeType(fileName.toLowerCase());
         }

         if(result == null) {
            result = "text/plain";
         }

         this.setMimeType(result);
         return result;
      }
   }

   static {
      try {
         sMimeIdentifier = new MagicMimeTypeIdentifier();
      } catch (Throwable var1) {
         var1.printStackTrace();
      }

   }
}
