/*     */ package com.cedar.cp.dto.extsys;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExtSysDimElementCK extends ExtSysDimensionCK
/*     */   implements Serializable
/*     */ {
/*     */   protected ExtSysDimElementPK mExtSysDimElementPK;
/*     */ 
/*     */   public ExtSysDimElementCK(ExternalSystemPK paramExternalSystemPK, ExtSysCompanyPK paramExtSysCompanyPK, ExtSysLedgerPK paramExtSysLedgerPK, ExtSysDimensionPK paramExtSysDimensionPK, ExtSysDimElementPK paramExtSysDimElementPK)
/*     */   {
/*  35 */     super(paramExternalSystemPK, paramExtSysCompanyPK, paramExtSysLedgerPK, paramExtSysDimensionPK);
/*     */ 
/*  41 */     this.mExtSysDimElementPK = paramExtSysDimElementPK;
/*     */   }
/*     */ 
/*     */   public ExtSysDimElementPK getExtSysDimElementPK()
/*     */   {
/*  49 */     return this.mExtSysDimElementPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  57 */     return this.mExtSysDimElementPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  65 */     return this.mExtSysDimElementPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  74 */     if ((obj instanceof ExtSysDimElementPK)) {
/*  75 */       return obj.equals(this);
/*     */     }
/*  77 */     if (!(obj instanceof ExtSysDimElementCK)) {
/*  78 */       return false;
/*     */     }
/*  80 */     ExtSysDimElementCK other = (ExtSysDimElementCK)obj;
/*  81 */     boolean eq = true;
/*     */ 
/*  83 */     eq = (eq) && (this.mExternalSystemPK.equals(other.mExternalSystemPK));
/*  84 */     eq = (eq) && (this.mExtSysCompanyPK.equals(other.mExtSysCompanyPK));
/*  85 */     eq = (eq) && (this.mExtSysLedgerPK.equals(other.mExtSysLedgerPK));
/*  86 */     eq = (eq) && (this.mExtSysDimensionPK.equals(other.mExtSysDimensionPK));
/*  87 */     eq = (eq) && (this.mExtSysDimElementPK.equals(other.mExtSysDimElementPK));
/*     */ 
/*  89 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  97 */     StringBuffer sb = new StringBuffer();
/*  98 */     sb.append(super.toString());
/*  99 */     sb.append("[");
/* 100 */     sb.append(this.mExtSysDimElementPK);
/* 101 */     sb.append("]");
/* 102 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 110 */     StringBuffer sb = new StringBuffer();
/* 111 */     sb.append("ExtSysDimElementCK|");
/* 112 */     sb.append(super.getPK().toTokens());
/* 113 */     sb.append('|');
/* 114 */     sb.append(this.mExtSysDimElementPK.toTokens());
/* 115 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ExternalSystemCK getKeyFromTokens(String extKey)
/*     */   {
/* 120 */     String[] token = extKey.split("[|]");
/* 121 */     int i = 0;
/* 122 */     checkExpected("ExtSysDimElementCK", token[(i++)]);
/* 123 */     checkExpected("ExternalSystemPK", token[(i++)]);
/* 124 */     i++;
/* 125 */     checkExpected("ExtSysCompanyPK", token[(i++)]);
/* 126 */     i++;
/* 127 */     checkExpected("ExtSysLedgerPK", token[(i++)]);
/* 128 */     i++;
/* 129 */     checkExpected("ExtSysDimensionPK", token[(i++)]);
/* 130 */     i++;
/* 131 */     checkExpected("ExtSysDimElementPK", token[(i++)]);
/* 132 */     i = 1;
/* 133 */     return new ExtSysDimElementCK(ExternalSystemPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysCompanyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysLedgerPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysDimensionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysDimElementPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 144 */     if (!expected.equals(found))
/* 145 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extsys.ExtSysDimElementCK
 * JD-Core Version:    0.6.0
 */