/*     */ package com.cedar.cp.dto.model.mapping;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MappedCalendarYearPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mMappedCalendarYearId;
/*     */ 
/*     */   public MappedCalendarYearPK(int newMappedCalendarYearId)
/*     */   {
/*  23 */     this.mMappedCalendarYearId = newMappedCalendarYearId;
/*     */   }
/*     */ 
/*     */   public int getMappedCalendarYearId()
/*     */   {
/*  32 */     return this.mMappedCalendarYearId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mMappedCalendarYearId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     MappedCalendarYearPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof MappedCalendarYearCK)) {
/*  56 */       other = ((MappedCalendarYearCK)obj).getMappedCalendarYearPK();
/*     */     }
/*  58 */     else if ((obj instanceof MappedCalendarYearPK))
/*  59 */       other = (MappedCalendarYearPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mMappedCalendarYearId == other.mMappedCalendarYearId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" MappedCalendarYearId=");
/*  77 */     sb.append(this.mMappedCalendarYearId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mMappedCalendarYearId);
/*  89 */     return "MappedCalendarYearPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static MappedCalendarYearPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("MappedCalendarYearPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'MappedCalendarYearPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pMappedCalendarYearId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new MappedCalendarYearPK(pMappedCalendarYearId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.mapping.MappedCalendarYearPK
 * JD-Core Version:    0.6.0
 */