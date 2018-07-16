// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.message;

import com.cedar.cp.utc.common.CPForm;
import java.util.ArrayList;
import java.util.List;

public class MessageUserListForm extends CPForm {

   private List users = new ArrayList();
   private int size = 0;
   private String searchUserName = "";
   private String searchUserId = "";


   public List getUsers() {
      return this.users;
   }

   public void setUsers(List users) {
      this.users = users;
   }

   public int getSize() {
      return this.size;
   }

   public void setSize(int size) {
      this.size = size;
   }

   public String getSearchUserName() {
      return this.searchUserName;
   }

   public void setSearchUserName(String searchUserName) {
      this.searchUserName = searchUserName;
   }

   public String getSearchUserId() {
      return this.searchUserId;
   }

   public void setSearchUserId(String searchUserId) {
      this.searchUserId = searchUserId;
   }
}
