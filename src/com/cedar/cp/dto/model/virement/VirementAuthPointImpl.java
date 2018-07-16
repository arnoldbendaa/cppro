// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.virement.VirementAuthPoint;
import com.cedar.cp.api.model.virement.VirementLine;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.virement.VirementAuthPointPK;
import com.cedar.cp.dto.model.virement.VirementLineImpl;
import com.cedar.cp.dto.user.UserRefImpl;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class VirementAuthPointImpl implements VirementAuthPoint {

   private VirementAuthPointPK mPK;
   private StructureElementRefImpl mRAElement;
   private Set mLines = new HashSet();
   private Set mAvailableAuthorisers = new HashSet();
   private String mNotes;
   private UserRefImpl mAuthUser;
   private boolean mCanUserAuth;
   private int mStatus;


   public VirementAuthPointImpl(VirementAuthPointPK PK, StructureElementRefImpl rAElement, String notes, Set lines, Set availableAuthorisers, UserRefImpl authUser, int status, boolean canUserAuth) {
      this.mPK = PK;
      this.mRAElement = rAElement;
      this.mNotes = notes;
      this.mLines = lines;
      this.mAvailableAuthorisers = availableAuthorisers;
      this.mAuthUser = authUser;
      this.mStatus = status;
      this.mCanUserAuth = canUserAuth;
   }

   public Set getLines() {
      return this.mLines;
   }

   public void setLines(Set lines) {
      this.mLines = lines;
   }

   public Object getKey() {
      return this.getPK();
   }

   public VirementAuthPointPK getPK() {
      return this.mPK;
   }

   public void setPK(VirementAuthPointPK PK) {
      this.mPK = PK;
   }

   public Set getAvailableAuthorisers() {
      return this.mAvailableAuthorisers;
   }

   public void setAvailableAuthorisers(Set availableAuthorisers) {
      this.mAvailableAuthorisers = availableAuthorisers;
   }

   public UserRef getAuthUser() {
      return this.mAuthUser;
   }

   public void setAuthUser(UserRefImpl authUser) {
      this.mAuthUser = authUser;
   }

   public String getNotes() {
      return this.mNotes;
   }

   public void setNotes(String notes) {
      this.mNotes = notes;
   }

   public int getStatus() {
      return this.mStatus;
   }

   public void setStatus(int status) {
      this.mStatus = status;
   }

   public StructureElementRef getStructureElementRef() {
      return this.mRAElement;
   }

   public StructureElementRefImpl getRAElement() {
      return this.mRAElement;
   }

   public void setRAElement(StructureElementRefImpl RAElement) {
      this.mRAElement = RAElement;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("\nVirementAuthPoint");
      sb.append(" pk:" + this.mPK != null?this.mPK.toString():"null");
      sb.append(" raElement: ");
      sb.append(this.mRAElement.toString());
      sb.append(" status:");
      sb.append(String.valueOf(this.mStatus));
      sb.append("\nnotes:");
      sb.append(this.mNotes);
      sb.append("\nlines:");
      sb.append(this.mLines);
      sb.append("\navailableAuthorisers:");
      sb.append(this.mAvailableAuthorisers);
      return sb.toString();
   }

   public VirementLineImpl getLine(int lineId) {
      Iterator lIter = this.mLines.iterator();

      VirementLineImpl virementLine;
      do {
         if(!lIter.hasNext()) {
            return null;
         }

         virementLine = (VirementLineImpl)lIter.next();
      } while(virementLine.getPK().getRequestLineId() != lineId);

      return virementLine;
   }

   public VirementLine getLine(StructureElementRef seRef) {
      Iterator lIter = this.mLines.iterator();

      VirementLineImpl virementLine;
      do {
         if(!lIter.hasNext()) {
            return null;
         }

         virementLine = (VirementLineImpl)lIter.next();
      } while(!virementLine.getAddress().get(0).equals(seRef));

      return virementLine;
   }

   public void removeLine(int lineId) {
      VirementLineImpl line = this.getLine(lineId);
      if(line != null) {
         this.mLines.remove(line);
      }

   }

   public String getKeyAsText() {
      return this.mPK != null?this.mPK.toTokens():null;
   }

   public boolean isCanUserAuth() {
      return this.mCanUserAuth;
   }

   public void setCanUserAuth(boolean canUserAuth) {
      this.mCanUserAuth = canUserAuth;
   }
}
