/*     */ package com.cedar.cp.dto.model.cc;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CcMappingLinePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mCcMappingLineId;
/*     */ 
/*     */   public CcMappingLinePK(int newCcMappingLineId)
/*     */   {
/*  23 */     this.mCcMappingLineId = newCcMappingLineId;
/*     */   }
/*     */ 
/*     */   public int getCcMappingLineId()
/*     */   {
/*  32 */     return this.mCcMappingLineId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mCcMappingLineId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     CcMappingLinePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof CcMappingLineCK)) {
/*  56 */       other = ((CcMappingLineCK)obj).getCcMappingLinePK();
/*     */     }
/*  58 */     else if ((obj instanceof CcMappingLinePK))
/*  59 */       other = (CcMappingLinePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mCcMappingLineId == other.mCcMappingLineId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" CcMappingLineId=");
/*  77 */     sb.append(this.mCcMappingLineId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mCcMappingLineId);
/*  89 */     return "CcMappingLinePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static CcMappingLinePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("CcMappingLinePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'CcMappingLinePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pCcMappingLineId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new CcMappingLinePK(pCcMappingLineId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.cc.CcMappingLinePK
 * JD-Core Version:    0.6.0
 */