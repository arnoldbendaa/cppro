/*     */ package com.cedar.cp.dto.extsys;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExtSysLedgerCK extends ExtSysCompanyCK
/*     */   implements Serializable
/*     */ {
/*     */   protected ExtSysLedgerPK mExtSysLedgerPK;
/*     */ 
/*     */   public ExtSysLedgerCK(ExternalSystemPK paramExternalSystemPK, ExtSysCompanyPK paramExtSysCompanyPK, ExtSysLedgerPK paramExtSysLedgerPK)
/*     */   {
/*  31 */     super(paramExternalSystemPK, paramExtSysCompanyPK);
/*     */ 
/*  35 */     this.mExtSysLedgerPK = paramExtSysLedgerPK;
/*     */   }
/*     */ 
/*     */   public ExtSysLedgerPK getExtSysLedgerPK()
/*     */   {
/*  43 */     return this.mExtSysLedgerPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  51 */     return this.mExtSysLedgerPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  59 */     return this.mExtSysLedgerPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  68 */     if ((obj instanceof ExtSysLedgerPK)) {
/*  69 */       return obj.equals(this);
/*     */     }
/*  71 */     if (!(obj instanceof ExtSysLedgerCK)) {
/*  72 */       return false;
/*     */     }
/*  74 */     ExtSysLedgerCK other = (ExtSysLedgerCK)obj;
/*  75 */     boolean eq = true;
/*     */ 
/*  77 */     eq = (eq) && (this.mExternalSystemPK.equals(other.mExternalSystemPK));
/*  78 */     eq = (eq) && (this.mExtSysCompanyPK.equals(other.mExtSysCompanyPK));
/*  79 */     eq = (eq) && (this.mExtSysLedgerPK.equals(other.mExtSysLedgerPK));
/*     */ 
/*  81 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  89 */     StringBuffer sb = new StringBuffer();
/*  90 */     sb.append(super.toString());
/*  91 */     sb.append("[");
/*  92 */     sb.append(this.mExtSysLedgerPK);
/*  93 */     sb.append("]");
/*  94 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 102 */     StringBuffer sb = new StringBuffer();
/* 103 */     sb.append("ExtSysLedgerCK|");
/* 104 */     sb.append(super.getPK().toTokens());
/* 105 */     sb.append('|');
/* 106 */     sb.append(this.mExtSysLedgerPK.toTokens());
/* 107 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ExternalSystemCK getKeyFromTokens(String extKey)
/*     */   {
/* 112 */     String[] token = extKey.split("[|]");
/* 113 */     int i = 0;
/* 114 */     checkExpected("ExtSysLedgerCK", token[(i++)]);
/* 115 */     checkExpected("ExternalSystemPK", token[(i++)]);
/* 116 */     i++;
/* 117 */     checkExpected("ExtSysCompanyPK", token[(i++)]);
/* 118 */     i++;
/* 119 */     checkExpected("ExtSysLedgerPK", token[(i++)]);
/* 120 */     i = 1;
/* 121 */     return new ExtSysLedgerCK(ExternalSystemPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysCompanyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysLedgerPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 130 */     if (!expected.equals(found))
/* 131 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extsys.ExtSysLedgerCK
 * JD-Core Version:    0.6.0
 */