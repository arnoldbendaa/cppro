// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.base;

import com.cedar.cp.ejb.impl.base.SqlExecutor;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

class SqlExecutor$BindVariable {

   private String mName;
   private Object mValue;
   private int mType;
   private int mPosition;
   // $FF: synthetic field
   final SqlExecutor this$0;


   public SqlExecutor$BindVariable(SqlExecutor var1, String name, Object value, int type) {
      this.this$0 = var1;
      this.mName = name;
      this.mValue = value;
      this.mType = type;
   }

   public SqlExecutor$BindVariable(SqlExecutor var1, String name, Object value, int type, int position) {
      this.this$0 = var1;
      this.mName = name;
      this.mValue = value;
      this.mType = type;
      this.mPosition = position;
   }

   public String getName() {
      return this.mName;
   }

   public Object getValue() {
      return this.mValue;
   }

   public int getType() {
      return this.mType;
   }

   public int getPosition() {
      return this.mPosition;
   }

   public String getDebugString() {
      StringBuilder sb = new StringBuilder();
      sb.append("/*");
      sb.append(this.mName);
      sb.append("*/ ");
      if(this.getType() == 12) {
         if(this.mValue == null) {
            sb.append("nullif(\'1\',\'1\')");
         } else {
            sb.append('\'').append(this.mValue.toString()).append('\'');
         }
      } else if(this.getType() != 4 && this.getType() != 5 && this.getType() != 3) {
         SimpleDateFormat sdf;
         if(this.getType() == 91) {
            sdf = new SimpleDateFormat("dd/MM/yyyy");
            sb.append("to_date(\'");
            sb.append(sdf.format((Date)this.mValue));
            sb.append("\',\'DD/MM/YYYY\')");
         } else if(this.getType() == 93) {
            sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
            sb.append("to_date(\'");
            sb.append(sdf.format((Timestamp)this.mValue));
            sb.append("\',\'DD/MM/YYYY HH24:MI:SS.FF3\')");
         } else if(this.getType() == 1111) {
            if(this.mValue instanceof String) {
               if(this.mValue == null) {
                  sb.append("nullif(\'1\',\'1\')");
               } else {
                  sb.append('\'').append(this.mValue.toString()).append('\'');
               }
            } else {
               sb.append(this.mValue);
            }
         } else {
            sb.append(this.mValue.toString());
         }
      } else if(this.mValue == null) {
         sb.append("nullif(1,1)");
      } else {
         sb.append(this.mValue.toString());
      }

      return sb.toString();
   }
}
