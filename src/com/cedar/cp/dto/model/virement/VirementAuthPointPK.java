/*     */ package com.cedar.cp.dto.model.virement;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class VirementAuthPointPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mAuthPointId;
/*     */ 
/*     */   public VirementAuthPointPK(int newAuthPointId)
/*     */   {
/*  23 */     this.mAuthPointId = newAuthPointId;
/*     */   }
/*     */ 
/*     */   public int getAuthPointId()
/*     */   {
/*  32 */     return this.mAuthPointId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mAuthPointId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     VirementAuthPointPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof VirementAuthPointCK)) {
/*  56 */       other = ((VirementAuthPointCK)obj).getVirementAuthPointPK();
/*     */     }
/*  58 */     else if ((obj instanceof VirementAuthPointPK))
/*  59 */       other = (VirementAuthPointPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mAuthPointId == other.mAuthPointId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" AuthPointId=");
/*  77 */     sb.append(this.mAuthPointId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mAuthPointId);
/*  89 */     return "VirementAuthPointPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static VirementAuthPointPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("VirementAuthPointPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'VirementAuthPointPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pAuthPointId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new VirementAuthPointPK(pAuthPointId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.virement.VirementAuthPointPK
 * JD-Core Version:    0.6.0
 */