/*     */ package com.cedar.cp.dto.report.mappingtemplate;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ReportMappingTemplateCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected ReportMappingTemplatePK mReportMappingTemplatePK;
/*     */ 
/*     */   public ReportMappingTemplateCK(ReportMappingTemplatePK paramReportMappingTemplatePK)
/*     */   {
/*  26 */     this.mReportMappingTemplatePK = paramReportMappingTemplatePK;
/*     */   }
/*     */ 
/*     */   public ReportMappingTemplatePK getReportMappingTemplatePK()
/*     */   {
/*  34 */     return this.mReportMappingTemplatePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mReportMappingTemplatePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mReportMappingTemplatePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof ReportMappingTemplatePK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof ReportMappingTemplateCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     ReportMappingTemplateCK other = (ReportMappingTemplateCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mReportMappingTemplatePK.equals(other.mReportMappingTemplatePK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mReportMappingTemplatePK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("ReportMappingTemplateCK|");
/*  92 */     sb.append(this.mReportMappingTemplatePK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ReportMappingTemplateCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("ReportMappingTemplateCK", token[(i++)]);
/* 101 */     checkExpected("ReportMappingTemplatePK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new ReportMappingTemplateCK(ReportMappingTemplatePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplateCK
 * JD-Core Version:    0.6.0
 */