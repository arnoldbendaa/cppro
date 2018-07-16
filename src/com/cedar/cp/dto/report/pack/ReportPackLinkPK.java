/*     */ package com.cedar.cp.dto.report.pack;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ReportPackLinkPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mReportPackId;
/*     */   int mReportPackLinkId;
/*     */ 
/*     */   public ReportPackLinkPK(int newReportPackId, int newReportPackLinkId)
/*     */   {
/*  24 */     this.mReportPackId = newReportPackId;
/*  25 */     this.mReportPackLinkId = newReportPackLinkId;
/*     */   }
/*     */ 
/*     */   public int getReportPackId()
/*     */   {
/*  34 */     return this.mReportPackId;
/*     */   }
/*     */ 
/*     */   public int getReportPackLinkId()
/*     */   {
/*  41 */     return this.mReportPackLinkId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mReportPackId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mReportPackLinkId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     ReportPackLinkPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof ReportPackLinkCK)) {
/*  66 */       other = ((ReportPackLinkCK)obj).getReportPackLinkPK();
/*     */     }
/*  68 */     else if ((obj instanceof ReportPackLinkPK))
/*  69 */       other = (ReportPackLinkPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mReportPackId == other.mReportPackId);
/*  76 */     eq = (eq) && (this.mReportPackLinkId == other.mReportPackLinkId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ReportPackId=");
/*  88 */     sb.append(this.mReportPackId);
/*  89 */     sb.append(",ReportPackLinkId=");
/*  90 */     sb.append(this.mReportPackLinkId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mReportPackId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mReportPackLinkId);
/* 104 */     return "ReportPackLinkPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ReportPackLinkPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("ReportPackLinkPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ReportPackLinkPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pReportPackId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pReportPackLinkId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new ReportPackLinkPK(pReportPackId, pReportPackLinkId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.pack.ReportPackLinkPK
 * JD-Core Version:    0.6.0
 */