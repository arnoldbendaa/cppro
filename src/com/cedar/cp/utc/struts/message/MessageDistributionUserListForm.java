// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.message;

import com.cedar.cp.utc.common.CPForm;
import java.util.List;

public class MessageDistributionUserListForm extends CPForm {

   private List Users;
   private int ListId;
   private boolean mInternal;


   public List getUsers() {
      return this.Users;
   }

   public void setUsers(List users) {
      this.Users = users;
   }

   public int getListId() {
      return this.ListId;
   }

   public void setListId(int listId) {
      this.ListId = listId;
   }

   public boolean getInternal() {
      return this.mInternal;
   }

   public void setInternal(boolean internal) {
      this.mInternal = internal;
   }
}
