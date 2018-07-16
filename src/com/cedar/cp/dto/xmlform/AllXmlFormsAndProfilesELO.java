// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform;

import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllXmlFormsAndProfilesELO extends AbstractELO implements Serializable {

   private transient String mName;
   private transient String mFullName;
   private transient String mForm;
   private transient String mFormDescription;
   private transient String mProfile;
   private transient String mProfileDescription;


   public AllXmlFormsAndProfilesELO() {
      super(new String[]{"Name", "FullName", "Form", "FormDescription", "Profile", "ProfileDescription"});
   }

   public void add(String col1, String col2, String col3, String col4, String col5, String col6) {
      ArrayList l = new ArrayList();
      l.add(col1);
      l.add(col2);
      l.add(col3);
      l.add(col4);
      l.add(col5);
      l.add(col6);
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
      this.mName = (String)l.get(index);
      this.mFullName = (String)l.get(var4++);
      this.mForm = (String)l.get(var4++);
      this.mFormDescription = (String)l.get(var4++);
      this.mProfile = (String)l.get(var4++);
      this.mProfileDescription = (String)l.get(var4++);
   }

   public String getName() {
      return this.mName;
   }

   public String getFullName() {
      return this.mFullName;
   }

   public String getFrom() {
      return this.mForm;
   }

   public String getFormDescription() {
      return this.mFormDescription;
   }

   public String getProfile() {
      return this.mProfile;
   }

   public String getProfileDescription() {
      return this.mProfileDescription;
   }
}
