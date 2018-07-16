/*     */ package com.cedar.cp.dto.role;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class RoleCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected RolePK mRolePK;
/*     */ 
/*     */   public RoleCK(RolePK paramRolePK)
/*     */   {
/*  26 */     this.mRolePK = paramRolePK;
/*     */   }
/*     */ 
/*     */   public RolePK getRolePK()
/*     */   {
/*  34 */     return this.mRolePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mRolePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mRolePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof RolePK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof RoleCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     RoleCK other = (RoleCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mRolePK.equals(other.mRolePK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mRolePK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("RoleCK|");
/*  92 */     sb.append(this.mRolePK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static RoleCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("RoleCK", token[(i++)]);
/* 101 */     checkExpected("RolePK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new RoleCK(RolePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.role.RoleCK
 * JD-Core Version:    0.6.0
 */