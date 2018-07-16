/*     */ package com.cedar.cp.dto.extsys;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExtSysHierElemFeedCK extends ExtSysHierElementCK
/*     */   implements Serializable
/*     */ {
/*     */   protected ExtSysHierElemFeedPK mExtSysHierElemFeedPK;
/*     */ 
/*     */   public ExtSysHierElemFeedCK(ExternalSystemPK paramExternalSystemPK, ExtSysCompanyPK paramExtSysCompanyPK, ExtSysLedgerPK paramExtSysLedgerPK, ExtSysDimensionPK paramExtSysDimensionPK, ExtSysHierarchyPK paramExtSysHierarchyPK, ExtSysHierElementPK paramExtSysHierElementPK, ExtSysHierElemFeedPK paramExtSysHierElemFeedPK)
/*     */   {
/*  39 */     super(paramExternalSystemPK, paramExtSysCompanyPK, paramExtSysLedgerPK, paramExtSysDimensionPK, paramExtSysHierarchyPK, paramExtSysHierElementPK);
/*     */ 
/*  47 */     this.mExtSysHierElemFeedPK = paramExtSysHierElemFeedPK;
/*     */   }
/*     */ 
/*     */   public ExtSysHierElemFeedPK getExtSysHierElemFeedPK()
/*     */   {
/*  55 */     return this.mExtSysHierElemFeedPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  63 */     return this.mExtSysHierElemFeedPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  71 */     return this.mExtSysHierElemFeedPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  80 */     if ((obj instanceof ExtSysHierElemFeedPK)) {
/*  81 */       return obj.equals(this);
/*     */     }
/*  83 */     if (!(obj instanceof ExtSysHierElemFeedCK)) {
/*  84 */       return false;
/*     */     }
/*  86 */     ExtSysHierElemFeedCK other = (ExtSysHierElemFeedCK)obj;
/*  87 */     boolean eq = true;
/*     */ 
/*  89 */     eq = (eq) && (this.mExternalSystemPK.equals(other.mExternalSystemPK));
/*  90 */     eq = (eq) && (this.mExtSysCompanyPK.equals(other.mExtSysCompanyPK));
/*  91 */     eq = (eq) && (this.mExtSysLedgerPK.equals(other.mExtSysLedgerPK));
/*  92 */     eq = (eq) && (this.mExtSysDimensionPK.equals(other.mExtSysDimensionPK));
/*  93 */     eq = (eq) && (this.mExtSysHierarchyPK.equals(other.mExtSysHierarchyPK));
/*  94 */     eq = (eq) && (this.mExtSysHierElementPK.equals(other.mExtSysHierElementPK));
/*  95 */     eq = (eq) && (this.mExtSysHierElemFeedPK.equals(other.mExtSysHierElemFeedPK));
/*     */ 
/*  97 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 105 */     StringBuffer sb = new StringBuffer();
/* 106 */     sb.append(super.toString());
/* 107 */     sb.append("[");
/* 108 */     sb.append(this.mExtSysHierElemFeedPK);
/* 109 */     sb.append("]");
/* 110 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 118 */     StringBuffer sb = new StringBuffer();
/* 119 */     sb.append("ExtSysHierElemFeedCK|");
/* 120 */     sb.append(super.getPK().toTokens());
/* 121 */     sb.append('|');
/* 122 */     sb.append(this.mExtSysHierElemFeedPK.toTokens());
/* 123 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ExternalSystemCK getKeyFromTokens(String extKey)
/*     */   {
/* 128 */     String[] token = extKey.split("[|]");
/* 129 */     int i = 0;
/* 130 */     checkExpected("ExtSysHierElemFeedCK", token[(i++)]);
/* 131 */     checkExpected("ExternalSystemPK", token[(i++)]);
/* 132 */     i++;
/* 133 */     checkExpected("ExtSysCompanyPK", token[(i++)]);
/* 134 */     i++;
/* 135 */     checkExpected("ExtSysLedgerPK", token[(i++)]);
/* 136 */     i++;
/* 137 */     checkExpected("ExtSysDimensionPK", token[(i++)]);
/* 138 */     i++;
/* 139 */     checkExpected("ExtSysHierarchyPK", token[(i++)]);
/* 140 */     i++;
/* 141 */     checkExpected("ExtSysHierElementPK", token[(i++)]);
/* 142 */     i++;
/* 143 */     checkExpected("ExtSysHierElemFeedPK", token[(i++)]);
/* 144 */     i = 1;
/* 145 */     return new ExtSysHierElemFeedCK(ExternalSystemPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysCompanyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysLedgerPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysDimensionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysHierarchyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysHierElementPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ExtSysHierElemFeedPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 158 */     if (!expected.equals(found))
/* 159 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extsys.ExtSysHierElemFeedCK
 * JD-Core Version:    0.6.0
 */