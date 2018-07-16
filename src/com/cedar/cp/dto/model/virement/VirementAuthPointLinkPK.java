/*     */ package com.cedar.cp.dto.model.virement;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class VirementAuthPointLinkPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mAuthPointId;
/*     */   int mVirementLineId;
/*     */ 
/*     */   public VirementAuthPointLinkPK(int newAuthPointId, int newVirementLineId)
/*     */   {
/*  24 */     this.mAuthPointId = newAuthPointId;
/*  25 */     this.mVirementLineId = newVirementLineId;
/*     */   }
/*     */ 
/*     */   public int getAuthPointId()
/*     */   {
/*  34 */     return this.mAuthPointId;
/*     */   }
/*     */ 
/*     */   public int getVirementLineId()
/*     */   {
/*  41 */     return this.mVirementLineId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mAuthPointId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mVirementLineId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     VirementAuthPointLinkPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof VirementAuthPointLinkCK)) {
/*  66 */       other = ((VirementAuthPointLinkCK)obj).getVirementAuthPointLinkPK();
/*     */     }
/*  68 */     else if ((obj instanceof VirementAuthPointLinkPK))
/*  69 */       other = (VirementAuthPointLinkPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mAuthPointId == other.mAuthPointId);
/*  76 */     eq = (eq) && (this.mVirementLineId == other.mVirementLineId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" AuthPointId=");
/*  88 */     sb.append(this.mAuthPointId);
/*  89 */     sb.append(",VirementLineId=");
/*  90 */     sb.append(this.mVirementLineId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mAuthPointId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mVirementLineId);
/* 104 */     return "VirementAuthPointLinkPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static VirementAuthPointLinkPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("VirementAuthPointLinkPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'VirementAuthPointLinkPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pAuthPointId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pVirementLineId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new VirementAuthPointLinkPK(pAuthPointId, pVirementLineId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.virement.VirementAuthPointLinkPK
 * JD-Core Version:    0.6.0
 */