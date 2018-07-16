/*     */ package com.cedar.cp.dto.report;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ReportPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mReportId;
/*     */ 
/*     */   public ReportPK(int newReportId)
/*     */   {
/*  23 */     this.mReportId = newReportId;
/*     */   }
/*     */ 
/*     */   public int getReportId()
/*     */   {
/*  32 */     return this.mReportId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mReportId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     ReportPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof ReportCK)) {
/*  56 */       other = ((ReportCK)obj).getReportPK();
/*     */     }
/*  58 */     else if ((obj instanceof ReportPK))
/*  59 */       other = (ReportPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mReportId == other.mReportId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" ReportId=");
/*  77 */     sb.append(this.mReportId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mReportId);
/*  89 */     return "ReportPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ReportPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("ReportPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ReportPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pReportId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new ReportPK(pReportId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.ReportPK
 * JD-Core Version:    0.6.0
 */