// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.destination.internal;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.report.destination.internal.InternalDestination;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationImpl;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationPK;
import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.impl.report.destination.internal.InternalDestinationEditorSessionImpl;
import java.util.List;

public class InternalDestinationAdapter implements InternalDestination {

   private InternalDestinationImpl mEditorData;
   private InternalDestinationEditorSessionImpl mEditorSessionImpl;


   public InternalDestinationAdapter(InternalDestinationEditorSessionImpl e, InternalDestinationImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected InternalDestinationEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected InternalDestinationImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(InternalDestinationPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public int getMessageType() {
      return this.mEditorData.getMessageType();
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setMessageType(int p) {
      this.mEditorData.setMessageType(p);
   }

   public List getUserList() {
      return this.mEditorData.getUserList();
   }

   public EntityList getAvailableUsers() {
      EntityList list = this.mEditorSessionImpl.getConnection().getListHelper().getAllUsers();
      int size = list.getNumRows();
      AllUsersELO avail = new AllUsersELO();
      List selected = this.getUserList();

      for(int i = 0; i < size; ++i) {
         UserRef ref = (UserRef)list.getValueAt(i, "User");
         if(!selected.contains(ref)) {
            Object o = list.getValueAt(i, "FullName");
            String name = "";
            if(o instanceof String) {
               name = o.toString();
            }

            avail.add(ref, name, false);
         }
      }

      return avail;
   }
}
