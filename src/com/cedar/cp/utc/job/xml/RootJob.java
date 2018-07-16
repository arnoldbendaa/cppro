// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.job.xml;

import com.cedar.cp.utc.job.xml.JobProperties;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "rootJob",
   propOrder = {"name", "properties"}
)
@XmlRootElement
public class RootJob {

   @XmlElement(
      required = true
   )
   protected String name;
   protected JobProperties properties;


   public String getName() {
      return this.name;
   }

   public void setName(String value) {
      this.name = value;
   }

   public JobProperties getProperties() {
      return this.properties;
   }

   public void setProperties(JobProperties value) {
      this.properties = value;
   }
}
