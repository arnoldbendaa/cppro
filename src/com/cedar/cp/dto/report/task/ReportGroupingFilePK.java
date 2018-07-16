/*     */ package com.cedar.cp.dto.report.task;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ReportGroupingFilePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mReportGroupingId;
/*     */   int mReportGroupingFileId;
/*     */ 
/*     */   public ReportGroupingFilePK(int newReportGroupingId, int newReportGroupingFileId)
/*     */   {
/*  24 */     this.mReportGroupingId = newReportGroupingId;
/*  25 */     this.mReportGroupingFileId = newReportGroupingFileId;
/*     */   }
/*     */ 
/*     */   public int getReportGroupingId()
/*     */   {
/*  34 */     return this.mReportGroupingId;
/*     */   }
/*     */ 
/*     */   public int getReportGroupingFileId()
/*     */   {
/*  41 */     return this.mReportGroupingFileId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mReportGroupingId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mReportGroupingFileId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     ReportGroupingFilePK other = null;
/*     */ 
/*  65 */     if ((obj instanceof ReportGroupingFileCK)) {
/*  66 */       other = ((ReportGroupingFileCK)obj).getReportGroupingFilePK();
/*     */     }
/*  68 */     else if ((obj instanceof ReportGroupingFilePK))
/*  69 */       other = (ReportGroupingFilePK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mReportGroupingId == other.mReportGroupingId);
/*  76 */     eq = (eq) && (this.mReportGroupingFileId == other.mReportGroupingFileId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ReportGroupingId=");
/*  88 */     sb.append(this.mReportGroupingId);
/*  89 */     sb.append(",ReportGroupingFileId=");
/*  90 */     sb.append(this.mReportGroupingFileId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mReportGroupingId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mReportGroupingFileId);
/* 104 */     return "ReportGroupingFilePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ReportGroupingFilePK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("ReportGroupingFilePK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ReportGroupingFilePK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pReportGroupingId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pReportGroupingFileId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new ReportGroupingFilePK(pReportGroupingId, pReportGroupingFileId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.task.ReportGroupingFilePK
 * JD-Core Version:    0.6.0
 */