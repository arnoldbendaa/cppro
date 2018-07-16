/*     */ package com.cedar.cp.dto.extsys;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExtSysHierarchyCK extends ExtSysDimensionCK
/*     */   implements Serializable
/*     */ {
/*     */   protected ExtSysHierarchyPK mExtSysHierarchyPK;
/*     */ 
/*     */   public ExtSysHierarchyCK(ExternalSystemPK paramExternalSystemPK, ExtSysCompanyPK paramExtSysCompanyPK, ExtSysLedgerPK paramExtSysLedgerPK, ExtSysDimensionPK paramExtSysDimensionPK, ExtSysHierarchyPK paramExtSysHierarchyPK)
/*     */   {
/*  35 */     super(paramExternalSystemPK, paramExtSysCompanyPK, paramExtSysLedgerPK, paramExtSysDimensionPK);
/*     */ 
/*  41 */     this.mExtSysHierarchyPK = paramExtSysHierarchyPK;
/*     */   }
/*     */ 
/*     */   public ExtSysHierarchyPK getExtSysHierarchyPK()
/*     */   {
/*  49 */     return this.mExtSysHierarchyPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  57 */     return this.mExtSysHierarchyPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  65 */     return this.mExtSysHierarchyPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  74 */     if ((obj instanceof ExtSysHierarchyPK)) {
/*  75 */       return obj.equals(this);
/*     */     }
/*  77 */     if (!(obj instanceof ExtSysHierarchyCK)) {
/*  78 */       return false;
/*     */     }
/*  80 */     ExtSysHierarchyCK other = (ExtSysHierarchyCK)obj;
/*  81 */     boolean eq = true;
/*     */ 
/*  83 */     eq = (eq) && (this.mExternalSystemPK.equals(other.mExternalSystemPK));
/*  84 */     eq = (eq) && (this.mExtSysCompanyPK.equals(other.mExtSysCompanyPK));
/*  85 */     eq = (eq) && (this.mExtSysLedgerPK.equals(other.mExtSysLedgerPK));
/*  86 */     eq = (eq) && (this.mExtSysDimensionPK.equals(other.mExtSysDimensionPK));
/*  87 */     eq = (eq) && (this.mExtSysHierarchyPK.equals(other.mExtSysHierarchyPK));
/*     */ 
/*  89 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  97 */     StringBuffer sb = new StringBuffer();
/*  98 */     sb.append(super.toString());
/*  99 */     sb.append("[");
/* 100 */     sb.append(this.mExtSysHierarchyPK);
/* 101 */     sb.append("]");
/* 102 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 110 */     StringBuffer sb = new StringBuffer();
/* 111 */     sb.append("ExtSysHierarchyCK|");
/* 112 */     sb.append(super.getPK().toTokens());
/* 113 */     sb.append('|');
/* 114 */     sb.append(this.mExtSysHierarchyPK.toTokens());
/* 115 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ExternalSystemCK getKeyFromTokens(String extKey)
/*     */   {
/* 120 */     String[] token = extKey.split("[|]");
/* 121 */     int i = 0;
/* 122 */     checkExpected("ExtSysHierarchyCK", token[(i++)]);
/* 123 */     checkExpected("ExternalSystemPK", token[(i++)]);
/* 124 */     i++;
/* 125 */     checkExpected("ExtSysCompanyPK", token[(i++)]);
/* 126 */     i++;
/* 127 */     checkExpected("ExtSysLedgerPK", token[(i++)]);
/* 128 */     i++;
/* 129 */     checkExpected("ExtSysDimensionPK", token[(i++)]);
/* 130 */     i++;
/* 131 */     checkExpected("ExtSysHierarchyPK", token[(i++)]);
/* 132 */     i = 1;
/* 133 */     return new ExtSysHierarchyCK(ExternalSystemPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysCompanyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysLedgerPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysDimensionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysHierarchyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 144 */     if (!expected.equals(found))
/* 145 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extsys.ExtSysHierarchyCK
 * JD-Core Version:    0.6.0
 */