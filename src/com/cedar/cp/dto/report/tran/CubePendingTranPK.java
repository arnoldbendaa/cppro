/*     */ package com.cedar.cp.dto.report.tran;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CubePendingTranPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 148 */   private int mHashCode = -2147483648;
/*     */   int mReportId;
/*     */   int mFinanceCubeId;
/*     */   int mRowNo;
/*     */ 
/*     */   public CubePendingTranPK(int newReportId, int newFinanceCubeId, int newRowNo)
/*     */   {
/*  25 */     this.mReportId = newReportId;
/*  26 */     this.mFinanceCubeId = newFinanceCubeId;
/*  27 */     this.mRowNo = newRowNo;
/*     */   }
/*     */ 
/*     */   public int getReportId()
/*     */   {
/*  36 */     return this.mReportId;
/*     */   }
/*     */ 
/*     */   public int getFinanceCubeId()
/*     */   {
/*  43 */     return this.mFinanceCubeId;
/*     */   }
/*     */ 
/*     */   public int getRowNo()
/*     */   {
/*  50 */     return this.mRowNo;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  58 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  60 */       this.mHashCode += String.valueOf(this.mReportId).hashCode();
/*  61 */       this.mHashCode += String.valueOf(this.mFinanceCubeId).hashCode();
/*  62 */       this.mHashCode += String.valueOf(this.mRowNo).hashCode();
/*     */     }
/*     */ 
/*  65 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  73 */     CubePendingTranPK other = null;
/*     */ 
/*  75 */     if ((obj instanceof CubePendingTranCK)) {
/*  76 */       other = ((CubePendingTranCK)obj).getCubePendingTranPK();
/*     */     }
/*  78 */     else if ((obj instanceof CubePendingTranPK))
/*  79 */       other = (CubePendingTranPK)obj;
/*     */     else {
/*  81 */       return false;
/*     */     }
/*  83 */     boolean eq = true;
/*     */ 
/*  85 */     eq = (eq) && (this.mReportId == other.mReportId);
/*  86 */     eq = (eq) && (this.mFinanceCubeId == other.mFinanceCubeId);
/*  87 */     eq = (eq) && (this.mRowNo == other.mRowNo);
/*     */ 
/*  89 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  97 */     StringBuffer sb = new StringBuffer();
/*  98 */     sb.append(" ReportId=");
/*  99 */     sb.append(this.mReportId);
/* 100 */     sb.append(",FinanceCubeId=");
/* 101 */     sb.append(this.mFinanceCubeId);
/* 102 */     sb.append(",RowNo=");
/* 103 */     sb.append(this.mRowNo);
/* 104 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 112 */     StringBuffer sb = new StringBuffer();
/* 113 */     sb.append(" ");
/* 114 */     sb.append(this.mReportId);
/* 115 */     sb.append(",");
/* 116 */     sb.append(this.mFinanceCubeId);
/* 117 */     sb.append(",");
/* 118 */     sb.append(this.mRowNo);
/* 119 */     return "CubePendingTranPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static CubePendingTranPK getKeyFromTokens(String extKey)
/*     */   {
/* 124 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 126 */     if (extValues.length != 2) {
/* 127 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 129 */     if (!extValues[0].equals("CubePendingTranPK")) {
/* 130 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'CubePendingTranPK|'");
/*     */     }
/* 132 */     extValues = extValues[1].split(",");
/*     */ 
/* 134 */     int i = 0;
/* 135 */     int pReportId = new Integer(extValues[(i++)]).intValue();
/* 136 */     int pFinanceCubeId = new Integer(extValues[(i++)]).intValue();
/* 137 */     int pRowNo = new Integer(extValues[(i++)]).intValue();
/* 138 */     return new CubePendingTranPK(pReportId, pFinanceCubeId, pRowNo);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.tran.CubePendingTranPK
 * JD-Core Version:    0.6.0
 */