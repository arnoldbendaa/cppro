/*     */ package com.cedar.cp.dto.udeflookup;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class UdefLookupColumnDefPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mUdefLookupId;
/*     */   int mColumnDefId;
/*     */ 
/*     */   public UdefLookupColumnDefPK(int newUdefLookupId, int newColumnDefId)
/*     */   {
/*  24 */     this.mUdefLookupId = newUdefLookupId;
/*  25 */     this.mColumnDefId = newColumnDefId;
/*     */   }
/*     */ 
/*     */   public int getUdefLookupId()
/*     */   {
/*  34 */     return this.mUdefLookupId;
/*     */   }
/*     */ 
/*     */   public int getColumnDefId()
/*     */   {
/*  41 */     return this.mColumnDefId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mUdefLookupId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mColumnDefId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     UdefLookupColumnDefPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof UdefLookupColumnDefCK)) {
/*  66 */       other = ((UdefLookupColumnDefCK)obj).getUdefLookupColumnDefPK();
/*     */     }
/*  68 */     else if ((obj instanceof UdefLookupColumnDefPK))
/*  69 */       other = (UdefLookupColumnDefPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mUdefLookupId == other.mUdefLookupId);
/*  76 */     eq = (eq) && (this.mColumnDefId == other.mColumnDefId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" UdefLookupId=");
/*  88 */     sb.append(this.mUdefLookupId);
/*  89 */     sb.append(",ColumnDefId=");
/*  90 */     sb.append(this.mColumnDefId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mUdefLookupId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mColumnDefId);
/* 104 */     return "UdefLookupColumnDefPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static UdefLookupColumnDefPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("UdefLookupColumnDefPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'UdefLookupColumnDefPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pUdefLookupId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pColumnDefId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new UdefLookupColumnDefPK(pUdefLookupId, pColumnDefId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.udeflookup.UdefLookupColumnDefPK
 * JD-Core Version:    0.6.0
 */