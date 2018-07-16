/*     */ package com.cedar.cp.dto.extsys;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExtSysCompanyCK extends ExternalSystemCK
/*     */   implements Serializable
/*     */ {
/*     */   protected ExtSysCompanyPK mExtSysCompanyPK;
/*     */ 
/*     */   public ExtSysCompanyCK(ExternalSystemPK paramExternalSystemPK, ExtSysCompanyPK paramExtSysCompanyPK)
/*     */   {
/*  29 */     super(paramExternalSystemPK);
/*     */ 
/*  32 */     this.mExtSysCompanyPK = paramExtSysCompanyPK;
/*     */   }
/*     */ 
/*     */   public ExtSysCompanyPK getExtSysCompanyPK()
/*     */   {
/*  40 */     return this.mExtSysCompanyPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mExtSysCompanyPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mExtSysCompanyPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof ExtSysCompanyPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof ExtSysCompanyCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     ExtSysCompanyCK other = (ExtSysCompanyCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mExternalSystemPK.equals(other.mExternalSystemPK));
/*  75 */     eq = (eq) && (this.mExtSysCompanyPK.equals(other.mExtSysCompanyPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mExtSysCompanyPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("ExtSysCompanyCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mExtSysCompanyPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ExternalSystemCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("ExtSysCompanyCK", token[(i++)]);
/* 111 */     checkExpected("ExternalSystemPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("ExtSysCompanyPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new ExtSysCompanyCK(ExternalSystemPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysCompanyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extsys.ExtSysCompanyCK
 * JD-Core Version:    0.6.0
 */