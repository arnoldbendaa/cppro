// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.api.base.CPFileWrapper;
import com.cedar.cp.utc.common.ResourceServlet;
import com.cedar.cp.util.db.DBAccessor;
import java.sql.ResultSet;
import java.sql.SQLException;

class ResourceServlet$ExtendedAttachmentHelper extends DBAccessor {

   protected CPFileWrapper mFileWrapper;
   // $FF: synthetic field
   final ResourceServlet this$0;


   public ResourceServlet$ExtendedAttachmentHelper(ResourceServlet var1) {
      this.this$0 = var1;
   }

   CPFileWrapper loadResource(int id) {
      StringBuffer query = new StringBuffer();
      query.append("select file_name, attatch from extended_attachment where extended_attachment_id = ? ");
      Object[] vars = new Object[]{Integer.valueOf(id)};
      this.executePreparedQuery(query.toString(), vars);
      return this.mFileWrapper;
   }

   protected void processResultSet(ResultSet rs) throws SQLException {
      while(rs != null && rs.next()) {
         String filename = rs.getString(1);
         this.mLog.debug("processResultSet", "Filename = " + filename);
         byte[] resource = this.blobToByteArray(rs.getBlob(2));
         this.mLog.debug("processResultSet", "we now have the data");
         this.mFileWrapper = new CPFileWrapper(resource, filename);
      }

   }
}
