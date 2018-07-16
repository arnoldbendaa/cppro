// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlreportfolder;

import com.cedar.cp.api.xmlreportfolder.XmlReportFolderRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllXmlReportFoldersELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"XmlReportFolder"};
   private transient XmlReportFolderRef mXmlReportFolderEntityRef;
   private transient int mUserId;


   public AllXmlReportFoldersELO() {
      super(new String[]{"XmlReportFolder", "UserId"});
   }

   public void add(XmlReportFolderRef eRefXmlReportFolder, int col1) {
      ArrayList l = new ArrayList();
      l.add(eRefXmlReportFolder);
      l.add(new Integer(col1));
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mXmlReportFolderEntityRef = (XmlReportFolderRef)l.get(index);
      this.mUserId = ((Integer)l.get(var4++)).intValue();
   }

   public XmlReportFolderRef getXmlReportFolderEntityRef() {
      return this.mXmlReportFolderEntityRef;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

}
