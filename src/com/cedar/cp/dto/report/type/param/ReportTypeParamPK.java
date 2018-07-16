/*     */ package com.cedar.cp.dto.report.type.param;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ReportTypeParamPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mReportTypeParamId;
/*     */   int mReportTypeId;
/*     */ 
/*     */   public ReportTypeParamPK(int newReportTypeParamId, int newReportTypeId)
/*     */   {
/*  24 */     this.mReportTypeParamId = newReportTypeParamId;
/*  25 */     this.mReportTypeId = newReportTypeId;
/*     */   }
/*     */ 
/*     */   public int getReportTypeParamId()
/*     */   {
/*  34 */     return this.mReportTypeParamId;
/*     */   }
/*     */ 
/*     */   public int getReportTypeId()
/*     */   {
/*  41 */     return this.mReportTypeId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mReportTypeParamId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mReportTypeId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     ReportTypeParamPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof ReportTypeParamCK)) {
/*  66 */       other = ((ReportTypeParamCK)obj).getReportTypeParamPK();
/*     */     }
/*  68 */     else if ((obj instanceof ReportTypeParamPK))
/*  69 */       other = (ReportTypeParamPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mReportTypeParamId == other.mReportTypeParamId);
/*  76 */     eq = (eq) && (this.mReportTypeId == other.mReportTypeId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ReportTypeParamId=");
/*  88 */     sb.append(this.mReportTypeParamId);
/*  89 */     sb.append(",ReportTypeId=");
/*  90 */     sb.append(this.mReportTypeId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mReportTypeParamId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mReportTypeId);
/* 104 */     return "ReportTypeParamPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ReportTypeParamPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("ReportTypeParamPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ReportTypeParamPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pReportTypeParamId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pReportTypeId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new ReportTypeParamPK(pReportTypeParamId, pReportTypeId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.type.param.ReportTypeParamPK
 * JD-Core Version:    0.6.0
 */