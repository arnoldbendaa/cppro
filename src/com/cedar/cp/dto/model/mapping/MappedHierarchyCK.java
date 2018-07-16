/*     */ package com.cedar.cp.dto.model.mapping;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MappedHierarchyCK extends MappedDimensionCK
/*     */   implements Serializable
/*     */ {
/*     */   protected MappedHierarchyPK mMappedHierarchyPK;
/*     */ 
/*     */   public MappedHierarchyCK(MappedModelPK paramMappedModelPK, MappedDimensionPK paramMappedDimensionPK, MappedHierarchyPK paramMappedHierarchyPK)
/*     */   {
/*  31 */     super(paramMappedModelPK, paramMappedDimensionPK);
/*     */ 
/*  35 */     this.mMappedHierarchyPK = paramMappedHierarchyPK;
/*     */   }
/*     */ 
/*     */   public MappedHierarchyPK getMappedHierarchyPK()
/*     */   {
/*  43 */     return this.mMappedHierarchyPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  51 */     return this.mMappedHierarchyPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  59 */     return this.mMappedHierarchyPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  68 */     if ((obj instanceof MappedHierarchyPK)) {
/*  69 */       return obj.equals(this);
/*     */     }
/*  71 */     if (!(obj instanceof MappedHierarchyCK)) {
/*  72 */       return false;
/*     */     }
/*  74 */     MappedHierarchyCK other = (MappedHierarchyCK)obj;
/*  75 */     boolean eq = true;
/*     */ 
/*  77 */     eq = (eq) && (this.mMappedModelPK.equals(other.mMappedModelPK));
/*  78 */     eq = (eq) && (this.mMappedDimensionPK.equals(other.mMappedDimensionPK));
/*  79 */     eq = (eq) && (this.mMappedHierarchyPK.equals(other.mMappedHierarchyPK));
/*     */ 
/*  81 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  89 */     StringBuffer sb = new StringBuffer();
/*  90 */     sb.append(super.toString());
/*  91 */     sb.append("[");
/*  92 */     sb.append(this.mMappedHierarchyPK);
/*  93 */     sb.append("]");
/*  94 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 102 */     StringBuffer sb = new StringBuffer();
/* 103 */     sb.append("MappedHierarchyCK|");
/* 104 */     sb.append(super.getPK().toTokens());
/* 105 */     sb.append('|');
/* 106 */     sb.append(this.mMappedHierarchyPK.toTokens());
/* 107 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static MappedModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 112 */     String[] token = extKey.split("[|]");
/* 113 */     int i = 0;
/* 114 */     checkExpected("MappedHierarchyCK", token[(i++)]);
/* 115 */     checkExpected("MappedModelPK", token[(i++)]);
/* 116 */     i++;
/* 117 */     checkExpected("MappedDimensionPK", token[(i++)]);
/* 118 */     i++;
/* 119 */     checkExpected("MappedHierarchyPK", token[(i++)]);
/* 120 */     i = 1;
/* 121 */     return new MappedHierarchyCK(MappedModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), MappedDimensionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), MappedHierarchyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 130 */     if (!expected.equals(found))
/* 131 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.mapping.MappedHierarchyCK
 * JD-Core Version:    0.6.0
 */