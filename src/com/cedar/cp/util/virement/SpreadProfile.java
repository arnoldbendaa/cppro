// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:29
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.virement;

import com.cedar.cp.util.virement.SpreadProfileLine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpreadProfile {

   public static final int PROFILE_ES_ID = 1;
   public static final int PROFILE_445_ID = 2;
   public static final int PROFILE_HYIA_ID = 3;
   public static final int PROFILE_QIAD = 4;
   public static final int PROFILE_QIAR = 5;
   public static final int PROFILE_FIRST_PERIOD = 6;
   public static final int PROFILE_LAST_PERIOD = 7;
   public static final int MAX_PROFILE_NUMBER = 7;
   private static Map sProfiles = new HashMap();
   private int mId;
   private String mName;
   private List mRows = new ArrayList();


   public SpreadProfile(int id, String name) {
      this.mId = id;
      this.mName = name;
   }

   public int getId() {
      return this.mId;
   }

   public void setId(int id) {
      this.mId = id;
   }

   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public List getRows() {
      return this.mRows;
   }

   public void setRows(List rows) {
      this.mRows = rows;
   }

   public static String getProfileVisId(int id) {
      return id >= 1 && id <= 7?getProfile(id).getName():"Custom";
   }

   public static SpreadProfile getProfile(int id) {
      return (SpreadProfile)sProfiles.get(new Integer(id));
   }

   static {
      SpreadProfile es = new SpreadProfile(1, "Even Split");

      for(int sp445 = 0; sp445 < 12; ++sp445) {
         es.getRows().add(new SpreadProfileLine(false, 1));
      }

      sProfiles.put(new Integer(es.getId()), es);
      SpreadProfile var8 = new SpreadProfile(2, "445");

      for(int hyia = 0; hyia < 12; hyia += 3) {
         var8.getRows().add(new SpreadProfileLine(false, 4));
         var8.getRows().add(new SpreadProfileLine(false, 4));
         var8.getRows().add(new SpreadProfileLine(false, 5));
      }

      sProfiles.put(new Integer(var8.getId()), var8);
      SpreadProfile var9 = new SpreadProfile(3, "Half year in advance");

      int qiar;
      for(int qiad = 0; qiad < 12; qiad += 6) {
         var9.getRows().add(new SpreadProfileLine(false, 1));

         for(qiar = 0; qiar < 5; ++qiar) {
            var9.getRows().add(new SpreadProfileLine(false, 0));
         }
      }

      sProfiles.put(new Integer(var9.getId()), var9);
      SpreadProfile var10 = new SpreadProfile(4, "Quarters in advance");

      int fp;
      for(qiar = 0; qiar < 12; qiar += 3) {
         var10.getRows().add(new SpreadProfileLine(false, 1));

         for(fp = 0; fp < 2; ++fp) {
            var10.getRows().add(new SpreadProfileLine(false, 0));
         }
      }

      sProfiles.put(new Integer(var10.getId()), var10);
      SpreadProfile var11 = new SpreadProfile(5, "Quarters in arrears");

      int lp;
      for(fp = 0; fp < 12; fp += 3) {
         for(lp = 0; lp < 2; ++lp) {
            var11.getRows().add(new SpreadProfileLine(false, 0));
         }

         var11.getRows().add(new SpreadProfileLine(false, 1));
      }

      sProfiles.put(new Integer(var11.getId()), var11);
      SpreadProfile var12 = new SpreadProfile(6, "First period");
      var12.getRows().add(new SpreadProfileLine(false, 1));

      for(lp = 0; lp < 11; ++lp) {
         var12.getRows().add(new SpreadProfileLine(false, 0));
      }

      sProfiles.put(new Integer(var12.getId()), var12);
      SpreadProfile var13 = new SpreadProfile(7, "Last period");

      for(int j = 0; j < 11; ++j) {
         var13.getRows().add(new SpreadProfileLine(false, 0));
      }

      var13.getRows().add(new SpreadProfileLine(false, 1));
      sProfiles.put(new Integer(var13.getId()), var13);
   }
}
