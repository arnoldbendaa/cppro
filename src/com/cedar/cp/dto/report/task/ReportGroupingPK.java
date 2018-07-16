/*     */ package com.cedar.cp.dto.report.task;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ReportGroupingPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mReportGroupingId;
/*     */ 
/*     */   public ReportGroupingPK(int newReportGroupingId)
/*     */   {
/*  23 */     this.mReportGroupingId = newReportGroupingId;
/*     */   }
/*     */ 
/*     */   public int getReportGroupingId()
/*     */   {
/*  32 */     return this.mReportGroupingId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mReportGroupingId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     ReportGroupingPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof ReportGroupingCK)) {
/*  56 */       other = ((ReportGroupingCK)obj).getReportGroupingPK();
/*     */     }
/*  58 */     else if ((obj instanceof ReportGroupingPK))
/*  59 */       other = (ReportGroupingPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mReportGroupingId == other.mReportGroupingId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" ReportGroupingId=");
/*  77 */     sb.append(this.mReportGroupingId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mReportGroupingId);
/*  89 */     return "ReportGroupingPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ReportGroupingPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("ReportGroupingPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ReportGroupingPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pReportGroupingId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new ReportGroupingPK(pReportGroupingId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.task.ReportGroupingPK
 * JD-Core Version:    0.6.0
 */