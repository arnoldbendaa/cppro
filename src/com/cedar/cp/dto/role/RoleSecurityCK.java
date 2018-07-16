/*     */ package com.cedar.cp.dto.role;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class RoleSecurityCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected RoleSecurityPK mRoleSecurityPK;
/*     */ 
/*     */   public RoleSecurityCK(RoleSecurityPK paramRoleSecurityPK)
/*     */   {
/*  26 */     this.mRoleSecurityPK = paramRoleSecurityPK;
/*     */   }
/*     */ 
/*     */   public RoleSecurityPK getRoleSecurityPK()
/*     */   {
/*  34 */     return this.mRoleSecurityPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mRoleSecurityPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mRoleSecurityPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof RoleSecurityPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof RoleSecurityCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     RoleSecurityCK other = (RoleSecurityCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mRoleSecurityPK.equals(other.mRoleSecurityPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mRoleSecurityPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("RoleSecurityCK|");
/*  92 */     sb.append(this.mRoleSecurityPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static RoleSecurityCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("RoleSecurityCK", token[(i++)]);
/* 101 */     checkExpected("RoleSecurityPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new RoleSecurityCK(RoleSecurityPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.role.RoleSecurityCK
 * JD-Core Version:    0.6.0
 */