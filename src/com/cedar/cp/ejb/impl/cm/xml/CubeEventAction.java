/*    */ package com.cedar.cp.ejb.impl.cm.xml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlEnumValue;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlType(name="cubeEventAction")
/*    */ @XmlEnum
/*    */ public enum CubeEventAction
/*    */ {
/* 36 */   INSERT("insert"), 
/*    */ 
/* 38 */   AMEND("amend"), 
/*    */ 
/* 40 */   DELETE("delete");
/*    */ 
/*    */   private final String value;
/*    */ 
/* 45 */   private CubeEventAction(String v) { this.value = v; }
/*    */ 
/*    */   public String value()
/*    */   {
/* 49 */     return this.value;
/*    */   }
/*    */ 
/*    */   public static CubeEventAction fromValue(String v) {
/* 53 */     for (CubeEventAction c : values()) {
/* 54 */       if (c.value.equals(v)) {
/* 55 */         return c;
/*    */       }
/*    */     }
/* 58 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cm.xml.CubeEventAction
 * JD-Core Version:    0.6.0
 */