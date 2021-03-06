/*     */ package com.cedar.cp.dto.extsys;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExtSysCalElementCK extends ExtSysCalendarYearCK
/*     */   implements Serializable
/*     */ {
/*     */   protected ExtSysCalElementPK mExtSysCalElementPK;
/*     */ 
/*     */   public ExtSysCalElementCK(ExternalSystemPK paramExternalSystemPK, ExtSysCompanyPK paramExtSysCompanyPK, ExtSysCalendarYearPK paramExtSysCalendarYearPK, ExtSysCalElementPK paramExtSysCalElementPK)
/*     */   {
/*  33 */     super(paramExternalSystemPK, paramExtSysCompanyPK, paramExtSysCalendarYearPK);
/*     */ 
/*  38 */     this.mExtSysCalElementPK = paramExtSysCalElementPK;
/*     */   }
/*     */ 
/*     */   public ExtSysCalElementPK getExtSysCalElementPK()
/*     */   {
/*  46 */     return this.mExtSysCalElementPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  54 */     return this.mExtSysCalElementPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  62 */     return this.mExtSysCalElementPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  71 */     if ((obj instanceof ExtSysCalElementPK)) {
/*  72 */       return obj.equals(this);
/*     */     }
/*  74 */     if (!(obj instanceof ExtSysCalElementCK)) {
/*  75 */       return false;
/*     */     }
/*  77 */     ExtSysCalElementCK other = (ExtSysCalElementCK)obj;
/*  78 */     boolean eq = true;
/*     */ 
/*  80 */     eq = (eq) && (this.mExternalSystemPK.equals(other.mExternalSystemPK));
/*  81 */     eq = (eq) && (this.mExtSysCompanyPK.equals(other.mExtSysCompanyPK));
/*  82 */     eq = (eq) && (this.mExtSysCalendarYearPK.equals(other.mExtSysCalendarYearPK));
/*  83 */     eq = (eq) && (this.mExtSysCalElementPK.equals(other.mExtSysCalElementPK));
/*     */ 
/*  85 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  93 */     StringBuffer sb = new StringBuffer();
/*  94 */     sb.append(super.toString());
/*  95 */     sb.append("[");
/*  96 */     sb.append(this.mExtSysCalElementPK);
/*  97 */     sb.append("]");
/*  98 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 106 */     StringBuffer sb = new StringBuffer();
/* 107 */     sb.append("ExtSysCalElementCK|");
/* 108 */     sb.append(super.getPK().toTokens());
/* 109 */     sb.append('|');
/* 110 */     sb.append(this.mExtSysCalElementPK.toTokens());
/* 111 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ExternalSystemCK getKeyFromTokens(String extKey)
/*     */   {
/* 116 */     String[] token = extKey.split("[|]");
/* 117 */     int i = 0;
/* 118 */     checkExpected("ExtSysCalElementCK", token[(i++)]);
/* 119 */     checkExpected("ExternalSystemPK", token[(i++)]);
/* 120 */     i++;
/* 121 */     checkExpected("ExtSysCompanyPK", token[(i++)]);
/* 122 */     i++;
/* 123 */     checkExpected("ExtSysCalendarYearPK", token[(i++)]);
/* 124 */     i++;
/* 125 */     checkExpected("ExtSysCalElementPK", token[(i++)]);
/* 126 */     i = 1;
/* 127 */     return new ExtSysCalElementCK(ExternalSystemPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysCompanyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysCalendarYearPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysCalElementPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 137 */     if (!expected.equals(found))
/* 138 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extsys.ExtSysCalElementCK
 * JD-Core Version:    0.6.0
 */