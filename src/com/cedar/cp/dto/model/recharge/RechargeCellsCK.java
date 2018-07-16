/*     */ package com.cedar.cp.dto.model.recharge;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class RechargeCellsCK extends RechargeCK
/*     */   implements Serializable
/*     */ {
/*     */   protected RechargeCellsPK mRechargeCellsPK;
/*     */ 
/*     */   public RechargeCellsCK(ModelPK paramModelPK, RechargePK paramRechargePK, RechargeCellsPK paramRechargeCellsPK)
/*     */   {
/*  32 */     super(paramModelPK, paramRechargePK);
/*     */ 
/*  36 */     this.mRechargeCellsPK = paramRechargeCellsPK;
/*     */   }
/*     */ 
/*     */   public RechargeCellsPK getRechargeCellsPK()
/*     */   {
/*  44 */     return this.mRechargeCellsPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  52 */     return this.mRechargeCellsPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  60 */     return this.mRechargeCellsPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  69 */     if ((obj instanceof RechargeCellsPK)) {
/*  70 */       return obj.equals(this);
/*     */     }
/*  72 */     if (!(obj instanceof RechargeCellsCK)) {
/*  73 */       return false;
/*     */     }
/*  75 */     RechargeCellsCK other = (RechargeCellsCK)obj;
/*  76 */     boolean eq = true;
/*     */ 
/*  78 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  79 */     eq = (eq) && (this.mRechargePK.equals(other.mRechargePK));
/*  80 */     eq = (eq) && (this.mRechargeCellsPK.equals(other.mRechargeCellsPK));
/*     */ 
/*  82 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append(super.toString());
/*  92 */     sb.append("[");
/*  93 */     sb.append(this.mRechargeCellsPK);
/*  94 */     sb.append("]");
/*  95 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 103 */     StringBuffer sb = new StringBuffer();
/* 104 */     sb.append("RechargeCellsCK|");
/* 105 */     sb.append(super.getPK().toTokens());
/* 106 */     sb.append('|');
/* 107 */     sb.append(this.mRechargeCellsPK.toTokens());
/* 108 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 113 */     String[] token = extKey.split("[|]");
/* 114 */     int i = 0;
/* 115 */     checkExpected("RechargeCellsCK", token[(i++)]);
/* 116 */     checkExpected("ModelPK", token[(i++)]);
/* 117 */     i++;
/* 118 */     checkExpected("RechargePK", token[(i++)]);
/* 119 */     i++;
/* 120 */     checkExpected("RechargeCellsPK", token[(i++)]);
/* 121 */     i = 1;
/* 122 */     return new RechargeCellsCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), RechargePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), RechargeCellsPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 131 */     if (!expected.equals(found))
/* 132 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.recharge.RechargeCellsCK
 * JD-Core Version:    0.6.0
 */