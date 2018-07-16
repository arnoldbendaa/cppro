// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.authenticationpolicy;

import com.cedar.cp.api.authenticationpolicy.AuthenticationPolicyRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllAuthenticationPolicysELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"AuthenticationPolicy"};
   private transient AuthenticationPolicyRef mAuthenticationPolicyEntityRef;
   private transient String mVisId;
   private transient String mDescription;
   private transient int mAuthenticationTechnique;
   private transient boolean mActive;


   public AllAuthenticationPolicysELO() {
      super(new String[]{"AuthenticationPolicy", "VisId", "Description", "AuthenticationTechnique", "Active"});
   }

   public void add(AuthenticationPolicyRef eRefAuthenticationPolicy, String col1, String col2, int col3, boolean col4) {
      ArrayList l = new ArrayList();
      l.add(eRefAuthenticationPolicy);
      l.add(col1);
      l.add(col2);
      l.add(new Integer(col3));
      l.add(new Boolean(col4));
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
      this.mAuthenticationPolicyEntityRef = (AuthenticationPolicyRef)l.get(index);
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mAuthenticationTechnique = ((Integer)l.get(var4++)).intValue();
      this.mActive = ((Boolean)l.get(var4++)).booleanValue();
   }

   public AuthenticationPolicyRef getAuthenticationPolicyEntityRef() {
      return this.mAuthenticationPolicyEntityRef;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getAuthenticationTechnique() {
      return this.mAuthenticationTechnique;
   }

   public boolean getActive() {
      return this.mActive;
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
