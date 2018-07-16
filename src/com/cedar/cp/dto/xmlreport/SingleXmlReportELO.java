// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlreport;

import com.cedar.cp.api.xmlreport.XmlReportRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SingleXmlReportELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"XmlReport"};
   private transient XmlReportRef mXmlReportEntityRef;
   private transient int mUserId;


   public SingleXmlReportELO() {
      super(new String[]{"XmlReport", "UserId"});
   }

   public void add(XmlReportRef eRefXmlReport, int col1) {
      ArrayList l = new ArrayList();
      l.add(eRefXmlReport);
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
      this.mXmlReportEntityRef = (XmlReportRef)l.get(index);
      this.mUserId = ((Integer)l.get(var4++)).intValue();
   }

   public XmlReportRef getXmlReportEntityRef() {
      return this.mXmlReportEntityRef;
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
