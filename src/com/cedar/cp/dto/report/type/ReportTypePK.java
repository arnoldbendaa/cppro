/*     */ package com.cedar.cp.dto.report.type;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ReportTypePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mReportTypeId;
/*     */ 
/*     */   public ReportTypePK(int newReportTypeId)
/*     */   {
/*  23 */     this.mReportTypeId = newReportTypeId;
/*     */   }
/*     */ 
/*     */   public int getReportTypeId()
/*     */   {
/*  32 */     return this.mReportTypeId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mReportTypeId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     ReportTypePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof ReportTypeCK)) {
/*  56 */       other = ((ReportTypeCK)obj).getReportTypePK();
/*     */     }
/*  58 */     else if ((obj instanceof ReportTypePK))
/*  59 */       other = (ReportTypePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mReportTypeId == other.mReportTypeId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" ReportTypeId=");
/*  77 */     sb.append(this.mReportTypeId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mReportTypeId);
/*  89 */     return "ReportTypePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ReportTypePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("ReportTypePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ReportTypePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pReportTypeId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new ReportTypePK(pReportTypeId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.type.ReportTypePK
 * JD-Core Version:    0.6.0
 */