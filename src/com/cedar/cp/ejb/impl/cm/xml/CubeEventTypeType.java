/*    */ package com.cedar.cp.ejb.impl.cm.xml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlEnumValue;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlType(name="cubeEventTypeType")
/*    */ @XmlEnum
/*    */ public enum CubeEventTypeType
/*    */ {
/* 35 */   DATA_TYPE("data-type"), 
/*    */ 
/* 37 */   IMP_EXP_MAPPING("imp-exp-mapping");
/*    */ 
/*    */   private final String value;
/*    */ 
/* 42 */   private CubeEventTypeType(String v) { this.value = v; }
/*    */ 
/*    */   public String value()
/*    */   {
/* 46 */     return this.value;
/*    */   }
/*    */ 
/*    */   public static CubeEventTypeType fromValue(String v) {
/* 50 */     for (CubeEventTypeType c : values()) {
/* 51 */       if (c.value.equals(v)) {
/* 52 */         return c;
/*    */       }
/*    */     }
/* 55 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cm.xml.CubeEventTypeType
 * JD-Core Version:    0.6.0
 */