/*     */ package com.cedar.cp.dto.report.type;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ReportTypeCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected ReportTypePK mReportTypePK;
/*     */ 
/*     */   public ReportTypeCK(ReportTypePK paramReportTypePK)
/*     */   {
/*  26 */     this.mReportTypePK = paramReportTypePK;
/*     */   }
/*     */ 
/*     */   public ReportTypePK getReportTypePK()
/*     */   {
/*  34 */     return this.mReportTypePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mReportTypePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mReportTypePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof ReportTypePK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof ReportTypeCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     ReportTypeCK other = (ReportTypeCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mReportTypePK.equals(other.mReportTypePK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mReportTypePK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("ReportTypeCK|");
/*  92 */     sb.append(this.mReportTypePK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ReportTypeCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("ReportTypeCK", token[(i++)]);
/* 101 */     checkExpected("ReportTypePK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new ReportTypeCK(ReportTypePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.type.ReportTypeCK
 * JD-Core Version:    0.6.0
 */