/*     */ package com.cedar.cp.ejb.impl.cm.xml;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="cubeEvent")
/*     */ public class CubeEvent
/*     */ {
/*     */ 
/*     */   @XmlAttribute
/*     */   protected CubeEventAction action;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected CubeEventTypeType type;
/*     */ 
/*     */   @XmlAttribute(name="vis_id")
/*     */   protected String visId;
/*     */ 
/*     */   @XmlAttribute(name="roll_up_rules")
/*     */   protected String rollUpRules;
/*     */ 
/*     */   public CubeEventAction getAction()
/*     */   {
/*  59 */     return this.action;
/*     */   }
/*     */ 
/*     */   public void setAction(CubeEventAction value)
/*     */   {
/*  71 */     this.action = value;
/*     */   }
/*     */ 
/*     */   public CubeEventTypeType getType()
/*     */   {
/*  83 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(CubeEventTypeType value)
/*     */   {
/*  95 */     this.type = value;
/*     */   }
/*     */ 
/*     */   public String getVisId()
/*     */   {
/* 107 */     return this.visId;
/*     */   }
/*     */ 
/*     */   public void setVisId(String value)
/*     */   {
/* 119 */     this.visId = value;
/*     */   }
/*     */ 
/*     */   public String getRollUpRules()
/*     */   {
/* 131 */     return this.rollUpRules;
/*     */   }
/*     */ 
/*     */   public void setRollUpRules(String value)
/*     */   {
/* 143 */     this.rollUpRules = value;
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cm.xml.CubeEvent
 * JD-Core Version:    0.6.0
 */