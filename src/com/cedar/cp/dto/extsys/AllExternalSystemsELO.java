// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllExternalSystemsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"ExternalSystem", "ExtSysCompany", "ExtSysLedger", "ExtSysDimension", "ExtSysDimElement", "ExtSysHierarchy", "ExtSysHierElement", "ExtSysHierElemFeed", "ExtSysValueType", "ExtSysCurrency", "ExtSysCalendarYear", "ExtSysCalElement", "ExtSysProperty", "MappedModel"};
   private transient ExternalSystemRef mExternalSystemEntityRef;
   private transient int mSystemType;
   private transient String mVisId;
   private transient String mDescription;
   private transient String mLocation;
   private transient String mImportSource;
   private transient String mExportTarget;
   private transient boolean mEnabled;


   public AllExternalSystemsELO() {
      super(new String[]{"ExternalSystem", "SystemType", "VisId", "Description", "Location", "ImportSource", "ExportTarget", "Enabled"});
   }

   public void add(ExternalSystemRef eRefExternalSystem, int col1, String col2, String col3, String col4, String col5, String col6, boolean col7) {
      ArrayList l = new ArrayList();
      l.add(eRefExternalSystem);
      l.add(new Integer(col1));
      l.add(col2);
      l.add(col3);
      l.add(col4);
      l.add(col5);
      l.add(col6);
      l.add(new Boolean(col7));
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
      this.mExternalSystemEntityRef = (ExternalSystemRef)l.get(index);
      this.mSystemType = ((Integer)l.get(var4++)).intValue();
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mLocation = (String)l.get(var4++);
      this.mImportSource = (String)l.get(var4++);
      this.mExportTarget = (String)l.get(var4++);
      this.mEnabled = ((Boolean)l.get(var4++)).booleanValue();
   }

   public ExternalSystemRef getExternalSystemEntityRef() {
      return this.mExternalSystemEntityRef;
   }

   public int getSystemType() {
      return this.mSystemType;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getLocation() {
      return this.mLocation;
   }

   public String getImportSource() {
      return this.mImportSource;
   }

   public String getExportTarget() {
      return this.mExportTarget;
   }

   public boolean getEnabled() {
      return this.mEnabled;
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
