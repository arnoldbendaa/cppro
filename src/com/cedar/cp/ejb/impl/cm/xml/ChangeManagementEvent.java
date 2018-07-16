/*     */ package com.cedar.cp.ejb.impl.cm.xml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="changeManagementEvent", propOrder={"event", "cubeEvent"})
/*     */ public class ChangeManagementEvent
/*     */ {
/*     */   protected List<DimensionEvent> event;
/*     */ 
/*     */   @XmlElement(name="cube-event")
/*     */   protected List<CubeEvent> cubeEvent;
/*     */ 
/*     */   @XmlAttribute(required=true)
/*     */   protected ChangeManagementActionType action;
/*     */ 
/*     */   @XmlAttribute(required=true)
/*     */   protected ChangeManagementEventType type;
/*     */ 
/*     */   @XmlAttribute(name="vis_id", required=true)
/*     */   protected String visId;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected String model;
/*     */ 
/*     */   public List<DimensionEvent> getEvent()
/*     */   {
/*  86 */     if (this.event == null) {
/*  87 */       this.event = new ArrayList();
/*     */     }
/*  89 */     return this.event;
/*     */   }
/*     */ 
/*     */   public List<CubeEvent> getCubeEvent()
/*     */   {
/* 115 */     if (this.cubeEvent == null) {
/* 116 */       this.cubeEvent = new ArrayList();
/*     */     }
/* 118 */     return this.cubeEvent;
/*     */   }
/*     */ 
/*     */   public ChangeManagementActionType getAction()
/*     */   {
/* 130 */     return this.action;
/*     */   }
/*     */ 
/*     */   public void setAction(ChangeManagementActionType value)
/*     */   {
/* 142 */     this.action = value;
/*     */   }
/*     */ 
/*     */   public ChangeManagementEventType getType()
/*     */   {
/* 154 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(ChangeManagementEventType value)
/*     */   {
/* 166 */     this.type = value;
/*     */   }
/*     */ 
/*     */   public String getVisId()
/*     */   {
/* 178 */     return this.visId;
/*     */   }
/*     */ 
/*     */   public void setVisId(String value)
/*     */   {
/* 190 */     this.visId = value;
/*     */   }
/*     */ 
/*     */   public String getModel()
/*     */   {
/* 202 */     return this.model;
/*     */   }
/*     */ 
/*     */   public void setModel(String value)
/*     */   {
/* 214 */     this.model = value;
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cm.xml.ChangeManagementEvent
 * JD-Core Version:    0.6.0
 */