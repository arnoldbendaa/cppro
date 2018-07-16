/*     */ package com.cedar.cp.dto.report.tran;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.report.ReportCK;
/*     */ import com.cedar.cp.dto.report.ReportPK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CubePendingTranCK extends ReportCK
/*     */   implements Serializable
/*     */ {
/*     */   protected CubePendingTranPK mCubePendingTranPK;
/*     */ 
/*     */   public CubePendingTranCK(ReportPK paramReportPK, CubePendingTranPK paramCubePendingTranPK)
/*     */   {
/*  30 */     super(paramReportPK);
/*     */ 
/*  33 */     this.mCubePendingTranPK = paramCubePendingTranPK;
/*     */   }
/*     */ 
/*     */   public CubePendingTranPK getCubePendingTranPK()
/*     */   {
/*  41 */     return this.mCubePendingTranPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  49 */     return this.mCubePendingTranPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  57 */     return this.mCubePendingTranPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  66 */     if ((obj instanceof CubePendingTranPK)) {
/*  67 */       return obj.equals(this);
/*     */     }
/*  69 */     if (!(obj instanceof CubePendingTranCK)) {
/*  70 */       return false;
/*     */     }
/*  72 */     CubePendingTranCK other = (CubePendingTranCK)obj;
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mReportPK.equals(other.mReportPK));
/*  76 */     eq = (eq) && (this.mCubePendingTranPK.equals(other.mCubePendingTranPK));
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(super.toString());
/*  88 */     sb.append("[");
/*  89 */     sb.append(this.mCubePendingTranPK);
/*  90 */     sb.append("]");
/*  91 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append("CubePendingTranCK|");
/* 101 */     sb.append(super.getPK().toTokens());
/* 102 */     sb.append('|');
/* 103 */     sb.append(this.mCubePendingTranPK.toTokens());
/* 104 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ReportCK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] token = extKey.split("[|]");
/* 110 */     int i = 0;
/* 111 */     checkExpected("CubePendingTranCK", token[(i++)]);
/* 112 */     checkExpected("ReportPK", token[(i++)]);
/* 113 */     i++;
/* 114 */     checkExpected("CubePendingTranPK", token[(i++)]);
/* 115 */     i = 1;
/* 116 */     return new CubePendingTranCK(ReportPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), CubePendingTranPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 124 */     if (!expected.equals(found))
/* 125 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.tran.CubePendingTranCK
 * JD-Core Version:    0.6.0
 */