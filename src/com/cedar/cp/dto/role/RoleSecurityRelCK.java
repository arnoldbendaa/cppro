/*     */ package com.cedar.cp.dto.role;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class RoleSecurityRelCK extends RoleCK
/*     */   implements Serializable
/*     */ {
/*     */   protected RoleSecurityRelPK mRoleSecurityRelPK;
/*     */ 
/*     */   public RoleSecurityRelCK(RolePK paramRolePK, RoleSecurityRelPK paramRoleSecurityRelPK)
/*     */   {
/*  29 */     super(paramRolePK);
/*     */ 
/*  32 */     this.mRoleSecurityRelPK = paramRoleSecurityRelPK;
/*     */   }
/*     */ 
/*     */   public RoleSecurityRelPK getRoleSecurityRelPK()
/*     */   {
/*  40 */     return this.mRoleSecurityRelPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mRoleSecurityRelPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mRoleSecurityRelPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof RoleSecurityRelPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof RoleSecurityRelCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     RoleSecurityRelCK other = (RoleSecurityRelCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mRolePK.equals(other.mRolePK));
/*  75 */     eq = (eq) && (this.mRoleSecurityRelPK.equals(other.mRoleSecurityRelPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mRoleSecurityRelPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("RoleSecurityRelCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mRoleSecurityRelPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static RoleCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("RoleSecurityRelCK", token[(i++)]);
/* 111 */     checkExpected("RolePK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("RoleSecurityRelPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new RoleSecurityRelCK(RolePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), RoleSecurityRelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.role.RoleSecurityRelCK
 * JD-Core Version:    0.6.0
 */