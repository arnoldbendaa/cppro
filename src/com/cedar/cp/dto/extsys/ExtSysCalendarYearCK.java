/*     */ package com.cedar.cp.dto.extsys;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExtSysCalendarYearCK extends ExtSysCompanyCK
/*     */   implements Serializable
/*     */ {
/*     */   protected ExtSysCalendarYearPK mExtSysCalendarYearPK;
/*     */ 
/*     */   public ExtSysCalendarYearCK(ExternalSystemPK paramExternalSystemPK, ExtSysCompanyPK paramExtSysCompanyPK, ExtSysCalendarYearPK paramExtSysCalendarYearPK)
/*     */   {
/*  31 */     super(paramExternalSystemPK, paramExtSysCompanyPK);
/*     */ 
/*  35 */     this.mExtSysCalendarYearPK = paramExtSysCalendarYearPK;
/*     */   }
/*     */ 
/*     */   public ExtSysCalendarYearPK getExtSysCalendarYearPK()
/*     */   {
/*  43 */     return this.mExtSysCalendarYearPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  51 */     return this.mExtSysCalendarYearPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  59 */     return this.mExtSysCalendarYearPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  68 */     if ((obj instanceof ExtSysCalendarYearPK)) {
/*  69 */       return obj.equals(this);
/*     */     }
/*  71 */     if (!(obj instanceof ExtSysCalendarYearCK)) {
/*  72 */       return false;
/*     */     }
/*  74 */     ExtSysCalendarYearCK other = (ExtSysCalendarYearCK)obj;
/*  75 */     boolean eq = true;
/*     */ 
/*  77 */     eq = (eq) && (this.mExternalSystemPK.equals(other.mExternalSystemPK));
/*  78 */     eq = (eq) && (this.mExtSysCompanyPK.equals(other.mExtSysCompanyPK));
/*  79 */     eq = (eq) && (this.mExtSysCalendarYearPK.equals(other.mExtSysCalendarYearPK));
/*     */ 
/*  81 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  89 */     StringBuffer sb = new StringBuffer();
/*  90 */     sb.append(super.toString());
/*  91 */     sb.append("[");
/*  92 */     sb.append(this.mExtSysCalendarYearPK);
/*  93 */     sb.append("]");
/*  94 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 102 */     StringBuffer sb = new StringBuffer();
/* 103 */     sb.append("ExtSysCalendarYearCK|");
/* 104 */     sb.append(super.getPK().toTokens());
/* 105 */     sb.append('|');
/* 106 */     sb.append(this.mExtSysCalendarYearPK.toTokens());
/* 107 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ExternalSystemCK getKeyFromTokens(String extKey)
/*     */   {
/* 112 */     String[] token = extKey.split("[|]");
/* 113 */     int i = 0;
/* 114 */     checkExpected("ExtSysCalendarYearCK", token[(i++)]);
/* 115 */     checkExpected("ExternalSystemPK", token[(i++)]);
/* 116 */     i++;
/* 117 */     checkExpected("ExtSysCompanyPK", token[(i++)]);
/* 118 */     i++;
/* 119 */     checkExpected("ExtSysCalendarYearPK", token[(i++)]);
/* 120 */     i = 1;
/* 121 */     return new ExtSysCalendarYearCK(ExternalSystemPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysCompanyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysCalendarYearPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 130 */     if (!expected.equals(found))
/* 131 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extsys.ExtSysCalendarYearCK
 * JD-Core Version:    0.6.0
 */