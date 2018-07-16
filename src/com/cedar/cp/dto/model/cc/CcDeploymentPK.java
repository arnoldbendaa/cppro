/*     */ package com.cedar.cp.dto.model.cc;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CcDeploymentPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mCcDeploymentId;
/*     */ 
/*     */   public CcDeploymentPK(int newCcDeploymentId)
/*     */   {
/*  23 */     this.mCcDeploymentId = newCcDeploymentId;
/*     */   }
/*     */ 
/*     */   public int getCcDeploymentId()
/*     */   {
/*  32 */     return this.mCcDeploymentId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mCcDeploymentId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     CcDeploymentPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof CcDeploymentCK)) {
/*  56 */       other = ((CcDeploymentCK)obj).getCcDeploymentPK();
/*     */     }
/*  58 */     else if ((obj instanceof CcDeploymentPK))
/*  59 */       other = (CcDeploymentPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mCcDeploymentId == other.mCcDeploymentId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" CcDeploymentId=");
/*  77 */     sb.append(this.mCcDeploymentId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mCcDeploymentId);
/*  89 */     return "CcDeploymentPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static CcDeploymentPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("CcDeploymentPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'CcDeploymentPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pCcDeploymentId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new CcDeploymentPK(pCcDeploymentId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.cc.CcDeploymentPK
 * JD-Core Version:    0.6.0
 */