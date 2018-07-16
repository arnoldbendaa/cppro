// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.job.xml;

import com.cedar.cp.utc.job.xml.JobProperty;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "jobProperties",
   propOrder = {"property"}
)
public class JobProperties {

   @XmlElement(
      required = true
   )
   protected List<JobProperty> property;


   public List<JobProperty> getProperty() {
      if(this.property == null) {
         this.property = new ArrayList();
      }

      return this.property;
   }
}
