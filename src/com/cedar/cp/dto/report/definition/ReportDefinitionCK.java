/*     */ package com.cedar.cp.dto.report.definition;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ReportDefinitionCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected ReportDefinitionPK mReportDefinitionPK;
/*     */ 
/*     */   public ReportDefinitionCK(ReportDefinitionPK paramReportDefinitionPK)
/*     */   {
/*  26 */     this.mReportDefinitionPK = paramReportDefinitionPK;
/*     */   }
/*     */ 
/*     */   public ReportDefinitionPK getReportDefinitionPK()
/*     */   {
/*  34 */     return this.mReportDefinitionPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mReportDefinitionPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mReportDefinitionPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof ReportDefinitionPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof ReportDefinitionCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     ReportDefinitionCK other = (ReportDefinitionCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mReportDefinitionPK.equals(other.mReportDefinitionPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mReportDefinitionPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("ReportDefinitionCK|");
/*  92 */     sb.append(this.mReportDefinitionPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ReportDefinitionCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("ReportDefinitionCK", token[(i++)]);
/* 101 */     checkExpected("ReportDefinitionPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new ReportDefinitionCK(ReportDefinitionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.definition.ReportDefinitionCK
 * JD-Core Version:    0.6.0
 */