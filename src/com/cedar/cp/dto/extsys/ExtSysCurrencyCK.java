/*     */ package com.cedar.cp.dto.extsys;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExtSysCurrencyCK extends ExtSysLedgerCK
/*     */   implements Serializable
/*     */ {
/*     */   protected ExtSysCurrencyPK mExtSysCurrencyPK;
/*     */ 
/*     */   public ExtSysCurrencyCK(ExternalSystemPK paramExternalSystemPK, ExtSysCompanyPK paramExtSysCompanyPK, ExtSysLedgerPK paramExtSysLedgerPK, ExtSysCurrencyPK paramExtSysCurrencyPK)
/*     */   {
/*  33 */     super(paramExternalSystemPK, paramExtSysCompanyPK, paramExtSysLedgerPK);
/*     */ 
/*  38 */     this.mExtSysCurrencyPK = paramExtSysCurrencyPK;
/*     */   }
/*     */ 
/*     */   public ExtSysCurrencyPK getExtSysCurrencyPK()
/*     */   {
/*  46 */     return this.mExtSysCurrencyPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  54 */     return this.mExtSysCurrencyPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  62 */     return this.mExtSysCurrencyPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  71 */     if ((obj instanceof ExtSysCurrencyPK)) {
/*  72 */       return obj.equals(this);
/*     */     }
/*  74 */     if (!(obj instanceof ExtSysCurrencyCK)) {
/*  75 */       return false;
/*     */     }
/*  77 */     ExtSysCurrencyCK other = (ExtSysCurrencyCK)obj;
/*  78 */     boolean eq = true;
/*     */ 
/*  80 */     eq = (eq) && (this.mExternalSystemPK.equals(other.mExternalSystemPK));
/*  81 */     eq = (eq) && (this.mExtSysCompanyPK.equals(other.mExtSysCompanyPK));
/*  82 */     eq = (eq) && (this.mExtSysLedgerPK.equals(other.mExtSysLedgerPK));
/*  83 */     eq = (eq) && (this.mExtSysCurrencyPK.equals(other.mExtSysCurrencyPK));
/*     */ 
/*  85 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  93 */     StringBuffer sb = new StringBuffer();
/*  94 */     sb.append(super.toString());
/*  95 */     sb.append("[");
/*  96 */     sb.append(this.mExtSysCurrencyPK);
/*  97 */     sb.append("]");
/*  98 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 106 */     StringBuffer sb = new StringBuffer();
/* 107 */     sb.append("ExtSysCurrencyCK|");
/* 108 */     sb.append(super.getPK().toTokens());
/* 109 */     sb.append('|');
/* 110 */     sb.append(this.mExtSysCurrencyPK.toTokens());
/* 111 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ExternalSystemCK getKeyFromTokens(String extKey)
/*     */   {
/* 116 */     String[] token = extKey.split("[|]");
/* 117 */     int i = 0;
/* 118 */     checkExpected("ExtSysCurrencyCK", token[(i++)]);
/* 119 */     checkExpected("ExternalSystemPK", token[(i++)]);
/* 120 */     i++;
/* 121 */     checkExpected("ExtSysCompanyPK", token[(i++)]);
/* 122 */     i++;
/* 123 */     checkExpected("ExtSysLedgerPK", token[(i++)]);
/* 124 */     i++;
/* 125 */     checkExpected("ExtSysCurrencyPK", token[(i++)]);
/* 126 */     i = 1;
/* 127 */     return new ExtSysCurrencyCK(ExternalSystemPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysCompanyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysLedgerPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysCurrencyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 137 */     if (!expected.equals(found))
/* 138 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extsys.ExtSysCurrencyCK
 * JD-Core Version:    0.6.0
 */