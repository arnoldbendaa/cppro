/*     */ package com.cedar.cp.dto.report.pack;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ReportPackCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected ReportPackPK mReportPackPK;
/*     */ 
/*     */   public ReportPackCK(ReportPackPK paramReportPackPK)
/*     */   {
/*  26 */     this.mReportPackPK = paramReportPackPK;
/*     */   }
/*     */ 
/*     */   public ReportPackPK getReportPackPK()
/*     */   {
/*  34 */     return this.mReportPackPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mReportPackPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mReportPackPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof ReportPackPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof ReportPackCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     ReportPackCK other = (ReportPackCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mReportPackPK.equals(other.mReportPackPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mReportPackPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("ReportPackCK|");
/*  92 */     sb.append(this.mReportPackPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ReportPackCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("ReportPackCK", token[(i++)]);
/* 101 */     checkExpected("ReportPackPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new ReportPackCK(ReportPackPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.pack.ReportPackCK
 * JD-Core Version:    0.6.0
 */