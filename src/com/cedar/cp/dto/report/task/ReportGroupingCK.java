/*     */ package com.cedar.cp.dto.report.task;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ReportGroupingCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected ReportGroupingPK mReportGroupingPK;
/*     */ 
/*     */   public ReportGroupingCK(ReportGroupingPK paramReportGroupingPK)
/*     */   {
/*  26 */     this.mReportGroupingPK = paramReportGroupingPK;
/*     */   }
/*     */ 
/*     */   public ReportGroupingPK getReportGroupingPK()
/*     */   {
/*  34 */     return this.mReportGroupingPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mReportGroupingPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mReportGroupingPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof ReportGroupingPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof ReportGroupingCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     ReportGroupingCK other = (ReportGroupingCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mReportGroupingPK.equals(other.mReportGroupingPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mReportGroupingPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("ReportGroupingCK|");
/*  92 */     sb.append(this.mReportGroupingPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ReportGroupingCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("ReportGroupingCK", token[(i++)]);
/* 101 */     checkExpected("ReportGroupingPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new ReportGroupingCK(ReportGroupingPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.task.ReportGroupingCK
 * JD-Core Version:    0.6.0
 */