/*     */ package com.cedar.cp.dto.dimension;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CalendarSpecCK extends DimensionCK
/*     */   implements Serializable
/*     */ {
/*     */   protected CalendarSpecPK mCalendarSpecPK;
/*     */ 
/*     */   public CalendarSpecCK(DimensionPK paramDimensionPK, CalendarSpecPK paramCalendarSpecPK)
/*     */   {
/*  29 */     super(paramDimensionPK);
/*     */ 
/*  32 */     this.mCalendarSpecPK = paramCalendarSpecPK;
/*     */   }
/*     */ 
/*     */   public CalendarSpecPK getCalendarSpecPK()
/*     */   {
/*  40 */     return this.mCalendarSpecPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mCalendarSpecPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mCalendarSpecPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof CalendarSpecPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof CalendarSpecCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     CalendarSpecCK other = (CalendarSpecCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mDimensionPK.equals(other.mDimensionPK));
/*  75 */     eq = (eq) && (this.mCalendarSpecPK.equals(other.mCalendarSpecPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mCalendarSpecPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("CalendarSpecCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mCalendarSpecPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static DimensionCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("CalendarSpecCK", token[(i++)]);
/* 111 */     checkExpected("DimensionPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("CalendarSpecPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new CalendarSpecCK(DimensionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), CalendarSpecPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.dimension.CalendarSpecCK
 * JD-Core Version:    0.6.0
 */