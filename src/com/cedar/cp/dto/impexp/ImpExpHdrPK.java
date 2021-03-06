/*     */ package com.cedar.cp.dto.impexp;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ImpExpHdrPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mBatchId;
/*     */ 
/*     */   public ImpExpHdrPK(int newBatchId)
/*     */   {
/*  23 */     this.mBatchId = newBatchId;
/*     */   }
/*     */ 
/*     */   public int getBatchId()
/*     */   {
/*  32 */     return this.mBatchId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mBatchId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     ImpExpHdrPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof ImpExpHdrCK)) {
/*  56 */       other = ((ImpExpHdrCK)obj).getImpExpHdrPK();
/*     */     }
/*  58 */     else if ((obj instanceof ImpExpHdrPK))
/*  59 */       other = (ImpExpHdrPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mBatchId == other.mBatchId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" BatchId=");
/*  77 */     sb.append(this.mBatchId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mBatchId);
/*  89 */     return "ImpExpHdrPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ImpExpHdrPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("ImpExpHdrPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ImpExpHdrPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pBatchId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new ImpExpHdrPK(pBatchId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.impexp.ImpExpHdrPK
 * JD-Core Version:    0.6.0
 */