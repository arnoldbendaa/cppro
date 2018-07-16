// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report.pack;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class ReportPackProjectionData implements Serializable, Comparable {

   private String mPack;
   private String mBudgetLocation;
   private Set<String> mUsers;


   public ReportPackProjectionData(String pack, String budgetLocation) {
      this.mPack = pack;
      this.mBudgetLocation = budgetLocation;
      this.mUsers = new TreeSet();
   }

   public String getPack() {
      return this.mPack;
   }

   public void setPack(String pack) {
      this.mPack = pack;
   }

   public String getBudgetLocation() {
      return this.mBudgetLocation;
   }

   public void setBudgetLocation(String budgetLocation) {
      this.mBudgetLocation = budgetLocation;
   }

   public Set<String> getUsers() {
      return this.mUsers;
   }

   public void setUsers(Set<String> users) {
      this.mUsers = users;
   }

   public void addUser(String s) {
      if(this.mUsers == null) {
         this.mUsers = new TreeSet();
      }

      this.mUsers.add(s);
   }

   public String getUsersAsString() {
      StringBuilder sb = new StringBuilder();

      String s;
      for(Iterator i$ = this.mUsers.iterator(); i$.hasNext(); sb.append(s)) {
         s = (String)i$.next();
         if(sb.length() > 0) {
            sb.append(", ");
         }
      }

      return sb.toString();
   }

   public int compareTo(Object o) {
      ReportPackProjectionData comp = (ReportPackProjectionData)o;
      return !this.getPack().equals(comp.getPack())?this.getPack().compareTo(comp.getPack()):this.getBudgetLocation().compareTo(comp.getBudgetLocation());
   }
}
