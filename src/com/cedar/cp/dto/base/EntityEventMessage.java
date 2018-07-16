// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.base;

import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class EntityEventMessage implements Serializable {

   public static final int CHANGE_TYPE_INSERT = 1;
   public static final int CHANGE_TYPE_DELETE = 2;
   public static final int CHANGE_TYPE_UPDATE = 3;
   private String mEntityName;
   private PrimaryKey mKey;
   private int mChangeType;
   private String mSource;


   public EntityEventMessage(String entityName, PrimaryKey key, int changeType, String source) {
      this.mEntityName = entityName;
      this.mKey = key;
      this.mChangeType = changeType;
      this.mSource = source;
   }

   public String getEntityName() {
      return this.mEntityName;
   }

   public PrimaryKey getPrimaryKey() {
      return this.mKey;
   }

   public int getChangeType() {
      return this.mChangeType;
   }

   public String getChangeTypeAsString() {
      switch(this.mChangeType) {
      case 1:
         return "insert";
      case 2:
         return "delete";
      case 3:
         return "update";
      default:
         return "unknown";
      }
   }

   public String getSource() {
      return this.mSource;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("entity=");
      sb.append(this.mEntityName);
      sb.append(" key=");
      sb.append(this.mKey);
      sb.append(" change=");
      sb.append(this.getChangeTypeAsString());
      sb.append(" source=");
      sb.append(this.mSource);
      return sb.toString();
   }
}
