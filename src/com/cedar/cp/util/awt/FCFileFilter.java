// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.filechooser.FileFilter;

public class FCFileFilter extends FileFilter {

   private Hashtable mFilters;
   private String mDescription;
   private String mFullDescription;
   private boolean mUseExtensionsInDescription;


   public FCFileFilter() {
      this((String)null, (String)null);
   }

   public FCFileFilter(String extension) {
      this(extension, (String)null);
   }

   public FCFileFilter(String extension, String description) {
      this(new String[]{extension}, description);
   }

   public FCFileFilter(String[] filters) {
      this(filters, (String)null);
   }

   public FCFileFilter(String[] filters, String description) {
      this.mUseExtensionsInDescription = true;

      for(int i = 0; i < filters.length; ++i) {
         this.addExtension(filters[i]);
      }

      this.setDescription(description);
   }

   public boolean accept(File f) {
      if(f != null) {
         if(f.isDirectory()) {
            return true;
         }

         String extension = this.getExtension(f);
         if(extension != null && this.mFilters.get(this.getExtension(f)) != null) {
            return true;
         }
      }

      return false;
   }

   public String getExtension(File f) {
      if(f != null) {
         String filename = f.getName();
         int i = filename.lastIndexOf(46);
         if(i > 0 && i < filename.length() - 1) {
            return filename.substring(i + 1).toLowerCase();
         }
      }

      return null;
   }

   public void addExtension(String extension) {
      if(extension != null) {
         if(this.mFilters == null) {
            this.mFilters = new Hashtable();
         }

         this.mFilters.put(extension.toLowerCase(), this);
         this.mFullDescription = null;
      }
   }

   public String getDescription() {
      Object result = null;
      if(this.mFullDescription == null) {
         if(this.mDescription != null && !this.isExtensionListInDescription()) {
            this.mFullDescription = this.mDescription;
         } else {
            if(this.mDescription != null) {
               this.mFullDescription = this.mDescription;
            }

            this.mFullDescription = this.mFullDescription + " (";
            if(this.mFilters == null) {
               return " ";
            }

            Enumeration extensions = this.mFilters.keys();
            if(extensions != null) {
               for(this.mFullDescription = this.mFullDescription + "." + (String)extensions.nextElement(); extensions.hasMoreElements(); this.mFullDescription = this.mFullDescription + ", " + (String)extensions.nextElement()) {
                  ;
               }
            }

            this.mFullDescription = this.mFullDescription + ")";
         }
      }

      return this.mFullDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
      this.mFullDescription = null;
   }

   public void setExtensionListInDescription(boolean b) {
      this.mUseExtensionsInDescription = b;
      this.mFullDescription = null;
   }

   public boolean isExtensionListInDescription() {
      return this.mUseExtensionsInDescription;
   }
}
