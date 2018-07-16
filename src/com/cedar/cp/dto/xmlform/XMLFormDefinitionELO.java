// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform;

import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class XMLFormDefinitionELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"XmlForm", "XmlFormUserLink", "DataEntryProfile"};
   private transient XmlFormRef mXmlFormEntityRef;
   private transient String mDescription;
   private transient String mDefinition;
   private transient int mFinanceCubeId;


   public XMLFormDefinitionELO() {
      super(new String[]{"XmlForm", "Description", "Definition", "FinanceCubeId"});
   }

   public void add(XmlFormRef eRefXmlForm, String col1, String col2, int col3) {
      ArrayList l = new ArrayList();
      l.add(eRefXmlForm);
      l.add(col1);
      l.add(col2);
      l.add(new Integer(col3));
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
      this.mXmlFormEntityRef = (XmlFormRef)l.get(index);
      this.mDescription = (String)l.get(var4++);
      this.mDefinition = (String)l.get(var4++);
      this.mFinanceCubeId = ((Integer)l.get(var4++)).intValue();
   }

   public XmlFormRef getXmlFormEntityRef() {
      return this.mXmlFormEntityRef;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getDefinition() {
      return this.mDefinition;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
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