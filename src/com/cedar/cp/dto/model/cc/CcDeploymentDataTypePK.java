/*     */ package com.cedar.cp.dto.model.cc;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CcDeploymentDataTypePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mCcDeploymentDataTypeId;
/*     */ 
/*     */   public CcDeploymentDataTypePK(int newCcDeploymentDataTypeId)
/*     */   {
/*  23 */     this.mCcDeploymentDataTypeId = newCcDeploymentDataTypeId;
/*     */   }
/*     */ 
/*     */   public int getCcDeploymentDataTypeId()
/*     */   {
/*  32 */     return this.mCcDeploymentDataTypeId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mCcDeploymentDataTypeId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     CcDeploymentDataTypePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof CcDeploymentDataTypeCK)) {
/*  56 */       other = ((CcDeploymentDataTypeCK)obj).getCcDeploymentDataTypePK();
/*     */     }
/*  58 */     else if ((obj instanceof CcDeploymentDataTypePK))
/*  59 */       other = (CcDeploymentDataTypePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mCcDeploymentDataTypeId == other.mCcDeploymentDataTypeId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" CcDeploymentDataTypeId=");
/*  77 */     sb.append(this.mCcDeploymentDataTypeId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mCcDeploymentDataTypeId);
/*  89 */     return "CcDeploymentDataTypePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static CcDeploymentDataTypePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("CcDeploymentDataTypePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'CcDeploymentDataTypePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pCcDeploymentDataTypeId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new CcDeploymentDataTypePK(pCcDeploymentDataTypeId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.cc.CcDeploymentDataTypePK
 * JD-Core Version:    0.6.0
 */