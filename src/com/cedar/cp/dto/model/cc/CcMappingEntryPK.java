/*     */ package com.cedar.cp.dto.model.cc;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CcMappingEntryPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mCcMappingEntryId;
/*     */ 
/*     */   public CcMappingEntryPK(int newCcMappingEntryId)
/*     */   {
/*  23 */     this.mCcMappingEntryId = newCcMappingEntryId;
/*     */   }
/*     */ 
/*     */   public int getCcMappingEntryId()
/*     */   {
/*  32 */     return this.mCcMappingEntryId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mCcMappingEntryId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     CcMappingEntryPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof CcMappingEntryCK)) {
/*  56 */       other = ((CcMappingEntryCK)obj).getCcMappingEntryPK();
/*     */     }
/*  58 */     else if ((obj instanceof CcMappingEntryPK))
/*  59 */       other = (CcMappingEntryPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mCcMappingEntryId == other.mCcMappingEntryId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" CcMappingEntryId=");
/*  77 */     sb.append(this.mCcMappingEntryId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mCcMappingEntryId);
/*  89 */     return "CcMappingEntryPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static CcMappingEntryPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("CcMappingEntryPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'CcMappingEntryPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pCcMappingEntryId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new CcMappingEntryPK(pCcMappingEntryId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.cc.CcMappingEntryPK
 * JD-Core Version:    0.6.0
 */