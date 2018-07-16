/*     */ package com.cedar.cp.dto.user;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class DataEntryProfilePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mDataEntryProfileId;
/*     */ 
/*     */   public DataEntryProfilePK(int newDataEntryProfileId)
/*     */   {
/*  23 */     this.mDataEntryProfileId = newDataEntryProfileId;
/*     */   }
/*     */ 
/*     */   public int getDataEntryProfileId()
/*     */   {
/*  32 */     return this.mDataEntryProfileId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mDataEntryProfileId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     DataEntryProfilePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof DataEntryProfileCK)) {
/*  56 */       other = ((DataEntryProfileCK)obj).getDataEntryProfilePK();
/*     */     }
/*  58 */     else if ((obj instanceof DataEntryProfilePK))
/*  59 */       other = (DataEntryProfilePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mDataEntryProfileId == other.mDataEntryProfileId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" DataEntryProfileId=");
/*  77 */     sb.append(this.mDataEntryProfileId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mDataEntryProfileId);
/*  89 */     return "DataEntryProfilePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static DataEntryProfilePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("DataEntryProfilePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'DataEntryProfilePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pDataEntryProfileId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new DataEntryProfilePK(pDataEntryProfileId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.user.DataEntryProfilePK
 * JD-Core Version:    0.6.0
 */