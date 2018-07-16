/*     */ package com.cedar.cp.dto.model.globalmapping2;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MappedHierarchyPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mMappedHierarchyId;
/*     */ 
/*     */   public MappedHierarchyPK(int newMappedHierarchyId)
/*     */   {
/*  23 */     this.mMappedHierarchyId = newMappedHierarchyId;
/*     */   }
/*     */ 
/*     */   public int getMappedHierarchyId()
/*     */   {
/*  32 */     return this.mMappedHierarchyId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mMappedHierarchyId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     MappedHierarchyPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof MappedHierarchyCK)) {
/*  56 */       other = ((MappedHierarchyCK)obj).getMappedHierarchyPK();
/*     */     }
/*  58 */     else if ((obj instanceof MappedHierarchyPK))
/*  59 */       other = (MappedHierarchyPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mMappedHierarchyId == other.mMappedHierarchyId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" MappedHierarchyId=");
/*  77 */     sb.append(this.mMappedHierarchyId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mMappedHierarchyId);
/*  89 */     return "MappedHierarchyPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static MappedHierarchyPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("MappedHierarchyPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'MappedHierarchyPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pMappedHierarchyId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new MappedHierarchyPK(pMappedHierarchyId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.mapping.MappedHierarchyPK
 * JD-Core Version:    0.6.0
 */