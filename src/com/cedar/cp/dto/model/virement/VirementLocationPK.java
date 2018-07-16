/*     */ package com.cedar.cp.dto.model.virement;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class VirementLocationPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 148 */   private int mHashCode = -2147483648;
/*     */   int mVirementCategoryId;
/*     */   int mStructureId;
/*     */   int mStructureElementId;
/*     */ 
/*     */   public VirementLocationPK(int newVirementCategoryId, int newStructureId, int newStructureElementId)
/*     */   {
/*  25 */     this.mVirementCategoryId = newVirementCategoryId;
/*  26 */     this.mStructureId = newStructureId;
/*  27 */     this.mStructureElementId = newStructureElementId;
/*     */   }
/*     */ 
/*     */   public int getVirementCategoryId()
/*     */   {
/*  36 */     return this.mVirementCategoryId;
/*     */   }
/*     */ 
/*     */   public int getStructureId()
/*     */   {
/*  43 */     return this.mStructureId;
/*     */   }
/*     */ 
/*     */   public int getStructureElementId()
/*     */   {
/*  50 */     return this.mStructureElementId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  58 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  60 */       this.mHashCode += String.valueOf(this.mVirementCategoryId).hashCode();
/*  61 */       this.mHashCode += String.valueOf(this.mStructureId).hashCode();
/*  62 */       this.mHashCode += String.valueOf(this.mStructureElementId).hashCode();
/*     */     }
/*     */ 
/*  65 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  73 */     VirementLocationPK other = null;
/*     */ 
/*  75 */     if ((obj instanceof VirementLocationCK)) {
/*  76 */       other = ((VirementLocationCK)obj).getVirementLocationPK();
/*     */     }
/*  78 */     else if ((obj instanceof VirementLocationPK))
/*  79 */       other = (VirementLocationPK)obj;
/*     */     else {
/*  81 */       return false;
/*     */     }
/*  83 */     boolean eq = true;
/*     */ 
/*  85 */     eq = (eq) && (this.mVirementCategoryId == other.mVirementCategoryId);
/*  86 */     eq = (eq) && (this.mStructureId == other.mStructureId);
/*  87 */     eq = (eq) && (this.mStructureElementId == other.mStructureElementId);
/*     */ 
/*  89 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  97 */     StringBuffer sb = new StringBuffer();
/*  98 */     sb.append(" VirementCategoryId=");
/*  99 */     sb.append(this.mVirementCategoryId);
/* 100 */     sb.append(",StructureId=");
/* 101 */     sb.append(this.mStructureId);
/* 102 */     sb.append(",StructureElementId=");
/* 103 */     sb.append(this.mStructureElementId);
/* 104 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 112 */     StringBuffer sb = new StringBuffer();
/* 113 */     sb.append(" ");
/* 114 */     sb.append(this.mVirementCategoryId);
/* 115 */     sb.append(",");
/* 116 */     sb.append(this.mStructureId);
/* 117 */     sb.append(",");
/* 118 */     sb.append(this.mStructureElementId);
/* 119 */     return "VirementLocationPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static VirementLocationPK getKeyFromTokens(String extKey)
/*     */   {
/* 124 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 126 */     if (extValues.length != 2) {
/* 127 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 129 */     if (!extValues[0].equals("VirementLocationPK")) {
/* 130 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'VirementLocationPK|'");
/*     */     }
/* 132 */     extValues = extValues[1].split(",");
/*     */ 
/* 134 */     int i = 0;
/* 135 */     int pVirementCategoryId = new Integer(extValues[(i++)]).intValue();
/* 136 */     int pStructureId = new Integer(extValues[(i++)]).intValue();
/* 137 */     int pStructureElementId = new Integer(extValues[(i++)]).intValue();
/* 138 */     return new VirementLocationPK(pVirementCategoryId, pStructureId, pStructureElementId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.virement.VirementLocationPK
 * JD-Core Version:    0.6.0
 */