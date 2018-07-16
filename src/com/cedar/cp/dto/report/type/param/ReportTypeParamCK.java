/*     */ package com.cedar.cp.dto.report.type.param;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.report.type.ReportTypeCK;
/*     */ import com.cedar.cp.dto.report.type.ReportTypePK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ReportTypeParamCK extends ReportTypeCK
/*     */   implements Serializable
/*     */ {
/*     */   protected ReportTypeParamPK mReportTypeParamPK;
/*     */ 
/*     */   public ReportTypeParamCK(ReportTypePK paramReportTypePK, ReportTypeParamPK paramReportTypeParamPK)
/*     */   {
/*  30 */     super(paramReportTypePK);
/*     */ 
/*  33 */     this.mReportTypeParamPK = paramReportTypeParamPK;
/*     */   }
/*     */ 
/*     */   public ReportTypeParamPK getReportTypeParamPK()
/*     */   {
/*  41 */     return this.mReportTypeParamPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  49 */     return this.mReportTypeParamPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  57 */     return this.mReportTypeParamPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  66 */     if ((obj instanceof ReportTypeParamPK)) {
/*  67 */       return obj.equals(this);
/*     */     }
/*  69 */     if (!(obj instanceof ReportTypeParamCK)) {
/*  70 */       return false;
/*     */     }
/*  72 */     ReportTypeParamCK other = (ReportTypeParamCK)obj;
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mReportTypePK.equals(other.mReportTypePK));
/*  76 */     eq = (eq) && (this.mReportTypeParamPK.equals(other.mReportTypeParamPK));
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(super.toString());
/*  88 */     sb.append("[");
/*  89 */     sb.append(this.mReportTypeParamPK);
/*  90 */     sb.append("]");
/*  91 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append("ReportTypeParamCK|");
/* 101 */     sb.append(super.getPK().toTokens());
/* 102 */     sb.append('|');
/* 103 */     sb.append(this.mReportTypeParamPK.toTokens());
/* 104 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ReportTypeCK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] token = extKey.split("[|]");
/* 110 */     int i = 0;
/* 111 */     checkExpected("ReportTypeParamCK", token[(i++)]);
/* 112 */     checkExpected("ReportTypePK", token[(i++)]);
/* 113 */     i++;
/* 114 */     checkExpected("ReportTypeParamPK", token[(i++)]);
/* 115 */     i = 1;
/* 116 */     return new ReportTypeParamCK(ReportTypePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ReportTypeParamPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 124 */     if (!expected.equals(found))
/* 125 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.type.param.ReportTypeParamCK
 * JD-Core Version:    0.6.0
 */