/*     */ package com.cedar.cp.dto.report.pack;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ReportPackLinkCK extends ReportPackCK
/*     */   implements Serializable
/*     */ {
/*     */   protected ReportPackLinkPK mReportPackLinkPK;
/*     */ 
/*     */   public ReportPackLinkCK(ReportPackPK paramReportPackPK, ReportPackLinkPK paramReportPackLinkPK)
/*     */   {
/*  29 */     super(paramReportPackPK);
/*     */ 
/*  32 */     this.mReportPackLinkPK = paramReportPackLinkPK;
/*     */   }
/*     */ 
/*     */   public ReportPackLinkPK getReportPackLinkPK()
/*     */   {
/*  40 */     return this.mReportPackLinkPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mReportPackLinkPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mReportPackLinkPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof ReportPackLinkPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof ReportPackLinkCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     ReportPackLinkCK other = (ReportPackLinkCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mReportPackPK.equals(other.mReportPackPK));
/*  75 */     eq = (eq) && (this.mReportPackLinkPK.equals(other.mReportPackLinkPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mReportPackLinkPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("ReportPackLinkCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mReportPackLinkPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ReportPackCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("ReportPackLinkCK", token[(i++)]);
/* 111 */     checkExpected("ReportPackPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("ReportPackLinkPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new ReportPackLinkCK(ReportPackPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ReportPackLinkPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.pack.ReportPackLinkCK
 * JD-Core Version:    0.6.0
 */