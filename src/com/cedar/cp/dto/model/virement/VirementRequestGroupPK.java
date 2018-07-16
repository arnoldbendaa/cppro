/*     */ package com.cedar.cp.dto.model.virement;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class VirementRequestGroupPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mRequestGroupId;
/*     */ 
/*     */   public VirementRequestGroupPK(int newRequestGroupId)
/*     */   {
/*  23 */     this.mRequestGroupId = newRequestGroupId;
/*     */   }
/*     */ 
/*     */   public int getRequestGroupId()
/*     */   {
/*  32 */     return this.mRequestGroupId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mRequestGroupId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     VirementRequestGroupPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof VirementRequestGroupCK)) {
/*  56 */       other = ((VirementRequestGroupCK)obj).getVirementRequestGroupPK();
/*     */     }
/*  58 */     else if ((obj instanceof VirementRequestGroupPK))
/*  59 */       other = (VirementRequestGroupPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mRequestGroupId == other.mRequestGroupId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" RequestGroupId=");
/*  77 */     sb.append(this.mRequestGroupId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mRequestGroupId);
/*  89 */     return "VirementRequestGroupPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static VirementRequestGroupPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("VirementRequestGroupPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'VirementRequestGroupPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pRequestGroupId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new VirementRequestGroupPK(pRequestGroupId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.virement.VirementRequestGroupPK
 * JD-Core Version:    0.6.0
 */