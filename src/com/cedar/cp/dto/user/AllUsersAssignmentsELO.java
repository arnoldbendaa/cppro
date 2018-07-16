// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllUsersAssignmentsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"UserId", "FullName", "Model_VisId", "Element_VisId", "Element_Description", "Read_Write"};
   private transient String mUserName;
   private transient String mFullName;
   private transient String mModelId;
   private transient String mElementId;
   private transient String mElementDesc;
   private transient String mReadWrite;


   public AllUsersAssignmentsELO() {
      super(new String[]{"UserId", "FullName", "Model_VisId", "Element_VisId", "Element_Description", "Read_Write"});
   }

   public void add(String name, String fullname, String modelVisId, String element_id, String element_desc, String read) {
      ArrayList l = new ArrayList();
      l.add(name);
      l.add(fullname);
      l.add(modelVisId);
      l.add(element_id);
      l.add(element_desc);
      l.add(read);
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
      this.mUserName = (String)l.get(index);
      this.mFullName = (String)l.get(var4++);
      this.mModelId = (String)l.get(var4++);
      this.mElementId = (String)l.get(var4++);
      this.mElementDesc = (String)l.get(var4++);
      this.mReadWrite = (String)l.get(var4);
   }

   public String getUserName() {
      return this.mUserName;
   }

   public String getFullName() {
      return this.mFullName;
   }

   public String getModelId() {
      return this.mModelId;
   }

   public String getElementId() {
      return this.mElementId;
   }

   public String getElementDesc() {
      return this.mElementDesc;
   }

   public String getReadWrite() {
      return this.mReadWrite;
   }

}
