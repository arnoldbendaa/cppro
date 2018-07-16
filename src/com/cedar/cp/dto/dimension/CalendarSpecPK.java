/*     */ package com.cedar.cp.dto.dimension;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CalendarSpecPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mCalendarSpecId;
/*     */ 
/*     */   public CalendarSpecPK(int newCalendarSpecId)
/*     */   {
/*  23 */     this.mCalendarSpecId = newCalendarSpecId;
/*     */   }
/*     */ 
/*     */   public int getCalendarSpecId()
/*     */   {
/*  32 */     return this.mCalendarSpecId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mCalendarSpecId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     CalendarSpecPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof CalendarSpecCK)) {
/*  56 */       other = ((CalendarSpecCK)obj).getCalendarSpecPK();
/*     */     }
/*  58 */     else if ((obj instanceof CalendarSpecPK))
/*  59 */       other = (CalendarSpecPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mCalendarSpecId == other.mCalendarSpecId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" CalendarSpecId=");
/*  77 */     sb.append(this.mCalendarSpecId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mCalendarSpecId);
/*  89 */     return "CalendarSpecPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static CalendarSpecPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("CalendarSpecPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'CalendarSpecPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pCalendarSpecId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new CalendarSpecPK(pCalendarSpecId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.dimension.CalendarSpecPK
 * JD-Core Version:    0.6.0
 */