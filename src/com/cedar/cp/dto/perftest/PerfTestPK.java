/*     */ package com.cedar.cp.dto.perftest;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class PerfTestPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mPerfTestId;
/*     */ 
/*     */   public PerfTestPK(int newPerfTestId)
/*     */   {
/*  23 */     this.mPerfTestId = newPerfTestId;
/*     */   }
/*     */ 
/*     */   public int getPerfTestId()
/*     */   {
/*  32 */     return this.mPerfTestId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mPerfTestId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     PerfTestPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof PerfTestCK)) {
/*  56 */       other = ((PerfTestCK)obj).getPerfTestPK();
/*     */     }
/*  58 */     else if ((obj instanceof PerfTestPK))
/*  59 */       other = (PerfTestPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mPerfTestId == other.mPerfTestId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" PerfTestId=");
/*  77 */     sb.append(this.mPerfTestId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mPerfTestId);
/*  89 */     return "PerfTestPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static PerfTestPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("PerfTestPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'PerfTestPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pPerfTestId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new PerfTestPK(pPerfTestId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.perftest.PerfTestPK
 * JD-Core Version:    0.6.0
 */