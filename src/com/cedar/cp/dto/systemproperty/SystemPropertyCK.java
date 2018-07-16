/*     */ package com.cedar.cp.dto.systemproperty;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class SystemPropertyCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected SystemPropertyPK mSystemPropertyPK;
/*     */ 
/*     */   public SystemPropertyCK(SystemPropertyPK paramSystemPropertyPK)
/*     */   {
/*  26 */     this.mSystemPropertyPK = paramSystemPropertyPK;
/*     */   }
/*     */ 
/*     */   public SystemPropertyPK getSystemPropertyPK()
/*     */   {
/*  34 */     return this.mSystemPropertyPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mSystemPropertyPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mSystemPropertyPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof SystemPropertyPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof SystemPropertyCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     SystemPropertyCK other = (SystemPropertyCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mSystemPropertyPK.equals(other.mSystemPropertyPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mSystemPropertyPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("SystemPropertyCK|");
/*  92 */     sb.append(this.mSystemPropertyPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static SystemPropertyCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("SystemPropertyCK", token[(i++)]);
/* 101 */     checkExpected("SystemPropertyPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new SystemPropertyCK(SystemPropertyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.systemproperty.SystemPropertyCK
 * JD-Core Version:    0.6.0
 */