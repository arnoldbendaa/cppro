/*    */ package com.cedar.cp.ejb.impl.cm.xml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlType(name="crDrOverrideType")
/*    */ @XmlEnum
/*    */ public enum CrDrOverrideType
/*    */ {
/* 35 */   NO_OVERRIDE, 
/* 36 */   CREDIT, 
/* 37 */   DEBIT;
/*    */ 
/*    */   public String value() {
/* 40 */     return name();
/*    */   }
/*    */ 
/*    */   public static CrDrOverrideType fromValue(String v) {
/* 44 */     return valueOf(v);
/*    */   }
/*    */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cm.xml.CrDrOverrideType
 * JD-Core Version:    0.6.0
 */