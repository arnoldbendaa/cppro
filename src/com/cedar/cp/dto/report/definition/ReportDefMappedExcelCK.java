/*     */ package com.cedar.cp.dto.report.definition;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ReportDefMappedExcelCK extends ReportDefinitionCK
/*     */   implements Serializable
/*     */ {
/*     */   protected ReportDefMappedExcelPK mReportDefMappedExcelPK;
/*     */ 
/*     */   public ReportDefMappedExcelCK(ReportDefinitionPK paramReportDefinitionPK, ReportDefMappedExcelPK paramReportDefMappedExcelPK)
/*     */   {
/*  29 */     super(paramReportDefinitionPK);
/*     */ 
/*  32 */     this.mReportDefMappedExcelPK = paramReportDefMappedExcelPK;
/*     */   }
/*     */ 
/*     */   public ReportDefMappedExcelPK getReportDefMappedExcelPK()
/*     */   {
/*  40 */     return this.mReportDefMappedExcelPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mReportDefMappedExcelPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mReportDefMappedExcelPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof ReportDefMappedExcelPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof ReportDefMappedExcelCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     ReportDefMappedExcelCK other = (ReportDefMappedExcelCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mReportDefinitionPK.equals(other.mReportDefinitionPK));
/*  75 */     eq = (eq) && (this.mReportDefMappedExcelPK.equals(other.mReportDefMappedExcelPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mReportDefMappedExcelPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("ReportDefMappedExcelCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mReportDefMappedExcelPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ReportDefinitionCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("ReportDefMappedExcelCK", token[(i++)]);
/* 111 */     checkExpected("ReportDefinitionPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("ReportDefMappedExcelPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new ReportDefMappedExcelCK(ReportDefinitionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ReportDefMappedExcelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.definition.ReportDefMappedExcelCK
 * JD-Core Version:    0.6.0
 */