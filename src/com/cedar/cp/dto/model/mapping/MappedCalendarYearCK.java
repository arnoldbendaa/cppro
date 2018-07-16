/*     */ package com.cedar.cp.dto.model.mapping;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MappedCalendarYearCK extends MappedModelCK
/*     */   implements Serializable
/*     */ {
/*     */   protected MappedCalendarYearPK mMappedCalendarYearPK;
/*     */ 
/*     */   public MappedCalendarYearCK(MappedModelPK paramMappedModelPK, MappedCalendarYearPK paramMappedCalendarYearPK)
/*     */   {
/*  29 */     super(paramMappedModelPK);
/*     */ 
/*  32 */     this.mMappedCalendarYearPK = paramMappedCalendarYearPK;
/*     */   }
/*     */ 
/*     */   public MappedCalendarYearPK getMappedCalendarYearPK()
/*     */   {
/*  40 */     return this.mMappedCalendarYearPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mMappedCalendarYearPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mMappedCalendarYearPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof MappedCalendarYearPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof MappedCalendarYearCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     MappedCalendarYearCK other = (MappedCalendarYearCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mMappedModelPK.equals(other.mMappedModelPK));
/*  75 */     eq = (eq) && (this.mMappedCalendarYearPK.equals(other.mMappedCalendarYearPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mMappedCalendarYearPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("MappedCalendarYearCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mMappedCalendarYearPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static MappedModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("MappedCalendarYearCK", token[(i++)]);
/* 111 */     checkExpected("MappedModelPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("MappedCalendarYearPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new MappedCalendarYearCK(MappedModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), MappedCalendarYearPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.mapping.MappedCalendarYearCK
 * JD-Core Version:    0.6.0
 */