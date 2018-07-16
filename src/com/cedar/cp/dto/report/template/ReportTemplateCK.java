/*     */ package com.cedar.cp.dto.report.template;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ReportTemplateCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected ReportTemplatePK mReportTemplatePK;
/*     */ 
/*     */   public ReportTemplateCK(ReportTemplatePK paramReportTemplatePK)
/*     */   {
/*  26 */     this.mReportTemplatePK = paramReportTemplatePK;
/*     */   }
/*     */ 
/*     */   public ReportTemplatePK getReportTemplatePK()
/*     */   {
/*  34 */     return this.mReportTemplatePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mReportTemplatePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mReportTemplatePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof ReportTemplatePK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof ReportTemplateCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     ReportTemplateCK other = (ReportTemplateCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mReportTemplatePK.equals(other.mReportTemplatePK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mReportTemplatePK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("ReportTemplateCK|");
/*  92 */     sb.append(this.mReportTemplatePK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ReportTemplateCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("ReportTemplateCK", token[(i++)]);
/* 101 */     checkExpected("ReportTemplatePK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new ReportTemplateCK(ReportTemplatePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.template.ReportTemplateCK
 * JD-Core Version:    0.6.0
 */