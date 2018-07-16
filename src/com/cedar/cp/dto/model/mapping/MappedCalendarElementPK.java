/*     */ package com.cedar.cp.dto.model.mapping;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MappedCalendarElementPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mMappedCalendarElementId;
/*     */ 
/*     */   public MappedCalendarElementPK(int newMappedCalendarElementId)
/*     */   {
/*  23 */     this.mMappedCalendarElementId = newMappedCalendarElementId;
/*     */   }
/*     */ 
/*     */   public int getMappedCalendarElementId()
/*     */   {
/*  32 */     return this.mMappedCalendarElementId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mMappedCalendarElementId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     MappedCalendarElementPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof MappedCalendarElementCK)) {
/*  56 */       other = ((MappedCalendarElementCK)obj).getMappedCalendarElementPK();
/*     */     }
/*  58 */     else if ((obj instanceof MappedCalendarElementPK))
/*  59 */       other = (MappedCalendarElementPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mMappedCalendarElementId == other.mMappedCalendarElementId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" MappedCalendarElementId=");
/*  77 */     sb.append(this.mMappedCalendarElementId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mMappedCalendarElementId);
/*  89 */     return "MappedCalendarElementPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static MappedCalendarElementPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("MappedCalendarElementPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'MappedCalendarElementPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pMappedCalendarElementId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new MappedCalendarElementPK(pMappedCalendarElementId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.mapping.MappedCalendarElementPK
 * JD-Core Version:    0.6.0
 */