/*     */ package com.cedar.cp.dto.report.template;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ReportTemplatePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mReportTemplateId;
/*     */ 
/*     */   public ReportTemplatePK(int newReportTemplateId)
/*     */   {
/*  23 */     this.mReportTemplateId = newReportTemplateId;
/*     */   }
/*     */ 
/*     */   public int getReportTemplateId()
/*     */   {
/*  32 */     return this.mReportTemplateId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mReportTemplateId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     ReportTemplatePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof ReportTemplateCK)) {
/*  56 */       other = ((ReportTemplateCK)obj).getReportTemplatePK();
/*     */     }
/*  58 */     else if ((obj instanceof ReportTemplatePK))
/*  59 */       other = (ReportTemplatePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mReportTemplateId == other.mReportTemplateId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" ReportTemplateId=");
/*  77 */     sb.append(this.mReportTemplateId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mReportTemplateId);
/*  89 */     return "ReportTemplatePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ReportTemplatePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("ReportTemplatePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ReportTemplatePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pReportTemplateId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new ReportTemplatePK(pReportTemplateId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.template.ReportTemplatePK
 * JD-Core Version:    0.6.0
 */