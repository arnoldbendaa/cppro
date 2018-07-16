/*     */ package com.cedar.cp.dto.dimension;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CalendarYearSpecCK extends DimensionCK
/*     */   implements Serializable
/*     */ {
/*     */   protected CalendarYearSpecPK mCalendarYearSpecPK;
/*     */ 
/*     */   public CalendarYearSpecCK(DimensionPK paramDimensionPK, CalendarYearSpecPK paramCalendarYearSpecPK)
/*     */   {
/*  29 */     super(paramDimensionPK);
/*     */ 
/*  32 */     this.mCalendarYearSpecPK = paramCalendarYearSpecPK;
/*     */   }
/*     */ 
/*     */   public CalendarYearSpecPK getCalendarYearSpecPK()
/*     */   {
/*  40 */     return this.mCalendarYearSpecPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mCalendarYearSpecPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mCalendarYearSpecPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof CalendarYearSpecPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof CalendarYearSpecCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     CalendarYearSpecCK other = (CalendarYearSpecCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mDimensionPK.equals(other.mDimensionPK));
/*  75 */     eq = (eq) && (this.mCalendarYearSpecPK.equals(other.mCalendarYearSpecPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mCalendarYearSpecPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("CalendarYearSpecCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mCalendarYearSpecPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static DimensionCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("CalendarYearSpecCK", token[(i++)]);
/* 111 */     checkExpected("DimensionPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("CalendarYearSpecPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new CalendarYearSpecCK(DimensionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), CalendarYearSpecPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.dimension.CalendarYearSpecCK
 * JD-Core Version:    0.6.0
 */