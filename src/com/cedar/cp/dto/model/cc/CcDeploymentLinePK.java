/*     */ package com.cedar.cp.dto.model.cc;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CcDeploymentLinePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mCcDeploymentLineId;
/*     */ 
/*     */   public CcDeploymentLinePK(int newCcDeploymentLineId)
/*     */   {
/*  23 */     this.mCcDeploymentLineId = newCcDeploymentLineId;
/*     */   }
/*     */ 
/*     */   public int getCcDeploymentLineId()
/*     */   {
/*  32 */     return this.mCcDeploymentLineId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mCcDeploymentLineId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     CcDeploymentLinePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof CcDeploymentLineCK)) {
/*  56 */       other = ((CcDeploymentLineCK)obj).getCcDeploymentLinePK();
/*     */     }
/*  58 */     else if ((obj instanceof CcDeploymentLinePK))
/*  59 */       other = (CcDeploymentLinePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mCcDeploymentLineId == other.mCcDeploymentLineId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" CcDeploymentLineId=");
/*  77 */     sb.append(this.mCcDeploymentLineId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mCcDeploymentLineId);
/*  89 */     return "CcDeploymentLinePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static CcDeploymentLinePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("CcDeploymentLinePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'CcDeploymentLinePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pCcDeploymentLineId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new CcDeploymentLinePK(pCcDeploymentLineId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.cc.CcDeploymentLinePK
 * JD-Core Version:    0.6.0
 */