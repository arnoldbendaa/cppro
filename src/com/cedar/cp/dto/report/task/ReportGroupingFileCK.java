/*     */ package com.cedar.cp.dto.report.task;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ReportGroupingFileCK extends ReportGroupingCK
/*     */   implements Serializable
/*     */ {
/*     */   protected ReportGroupingFilePK mReportGroupingFilePK;
/*     */ 
/*     */   public ReportGroupingFileCK(ReportGroupingPK paramReportGroupingPK, ReportGroupingFilePK paramReportGroupingFilePK)
/*     */   {
/*  29 */     super(paramReportGroupingPK);
/*     */ 
/*  32 */     this.mReportGroupingFilePK = paramReportGroupingFilePK;
/*     */   }
/*     */ 
/*     */   public ReportGroupingFilePK getReportGroupingFilePK()
/*     */   {
/*  40 */     return this.mReportGroupingFilePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mReportGroupingFilePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mReportGroupingFilePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof ReportGroupingFilePK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof ReportGroupingFileCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     ReportGroupingFileCK other = (ReportGroupingFileCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mReportGroupingPK.equals(other.mReportGroupingPK));
/*  75 */     eq = (eq) && (this.mReportGroupingFilePK.equals(other.mReportGroupingFilePK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mReportGroupingFilePK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("ReportGroupingFileCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mReportGroupingFilePK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ReportGroupingCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("ReportGroupingFileCK", token[(i++)]);
/* 111 */     checkExpected("ReportGroupingPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("ReportGroupingFilePK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new ReportGroupingFileCK(ReportGroupingPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ReportGroupingFilePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.task.ReportGroupingFileCK
 * JD-Core Version:    0.6.0
 */