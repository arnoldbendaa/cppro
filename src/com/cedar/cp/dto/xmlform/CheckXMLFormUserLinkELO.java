// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform;

import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.api.xmlform.XmlFormUserLinkRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckXMLFormUserLinkELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"XmlFormUserLink", "XmlForm"};
   private transient XmlFormUserLinkRef mXmlFormUserLinkEntityRef;
   private transient XmlFormRef mXmlFormEntityRef;
   private transient int mUserId;


   public CheckXMLFormUserLinkELO() {
      super(new String[]{"XmlFormUserLink", "XmlForm", "UserId"});
   }

   public void add(XmlFormUserLinkRef eRefXmlFormUserLink, XmlFormRef eRefXmlForm, int col1) {
      ArrayList l = new ArrayList();
      l.add(eRefXmlFormUserLink);
      l.add(eRefXmlForm);
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
      this.mXmlFormUserLinkEntityRef = (XmlFormUserLinkRef)l.get(index);
      this.mXmlFormEntityRef = (XmlFormRef)l.get(var4++);
      this.mUserId = ((Integer)l.get(var4++)).intValue();
   }

   public XmlFormUserLinkRef getXmlFormUserLinkEntityRef() {
      return this.mXmlFormUserLinkEntityRef;
   }

   public XmlFormRef getXmlFormEntityRef() {
      return this.mXmlFormEntityRef;
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
