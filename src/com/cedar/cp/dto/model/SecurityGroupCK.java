/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class SecurityGroupCK extends ModelCK
/*     */   implements Serializable
/*     */ {
/*     */   protected SecurityGroupPK mSecurityGroupPK;
/*     */ 
/*     */   public SecurityGroupCK(ModelPK paramModelPK, SecurityGroupPK paramSecurityGroupPK)
/*     */   {
/*  29 */     super(paramModelPK);
/*     */ 
/*  32 */     this.mSecurityGroupPK = paramSecurityGroupPK;
/*     */   }
/*     */ 
/*     */   public SecurityGroupPK getSecurityGroupPK()
/*     */   {
/*  40 */     return this.mSecurityGroupPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mSecurityGroupPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mSecurityGroupPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof SecurityGroupPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof SecurityGroupCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     SecurityGroupCK other = (SecurityGroupCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  75 */     eq = (eq) && (this.mSecurityGroupPK.equals(other.mSecurityGroupPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mSecurityGroupPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("SecurityGroupCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mSecurityGroupPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("SecurityGroupCK", token[(i++)]);
/* 111 */     checkExpected("ModelPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("SecurityGroupPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new SecurityGroupCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), SecurityGroupPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.SecurityGroupCK
 * JD-Core Version:    0.6.0
 */