// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 10:25:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lnf;

import java.util.Set;
import org.jvnet.substance.plugin.BaseSkinPlugin;
import org.jvnet.substance.skin.SkinInfo;

public class COASkinPlugin extends BaseSkinPlugin {

   private Set<SkinInfo> mSkins;


   public Set<SkinInfo> getSkins() {
      this.mSkins = super.getSkins();
      this.mSkins.add(new SkinInfo("COA", "com.coa.lnf.skin.COASkin"));
      return this.mSkins;
   }

   public String getDefaultSkinClassName() {
      return "com.coa.lnf.skin.COASkin";
   }
}
