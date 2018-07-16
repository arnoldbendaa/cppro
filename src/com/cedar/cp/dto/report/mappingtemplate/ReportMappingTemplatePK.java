/*     */ package com.cedar.cp.dto.report.mappingtemplate;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ReportMappingTemplatePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mReportMappingTemplateId;
/*     */ 
/*     */   public ReportMappingTemplatePK(int newReportMappingTemplateId)
/*     */   {
/*  23 */     this.mReportMappingTemplateId = newReportMappingTemplateId;
/*     */   }
/*     */ 
/*     */   public int getReportMappingTemplateId()
/*     */   {
/*  32 */     return this.mReportMappingTemplateId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mReportMappingTemplateId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     ReportMappingTemplatePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof ReportMappingTemplateCK)) {
/*  56 */       other = ((ReportMappingTemplateCK)obj).getReportMappingTemplatePK();
/*     */     }
/*  58 */     else if ((obj instanceof ReportMappingTemplatePK))
/*  59 */       other = (ReportMappingTemplatePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mReportMappingTemplateId == other.mReportMappingTemplateId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" ReportMappingTemplateId=");
/*  77 */     sb.append(this.mReportMappingTemplateId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mReportMappingTemplateId);
/*  89 */     return "ReportMappingTemplatePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ReportMappingTemplatePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("ReportMappingTemplatePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ReportMappingTemplatePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pReportMappingTemplateId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new ReportMappingTemplatePK(pReportMappingTemplateId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplatePK
 * JD-Core Version:    0.6.0
 */