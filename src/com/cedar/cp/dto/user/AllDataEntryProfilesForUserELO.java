// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.api.user.DataEntryProfileRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllDataEntryProfilesForUserELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"DataEntryProfile", "User", "DataEntryProfileHistory", "XmlForm"};
   private transient DataEntryProfileRef mDataEntryProfileEntityRef;
   private transient UserRef mUserEntityRef;
   private transient String mDescription;
   private transient int mXmlformId;
   private transient int mStructureId0;
   private transient int mStructureId1;
   private transient int mStructureElementId0;
   private transient int mStructureElementId1;
   private transient String mElementLabel0;
   private transient String mElementLabel1;
   private transient String mDataType;
   private transient int mFormType;
   
   public AllDataEntryProfilesForUserELO() {
      super(new String[]{"DataEntryProfile", "User", "Description", "XmlFormId", "StructureId0", "StructureId1", "StructureElementId0", "StructureElementId1", "ElementLabel0", "ElementLabel1", "DataType", "FormType" });
   }

   public void add(DataEntryProfileRef eRefDataEntryProfile, UserRef eRefUser, String description, int xmlFormId, int structureId0, int structureId1, int structureElementId0, int structureElementId1, String elementLabel0, String elementLabel1, String dataType, Integer formType) {
      ArrayList l = new ArrayList();
      l.add(eRefDataEntryProfile);
      l.add(eRefUser);
      l.add(description);
      l.add(xmlFormId);
      l.add(structureId0);
      l.add(structureId1);
      l.add(structureElementId0);
      l.add(structureElementId1);
      l.add(elementLabel0);
      l.add(elementLabel1);
      l.add(dataType);
      l.add(formType);
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      int i = 0;
      this.mDataEntryProfileEntityRef = (DataEntryProfileRef)l.get(i++);
      this.mUserEntityRef = (UserRef)l.get(i++);
      this.mDescription = (String)l.get(i++);
      this.mXmlformId = ((Integer)l.get(i++)).intValue();
      this.mStructureId0 = ((Integer)l.get(i++)).intValue();
      this.mStructureId1 = ((Integer)l.get(i++)).intValue();
      this.mStructureElementId0 = ((Integer)l.get(i++)).intValue();
      this.mStructureElementId1 = ((Integer)l.get(i++)).intValue();
      this.mElementLabel0 = (String)l.get(i++);
      this.mElementLabel1 = (String)l.get(i++);
      this.mDataType = (String)l.get(i++);
      this.setFormType((Integer)l.get(i++));
   }

   public DataEntryProfileRef getDataEntryProfileEntityRef() {
      return this.mDataEntryProfileEntityRef;
   }

   public UserRef getUserEntityRef() {
      return this.mUserEntityRef;
   }

   public String getDescription() {
      return this.mDescription;
   }  

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

    public int getXmlformId() {
        return mXmlformId;
    }
    
    public void setXmlformId(int mXmlformId) {
        this.mXmlformId = mXmlformId;
    }
    
    public int getStructureId0() {
        return mStructureId0;
    }
    
    public void setStructureId0(int mStructureId0) {
        this.mStructureId0 = mStructureId0;
    }
    
    public int getStructureId1() {
        return mStructureId1;
    }
    
    public void setStructureId1(int mStructureId1) {
        this.mStructureId1 = mStructureId1;
    }
    
    public int getStructureElementId0() {
        return mStructureElementId0;
    }
    
    public void setStructureElementId0(int mStructureElementId0) {
        this.mStructureElementId0 = mStructureElementId0;
    }
    
    public int getStructureElementId1() {
        return mStructureElementId1;
    }
    
    public void setStructureElementId1(int mStructureElementId1) {
        this.mStructureElementId1 = mStructureElementId1;
    }
    
    public String getElementLabel0() {
        return mElementLabel0;
    }
    
    public void setElementLabel0(String mElementLabel0) {
        this.mElementLabel0 = mElementLabel0;
    }
    
    public String getElementLabel1() {
        return mElementLabel1;
    }

    public void setElementLabel1(String mElementLabel1) {
        this.mElementLabel1 = mElementLabel1;
    }
    
    public String getDataType() {
        return mDataType;
    }
    
    public void setDataType(String mDataType) {
        this.mDataType = mDataType;
    }

    public int getFormType() {
        return mFormType;
    }

    public void setFormType(int mFormType) {
        this.mFormType = mFormType;
    }

}
