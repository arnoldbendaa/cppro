/*     */ package com.cedar.cp.dto.perftestrun;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class PerfTestRunPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mPerfTestRunId;
/*     */ 
/*     */   public PerfTestRunPK(int newPerfTestRunId)
/*     */   {
/*  23 */     this.mPerfTestRunId = newPerfTestRunId;
/*     */   }
/*     */ 
/*     */   public int getPerfTestRunId()
/*     */   {
/*  32 */     return this.mPerfTestRunId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mPerfTestRunId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     PerfTestRunPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof PerfTestRunCK)) {
/*  56 */       other = ((PerfTestRunCK)obj).getPerfTestRunPK();
/*     */     }
/*  58 */     else if ((obj instanceof PerfTestRunPK))
/*  59 */       other = (PerfTestRunPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mPerfTestRunId == other.mPerfTestRunId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" PerfTestRunId=");
/*  77 */     sb.append(this.mPerfTestRunId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mPerfTestRunId);
/*  89 */     return "PerfTestRunPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static PerfTestRunPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("PerfTestRunPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'PerfTestRunPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pPerfTestRunId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new PerfTestRunPK(pPerfTestRunId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.perftestrun.PerfTestRunPK
 * JD-Core Version:    0.6.0
 */