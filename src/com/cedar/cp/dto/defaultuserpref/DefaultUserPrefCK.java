/*     */ package com.cedar.cp.dto.defaultuserpref;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class DefaultUserPrefCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected DefaultUserPrefPK mDefaultUserPrefPK;
/*     */ 
/*     */   public DefaultUserPrefCK(DefaultUserPrefPK paramDefaultUserPrefPK)
/*     */   {
/*  26 */     this.mDefaultUserPrefPK = paramDefaultUserPrefPK;
/*     */   }
/*     */ 
/*     */   public DefaultUserPrefPK getDefaultUserPrefPK()
/*     */   {
/*  34 */     return this.mDefaultUserPrefPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mDefaultUserPrefPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mDefaultUserPrefPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof DefaultUserPrefPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof DefaultUserPrefCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     DefaultUserPrefCK other = (DefaultUserPrefCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mDefaultUserPrefPK.equals(other.mDefaultUserPrefPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mDefaultUserPrefPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("DefaultUserPrefCK|");
/*  92 */     sb.append(this.mDefaultUserPrefPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static DefaultUserPrefCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("DefaultUserPrefCK", token[(i++)]);
/* 101 */     checkExpected("DefaultUserPrefPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new DefaultUserPrefCK(DefaultUserPrefPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.defaultuserpref.DefaultUserPrefCK
 * JD-Core Version:    0.6.0
 */