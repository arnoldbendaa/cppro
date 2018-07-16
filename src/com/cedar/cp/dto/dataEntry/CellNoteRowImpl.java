// Decompiled by: Fernflower v0.8.6
// Date: 12.08.2012 13:05:48
// Copyright: 2008-2012, Stiver
// Home page: http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dataEntry;

import com.cedar.cp.api.dataEntry.CellNoteRow;
import java.io.Serializable;
import java.sql.Timestamp;

public class CellNoteRowImpl implements CellNoteRow, Serializable {

	private String mUserName;
	private Timestamp mCreated;
	private String mText;
	private Integer mAttachmentId;
	private String mCompany;
	
	public CellNoteRowImpl(String userName, Timestamp created, String text, Integer attachmentId) {
		this.mUserName = userName;
		this.mCreated = created;
		this.mText = text;
		this.mAttachmentId = attachmentId;
	}

	public String getUserName() {
		return this.mUserName;
	}

	public Timestamp getCreated() {
		return this.mCreated;
	}

	public String getText() {
		return this.mText;
	}

	public Integer getAttachmentId() {
		return this.mAttachmentId;
	}

	public String getCompany() {
		return mCompany;
	}

	public void setCompany(String mCompany) {
		this.mCompany = mCompany;
	}
}
