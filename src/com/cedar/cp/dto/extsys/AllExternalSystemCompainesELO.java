// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.api.extsys.ExtSysCompanyRef;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllExternalSystemCompainesELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"ExtSysCompany", "ExternalSystem", "ExtSysLedger", "ExtSysDimension", "ExtSysDimElement", "ExtSysHierarchy", "ExtSysHierElement", "ExtSysHierElemFeed", "ExtSysValueType", "ExtSysCurrency", "ExtSysCalendarYear", "ExtSysCalElement"};
   private transient ExtSysCompanyRef mExtSysCompanyEntityRef;
   private transient ExternalSystemRef mExternalSystemEntityRef;
   private transient int mExternalSystemId;
   private transient String mDescription;
   private transient boolean mDummy;
   private transient int mImportColumnCalendarIndex;


   public AllExternalSystemCompainesELO() {
      super(new String[]{"ExtSysCompany", "ExternalSystem", "ExternalSystemId", "Description", "Dummy", "ImportColumnCalendarIndex"});
   }

   public void add(ExtSysCompanyRef eRefExtSysCompany, ExternalSystemRef eRefExternalSystem, int col1, String col2, boolean col3, int col4) {
      ArrayList l = new ArrayList();
      l.add(eRefExtSysCompany);
      l.add(eRefExternalSystem);
      l.add(new Integer(col1));
      l.add(col2);
      l.add(new Boolean(col3));
      l.add(new Integer(col4));
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
      this.mExtSysCompanyEntityRef = (ExtSysCompanyRef)l.get(index);
      this.mExternalSystemEntityRef = (ExternalSystemRef)l.get(var4++);
      this.mExternalSystemId = ((Integer)l.get(var4++)).intValue();
      this.mDescription = (String)l.get(var4++);
      this.mDummy = ((Boolean)l.get(var4++)).booleanValue();
      this.mImportColumnCalendarIndex = ((Integer)l.get(var4++)).intValue();
   }

   public ExtSysCompanyRef getExtSysCompanyEntityRef() {
      return this.mExtSysCompanyEntityRef;
   }

   public ExternalSystemRef getExternalSystemEntityRef() {
      return this.mExternalSystemEntityRef;
   }

   public int getExternalSystemId() {
      return this.mExternalSystemId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public boolean getDummy() {
      return this.mDummy;
   }

   public int getImportColumnCalendarIndex() {
      return this.mImportColumnCalendarIndex;
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
