/*     */ package com.cedar.cp.dto.report.definition;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ReportDefSummaryCalcPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mReportDefinitionId;
/*     */ 
/*     */   public ReportDefSummaryCalcPK(int newReportDefinitionId)
/*     */   {
/*  23 */     this.mReportDefinitionId = newReportDefinitionId;
/*     */   }
/*     */ 
/*     */   public int getReportDefinitionId()
/*     */   {
/*  32 */     return this.mReportDefinitionId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mReportDefinitionId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     ReportDefSummaryCalcPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof ReportDefSummaryCalcCK)) {
/*  56 */       other = ((ReportDefSummaryCalcCK)obj).getReportDefSummaryCalcPK();
/*     */     }
/*  58 */     else if ((obj instanceof ReportDefSummaryCalcPK))
/*  59 */       other = (ReportDefSummaryCalcPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mReportDefinitionId == other.mReportDefinitionId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" ReportDefinitionId=");
/*  77 */     sb.append(this.mReportDefinitionId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mReportDefinitionId);
/*  89 */     return "ReportDefSummaryCalcPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ReportDefSummaryCalcPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("ReportDefSummaryCalcPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ReportDefSummaryCalcPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pReportDefinitionId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new ReportDefSummaryCalcPK(pReportDefinitionId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.definition.ReportDefSummaryCalcPK
 * JD-Core Version:    0.6.0
 */