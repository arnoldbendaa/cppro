/*     */ package com.cedar.cp.dto.model.cc;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CcDeploymentEntryPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mCcDeploymentEntryId;
/*     */ 
/*     */   public CcDeploymentEntryPK(int newCcDeploymentEntryId)
/*     */   {
/*  23 */     this.mCcDeploymentEntryId = newCcDeploymentEntryId;
/*     */   }
/*     */ 
/*     */   public int getCcDeploymentEntryId()
/*     */   {
/*  32 */     return this.mCcDeploymentEntryId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mCcDeploymentEntryId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     CcDeploymentEntryPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof CcDeploymentEntryCK)) {
/*  56 */       other = ((CcDeploymentEntryCK)obj).getCcDeploymentEntryPK();
/*     */     }
/*  58 */     else if ((obj instanceof CcDeploymentEntryPK))
/*  59 */       other = (CcDeploymentEntryPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mCcDeploymentEntryId == other.mCcDeploymentEntryId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" CcDeploymentEntryId=");
/*  77 */     sb.append(this.mCcDeploymentEntryId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mCcDeploymentEntryId);
/*  89 */     return "CcDeploymentEntryPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static CcDeploymentEntryPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("CcDeploymentEntryPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'CcDeploymentEntryPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pCcDeploymentEntryId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new CcDeploymentEntryPK(pCcDeploymentEntryId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.cc.CcDeploymentEntryPK
 * JD-Core Version:    0.6.0
 */