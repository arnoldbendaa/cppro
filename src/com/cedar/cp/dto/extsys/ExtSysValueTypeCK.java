/*     */ package com.cedar.cp.dto.extsys;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExtSysValueTypeCK extends ExtSysLedgerCK
/*     */   implements Serializable
/*     */ {
/*     */   protected ExtSysValueTypePK mExtSysValueTypePK;
/*     */ 
/*     */   public ExtSysValueTypeCK(ExternalSystemPK paramExternalSystemPK, ExtSysCompanyPK paramExtSysCompanyPK, ExtSysLedgerPK paramExtSysLedgerPK, ExtSysValueTypePK paramExtSysValueTypePK)
/*     */   {
/*  33 */     super(paramExternalSystemPK, paramExtSysCompanyPK, paramExtSysLedgerPK);
/*     */ 
/*  38 */     this.mExtSysValueTypePK = paramExtSysValueTypePK;
/*     */   }
/*     */ 
/*     */   public ExtSysValueTypePK getExtSysValueTypePK()
/*     */   {
/*  46 */     return this.mExtSysValueTypePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  54 */     return this.mExtSysValueTypePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  62 */     return this.mExtSysValueTypePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  71 */     if ((obj instanceof ExtSysValueTypePK)) {
/*  72 */       return obj.equals(this);
/*     */     }
/*  74 */     if (!(obj instanceof ExtSysValueTypeCK)) {
/*  75 */       return false;
/*     */     }
/*  77 */     ExtSysValueTypeCK other = (ExtSysValueTypeCK)obj;
/*  78 */     boolean eq = true;
/*     */ 
/*  80 */     eq = (eq) && (this.mExternalSystemPK.equals(other.mExternalSystemPK));
/*  81 */     eq = (eq) && (this.mExtSysCompanyPK.equals(other.mExtSysCompanyPK));
/*  82 */     eq = (eq) && (this.mExtSysLedgerPK.equals(other.mExtSysLedgerPK));
/*  83 */     eq = (eq) && (this.mExtSysValueTypePK.equals(other.mExtSysValueTypePK));
/*     */ 
/*  85 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  93 */     StringBuffer sb = new StringBuffer();
/*  94 */     sb.append(super.toString());
/*  95 */     sb.append("[");
/*  96 */     sb.append(this.mExtSysValueTypePK);
/*  97 */     sb.append("]");
/*  98 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 106 */     StringBuffer sb = new StringBuffer();
/* 107 */     sb.append("ExtSysValueTypeCK|");
/* 108 */     sb.append(super.getPK().toTokens());
/* 109 */     sb.append('|');
/* 110 */     sb.append(this.mExtSysValueTypePK.toTokens());
/* 111 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ExternalSystemCK getKeyFromTokens(String extKey)
/*     */   {
/* 116 */     String[] token = extKey.split("[|]");
/* 117 */     int i = 0;
/* 118 */     checkExpected("ExtSysValueTypeCK", token[(i++)]);
/* 119 */     checkExpected("ExternalSystemPK", token[(i++)]);
/* 120 */     i++;
/* 121 */     checkExpected("ExtSysCompanyPK", token[(i++)]);
/* 122 */     i++;
/* 123 */     checkExpected("ExtSysLedgerPK", token[(i++)]);
/* 124 */     i++;
/* 125 */     checkExpected("ExtSysValueTypePK", token[(i++)]);
/* 126 */     i = 1;
/* 127 */     return new ExtSysValueTypeCK(ExternalSystemPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysCompanyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysLedgerPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysValueTypePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 137 */     if (!expected.equals(found))
/* 138 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extsys.ExtSysValueTypeCK
 * JD-Core Version:    0.6.0
 */