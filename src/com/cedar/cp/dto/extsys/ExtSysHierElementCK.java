/*     */ package com.cedar.cp.dto.extsys;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExtSysHierElementCK extends ExtSysHierarchyCK
/*     */   implements Serializable
/*     */ {
/*     */   protected ExtSysHierElementPK mExtSysHierElementPK;
/*     */ 
/*     */   public ExtSysHierElementCK(ExternalSystemPK paramExternalSystemPK, ExtSysCompanyPK paramExtSysCompanyPK, ExtSysLedgerPK paramExtSysLedgerPK, ExtSysDimensionPK paramExtSysDimensionPK, ExtSysHierarchyPK paramExtSysHierarchyPK, ExtSysHierElementPK paramExtSysHierElementPK)
/*     */   {
/*  37 */     super(paramExternalSystemPK, paramExtSysCompanyPK, paramExtSysLedgerPK, paramExtSysDimensionPK, paramExtSysHierarchyPK);
/*     */ 
/*  44 */     this.mExtSysHierElementPK = paramExtSysHierElementPK;
/*     */   }
/*     */ 
/*     */   public ExtSysHierElementPK getExtSysHierElementPK()
/*     */   {
/*  52 */     return this.mExtSysHierElementPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  60 */     return this.mExtSysHierElementPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  68 */     return this.mExtSysHierElementPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  77 */     if ((obj instanceof ExtSysHierElementPK)) {
/*  78 */       return obj.equals(this);
/*     */     }
/*  80 */     if (!(obj instanceof ExtSysHierElementCK)) {
/*  81 */       return false;
/*     */     }
/*  83 */     ExtSysHierElementCK other = (ExtSysHierElementCK)obj;
/*  84 */     boolean eq = true;
/*     */ 
/*  86 */     eq = (eq) && (this.mExternalSystemPK.equals(other.mExternalSystemPK));
/*  87 */     eq = (eq) && (this.mExtSysCompanyPK.equals(other.mExtSysCompanyPK));
/*  88 */     eq = (eq) && (this.mExtSysLedgerPK.equals(other.mExtSysLedgerPK));
/*  89 */     eq = (eq) && (this.mExtSysDimensionPK.equals(other.mExtSysDimensionPK));
/*  90 */     eq = (eq) && (this.mExtSysHierarchyPK.equals(other.mExtSysHierarchyPK));
/*  91 */     eq = (eq) && (this.mExtSysHierElementPK.equals(other.mExtSysHierElementPK));
/*     */ 
/*  93 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 101 */     StringBuffer sb = new StringBuffer();
/* 102 */     sb.append(super.toString());
/* 103 */     sb.append("[");
/* 104 */     sb.append(this.mExtSysHierElementPK);
/* 105 */     sb.append("]");
/* 106 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 114 */     StringBuffer sb = new StringBuffer();
/* 115 */     sb.append("ExtSysHierElementCK|");
/* 116 */     sb.append(super.getPK().toTokens());
/* 117 */     sb.append('|');
/* 118 */     sb.append(this.mExtSysHierElementPK.toTokens());
/* 119 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ExternalSystemCK getKeyFromTokens(String extKey)
/*     */   {
/* 124 */     String[] token = extKey.split("[|]");
/* 125 */     int i = 0;
/* 126 */     checkExpected("ExtSysHierElementCK", token[(i++)]);
/* 127 */     checkExpected("ExternalSystemPK", token[(i++)]);
/* 128 */     i++;
/* 129 */     checkExpected("ExtSysCompanyPK", token[(i++)]);
/* 130 */     i++;
/* 131 */     checkExpected("ExtSysLedgerPK", token[(i++)]);
/* 132 */     i++;
/* 133 */     checkExpected("ExtSysDimensionPK", token[(i++)]);
/* 134 */     i++;
/* 135 */     checkExpected("ExtSysHierarchyPK", token[(i++)]);
/* 136 */     i++;
/* 137 */     checkExpected("ExtSysHierElementPK", token[(i++)]);
/* 138 */     i = 1;
/* 139 */     return new ExtSysHierElementCK(ExternalSystemPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysCompanyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysLedgerPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysDimensionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysHierarchyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysHierElementPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 151 */     if (!expected.equals(found))
/* 152 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extsys.ExtSysHierElementCK
 * JD-Core Version:    0.6.0
 */