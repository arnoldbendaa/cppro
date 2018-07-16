/*    */ package com.cedar.cp.dto.sqlmon;
/*    */ 
/*    */ import com.cedar.cp.dto.base.PrimaryKey;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class SqlPK extends PrimaryKey
/*    */   implements Serializable
/*    */ {
/* 79 */   private int mHashCode = -2147483648;
/*    */   private int mSid;
/*    */   private int mSerialNumber;
/*    */ 
/*    */   public SqlPK(int sid, int serialNumber)
/*    */   {
/* 10 */     this.mSid = sid;
/* 11 */     this.mSerialNumber = serialNumber;
/*    */   }
/*    */ 
/*    */   public int getSid()
/*    */   {
/* 17 */     return this.mSid;
/*    */   }
/*    */ 
/*    */   public int getSerialNumber()
/*    */   {
/* 22 */     return this.mSerialNumber;
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 30 */     if (this.mHashCode == -2147483648)
/*    */     {
/* 32 */       this.mHashCode += String.valueOf(this.mSid).hashCode();
/* 33 */       this.mHashCode += String.valueOf(this.mSerialNumber).hashCode();
/*    */     }
/*    */ 
/* 36 */     return this.mHashCode;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object obj)
/*    */   {
/* 44 */     SqlPK other = null;
/*    */ 
/* 46 */     if ((obj instanceof SqlPK))
/* 47 */       other = (SqlPK)obj;
/*    */     else {
/* 49 */       return false;
/*    */     }
/* 51 */     boolean eq = true;
/*    */ 
/* 53 */     eq = (eq) && (this.mSid == other.mSid);
/* 54 */     eq = (eq) && (this.mSerialNumber == other.mSerialNumber);
/*    */ 
/* 57 */     return eq;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 65 */     StringBuffer sb = new StringBuffer();
/* 66 */     sb.append(" Sid=");
/* 67 */     sb.append(String.valueOf(this.mSid));
/* 68 */     sb.append(",Serial#=");
/* 69 */     sb.append(String.valueOf(this.mSerialNumber));
/* 70 */     return sb.toString().substring(1);
/*    */   }
/*    */ 
/*    */   public String toTokens()
/*    */   {
/* 75 */     return null;
/*    */   }
/*    */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.sqlmon.SqlPK
 * JD-Core Version:    0.6.0
 */