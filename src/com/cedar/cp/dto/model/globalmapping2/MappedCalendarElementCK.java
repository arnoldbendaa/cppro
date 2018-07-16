/*     */ package com.cedar.cp.dto.model.globalmapping2;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MappedCalendarElementCK extends MappedCalendarYearCK
/*     */   implements Serializable
/*     */ {
/*     */   protected MappedCalendarElementPK mMappedCalendarElementPK;
/*     */ 
/*     */   public MappedCalendarElementCK(GlobalMappedModel2PK paramMappedModelPK, MappedCalendarYearPK paramMappedCalendarYearPK, MappedCalendarElementPK paramMappedCalendarElementPK)
/*     */   {
/*  31 */     super(paramMappedModelPK, paramMappedCalendarYearPK);
/*     */ 
/*  35 */     this.mMappedCalendarElementPK = paramMappedCalendarElementPK;
/*     */   }
/*     */ 
/*     */   public MappedCalendarElementPK getMappedCalendarElementPK()
/*     */   {
/*  43 */     return this.mMappedCalendarElementPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  51 */     return this.mMappedCalendarElementPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  59 */     return this.mMappedCalendarElementPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  68 */     if ((obj instanceof MappedCalendarElementPK)) {
/*  69 */       return obj.equals(this);
/*     */     }
/*  71 */     if (!(obj instanceof MappedCalendarElementCK)) {
/*  72 */       return false;
/*     */     }
/*  74 */     MappedCalendarElementCK other = (MappedCalendarElementCK)obj;
/*  75 */     boolean eq = true;
/*     */ 
/*  77 */     eq = (eq) && (this.mMappedModelPK.equals(other.mMappedModelPK));
/*  78 */     eq = (eq) && (this.mMappedCalendarYearPK.equals(other.mMappedCalendarYearPK));
/*  79 */     eq = (eq) && (this.mMappedCalendarElementPK.equals(other.mMappedCalendarElementPK));
/*     */ 
/*  81 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  89 */     StringBuffer sb = new StringBuffer();
/*  90 */     sb.append(super.toString());
/*  91 */     sb.append("[");
/*  92 */     sb.append(this.mMappedCalendarElementPK);
/*  93 */     sb.append("]");
/*  94 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 102 */     StringBuffer sb = new StringBuffer();
/* 103 */     sb.append("MappedCalendarElementCK|");
/* 104 */     sb.append(super.getPK().toTokens());
/* 105 */     sb.append('|');
/* 106 */     sb.append(this.mMappedCalendarElementPK.toTokens());
/* 107 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static GlobalMappedModel2CK getKeyFromTokens(String extKey)
/*     */   {
/* 112 */     String[] token = extKey.split("[|]");
/* 113 */     int i = 0;
/* 114 */     checkExpected("MappedCalendarElementCK", token[(i++)]);
/* 115 */     checkExpected("GlobalMappedModel2PK", token[(i++)]);
/* 116 */     i++;
/* 117 */     checkExpected("MappedCalendarYearPK", token[(i++)]);
/* 118 */     i++;
/* 119 */     checkExpected("MappedCalendarElementPK", token[(i++)]);
/* 120 */     i = 1;
/* 121 */     return new MappedCalendarElementCK(GlobalMappedModel2PK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), MappedCalendarYearPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), MappedCalendarElementPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 130 */     if (!expected.equals(found))
/* 131 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.mapping.MappedCalendarElementCK
 * JD-Core Version:    0.6.0
 */