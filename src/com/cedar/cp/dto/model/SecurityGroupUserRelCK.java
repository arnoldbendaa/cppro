/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class SecurityGroupUserRelCK extends SecurityGroupCK
/*     */   implements Serializable
/*     */ {
/*     */   protected SecurityGroupUserRelPK mSecurityGroupUserRelPK;
/*     */ 
/*     */   public SecurityGroupUserRelCK(ModelPK paramModelPK, SecurityGroupPK paramSecurityGroupPK, SecurityGroupUserRelPK paramSecurityGroupUserRelPK)
/*     */   {
/*  31 */     super(paramModelPK, paramSecurityGroupPK);
/*     */ 
/*  35 */     this.mSecurityGroupUserRelPK = paramSecurityGroupUserRelPK;
/*     */   }
/*     */ 
/*     */   public SecurityGroupUserRelPK getSecurityGroupUserRelPK()
/*     */   {
/*  43 */     return this.mSecurityGroupUserRelPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  51 */     return this.mSecurityGroupUserRelPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  59 */     return this.mSecurityGroupUserRelPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  68 */     if ((obj instanceof SecurityGroupUserRelPK)) {
/*  69 */       return obj.equals(this);
/*     */     }
/*  71 */     if (!(obj instanceof SecurityGroupUserRelCK)) {
/*  72 */       return false;
/*     */     }
/*  74 */     SecurityGroupUserRelCK other = (SecurityGroupUserRelCK)obj;
/*  75 */     boolean eq = true;
/*     */ 
/*  77 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  78 */     eq = (eq) && (this.mSecurityGroupPK.equals(other.mSecurityGroupPK));
/*  79 */     eq = (eq) && (this.mSecurityGroupUserRelPK.equals(other.mSecurityGroupUserRelPK));
/*     */ 
/*  81 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  89 */     StringBuffer sb = new StringBuffer();
/*  90 */     sb.append(super.toString());
/*  91 */     sb.append("[");
/*  92 */     sb.append(this.mSecurityGroupUserRelPK);
/*  93 */     sb.append("]");
/*  94 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 102 */     StringBuffer sb = new StringBuffer();
/* 103 */     sb.append("SecurityGroupUserRelCK|");
/* 104 */     sb.append(super.getPK().toTokens());
/* 105 */     sb.append('|');
/* 106 */     sb.append(this.mSecurityGroupUserRelPK.toTokens());
/* 107 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 112 */     String[] token = extKey.split("[|]");
/* 113 */     int i = 0;
/* 114 */     checkExpected("SecurityGroupUserRelCK", token[(i++)]);
/* 115 */     checkExpected("ModelPK", token[(i++)]);
/* 116 */     i++;
/* 117 */     checkExpected("SecurityGroupPK", token[(i++)]);
/* 118 */     i++;
/* 119 */     checkExpected("SecurityGroupUserRelPK", token[(i++)]);
/* 120 */     i = 1;
/* 121 */     return new SecurityGroupUserRelCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), SecurityGroupPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), SecurityGroupUserRelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 130 */     if (!expected.equals(found))
/* 131 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.SecurityGroupUserRelCK
 * JD-Core Version:    0.6.0
 */