/*     */ package com.cedar.cp.dto.logonhistory;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class LogonHistoryCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected LogonHistoryPK mLogonHistoryPK;
/*     */ 
/*     */   public LogonHistoryCK(LogonHistoryPK paramLogonHistoryPK)
/*     */   {
/*  26 */     this.mLogonHistoryPK = paramLogonHistoryPK;
/*     */   }
/*     */ 
/*     */   public LogonHistoryPK getLogonHistoryPK()
/*     */   {
/*  34 */     return this.mLogonHistoryPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mLogonHistoryPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mLogonHistoryPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof LogonHistoryPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof LogonHistoryCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     LogonHistoryCK other = (LogonHistoryCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mLogonHistoryPK.equals(other.mLogonHistoryPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mLogonHistoryPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("LogonHistoryCK|");
/*  92 */     sb.append(this.mLogonHistoryPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static LogonHistoryCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("LogonHistoryCK", token[(i++)]);
/* 101 */     checkExpected("LogonHistoryPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new LogonHistoryCK(LogonHistoryPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.logonhistory.LogonHistoryCK
 * JD-Core Version:    0.6.0
 */