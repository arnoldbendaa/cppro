// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import com.cedar.cp.ejb.base.cube.Cell;

public class CellNote extends Cell {

   private String mUser;
   private String mTimestamp;
   private String mNote;
   private int mLinkId;
   private int mLinkType;
   private String mAction;
   private String mCompany;
   private int mDataEntryProfileId;

   public String getUserName() {
      return this.mUser;
   }

   public String getTimestamp() {
      return this.mTimestamp;
   }

   public String getNote() {
      return this.mNote;
   }

   public void setUserName(String user) {
      this.mUser = user;
   }

   public void setTimestamp(String ts) {
      this.mTimestamp = ts;
   }

   public void setNote(String note) {
      this.mNote = note;
   }

   public int getLinkId() {
      return this.mLinkId;
   }

   public void setLinkId(int linkId) {
      this.mLinkId = linkId;
   }

   public int getLinkType() {
      return this.mLinkType;
   }

   public void setLinkType(int linkType) {
      this.mLinkType = linkType;
   }

	public String getAction() {
		return mAction;
	}
	
	public void setAction(String mAction) {
		this.mAction = mAction;
	}

	public String getCompany() {
		return mCompany;
	}

	public void setCompany(String mCompany) {
		this.mCompany = mCompany;
	}

	public int getDataEntryProfileId() {
		return mDataEntryProfileId;
	}

	public void setDataEntryProfileId(int dataEntryProfileId) {
		this.mDataEntryProfileId = dataEntryProfileId;
	}
}
