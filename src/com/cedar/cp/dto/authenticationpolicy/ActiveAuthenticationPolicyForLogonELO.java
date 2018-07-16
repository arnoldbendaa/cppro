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

public class ActiveAuthenticationPolicyForLogonELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"AuthenticationPolicy"};
   private transient AuthenticationPolicyRef mAuthenticationPolicyEntityRef;
   private transient String mVisId;
   private transient String mDescription;
   private transient int mAuthenticationTechnique;
   private transient int mMinimumPasswordLength;
   private transient int mSecurityLog;
   private transient int mSecurityAdministrator;
   private transient int mPasswordExpiry;
   private transient String mJaasEntryName;


   public ActiveAuthenticationPolicyForLogonELO() {
      super(new String[]{"AuthenticationPolicy", "VisId", "Description", "AuthenticationTechnique", "MinimumPasswordLength", "SecurityLog", "SecurityAdministrator", "PasswordExpiry", "JaasEntryName"});
   }

   public void add(AuthenticationPolicyRef eRefAuthenticationPolicy, String col1, String col2, int col3, int col4, int col5, int col6, int col7, String col8) {
      ArrayList l = new ArrayList();
      l.add(eRefAuthenticationPolicy);
      l.add(col1);
      l.add(col2);
      l.add(new Integer(col3));
      l.add(new Integer(col4));
      l.add(new Integer(col5));
      l.add(new Integer(col6));
      l.add(new Integer(col7));
      l.add(col8);
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
      this.mMinimumPasswordLength = ((Integer)l.get(var4++)).intValue();
      this.mSecurityLog = ((Integer)l.get(var4++)).intValue();
      this.mSecurityAdministrator = ((Integer)l.get(var4++)).intValue();
      this.mPasswordExpiry = ((Integer)l.get(var4++)).intValue();
      this.mJaasEntryName = (String)l.get(var4++);
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

   public int getMinimumPasswordLength() {
      return this.mMinimumPasswordLength;
   }

   public int getSecurityLog() {
      return this.mSecurityLog;
   }

   public int getSecurityAdministrator() {
      return this.mSecurityAdministrator;
   }

   public int getPasswordExpiry() {
      return this.mPasswordExpiry;
   }

   public String getJaasEntryName() {
      return this.mJaasEntryName;
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
