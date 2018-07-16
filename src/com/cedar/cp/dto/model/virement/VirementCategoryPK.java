/*     */ package com.cedar.cp.dto.model.virement;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class VirementCategoryPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mVirementCategoryId;
/*     */ 
/*     */   public VirementCategoryPK(int newVirementCategoryId)
/*     */   {
/*  23 */     this.mVirementCategoryId = newVirementCategoryId;
/*     */   }
/*     */ 
/*     */   public int getVirementCategoryId()
/*     */   {
/*  32 */     return this.mVirementCategoryId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mVirementCategoryId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     VirementCategoryPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof VirementCategoryCK)) {
/*  56 */       other = ((VirementCategoryCK)obj).getVirementCategoryPK();
/*     */     }
/*  58 */     else if ((obj instanceof VirementCategoryPK))
/*  59 */       other = (VirementCategoryPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mVirementCategoryId == other.mVirementCategoryId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" VirementCategoryId=");
/*  77 */     sb.append(this.mVirementCategoryId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mVirementCategoryId);
/*  89 */     return "VirementCategoryPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static VirementCategoryPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("VirementCategoryPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'VirementCategoryPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pVirementCategoryId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new VirementCategoryPK(pVirementCategoryId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.virement.VirementCategoryPK
 * JD-Core Version:    0.6.0
 */