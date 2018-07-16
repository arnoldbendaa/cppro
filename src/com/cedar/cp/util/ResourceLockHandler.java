// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.ResourceLock;
import com.cedar.cp.util.SingleCharTokenizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public final class ResourceLockHandler {

   public static final char sResourceDelimeter = '\\';
   private Map mCurrentLockedResourceMap;
   private String[] mOwners;
   private String[] mLockedResources;
   private List mWorkLockedResources = new ArrayList();
   private List mWorkOwners = new ArrayList();
   private StringBuffer mSBuff1 = new StringBuffer(512);
   private StringBuffer mSBuff2 = new StringBuffer(512);
   private static final String[] sSample = new String[0];


   public synchronized void setCurrentLocks(Map currentLockMap) {
      this.mCurrentLockedResourceMap = currentLockMap;
      this.mWorkOwners.clear();
      this.mWorkLockedResources.clear();
      Set entries = currentLockMap.entrySet();
      Iterator iter = entries.iterator();

      while(iter.hasNext()) {
         Entry entry = (Entry)iter.next();
         Iterator locksIter = ((List)entry.getValue()).iterator();

         while(locksIter.hasNext()) {
            String lock = (String)locksIter.next();
            this.mWorkOwners.add(entry.getKey());
            this.mWorkLockedResources.add(lock);
         }
      }

      this.mOwners = (String[])((String[])this.mWorkOwners.toArray(sSample));
      this.mLockedResources = (String[])((String[])this.mWorkLockedResources.toArray(sSample));
   }

   public synchronized void addResourceLocks(String owner, List resources) {
      this.mCurrentLockedResourceMap.put(owner, resources);
      this.setCurrentLocks(this.mCurrentLockedResourceMap);
   }

   public synchronized void removeResourceLocks(String owner) {
      Object result = this.mCurrentLockedResourceMap.remove(owner);
      if(result == null) {
         throw new IllegalArgumentException("Attempt to remove non-existent owner from ResourceLocks");
      } else {
         this.setCurrentLocks(this.mCurrentLockedResourceMap);
      }
   }

   public synchronized boolean doesResourceConflict(String def1, String def2) {
      boolean result = true;
      SingleCharTokenizer tok1 = new SingleCharTokenizer('\\');
      tok1.setString(def1);
      SingleCharTokenizer tok2 = new SingleCharTokenizer('\\');
      tok2.setString(def2);

      while(result && tok1.hasNextToken() && tok2.hasNextToken()) {
         tok1.nextToken(this.mSBuff1);
         tok2.nextToken(this.mSBuff2);
         if(!this.sBuffEquals(this.mSBuff1, this.mSBuff2)) {
            result = false;
         }
      }

      return result;
   }

   public ResourceLock getFirstConflict(String owner, List resources) {
      if(this.mOwners != null && this.mOwners.length != 0) {
         ResourceLock result = null;
         String[] intents = (String[])((String[])resources.toArray(sSample));

         for(int i = 0; i < this.mOwners.length && result == null; ++i) {
            for(int j = 0; j < intents.length && result == null; ++j) {
               if(!this.mOwners[i].equals(owner) && this.doesResourceConflict(intents[j], this.mLockedResources[i])) {
                  result = new ResourceLock(this.mOwners[i], this.mLockedResources[i]);
               }
            }
         }

         return result;
      } else {
         return null;
      }
   }

   private boolean sBuffEquals(StringBuffer sb1, StringBuffer sb2) {
      if(sb1 == sb2) {
         return true;
      } else if(sb1.length() != sb2.length()) {
         return false;
      } else {
         for(int i = 0; i < sb2.length(); ++i) {
            if(sb1.charAt(i) != sb2.charAt(i)) {
               return false;
            }
         }

         return true;
      }
   }

}
