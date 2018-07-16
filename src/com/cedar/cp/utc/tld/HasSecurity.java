package com.cedar.cp.utc.tld;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.logic.CompareTagBase;

import com.cedar.cp.utc.common.CPContext;

public class HasSecurity extends CompareTagBase {

	protected String mSecurityString;
	private HttpSession mSession = null;

	protected boolean condition() throws JspException {
		return this.checkSecurity();
	}

	public String getSecurityString() {
		return this.mSecurityString;
	}

	public void setSecurityString(String securityString) {
		this.mSecurityString = securityString;
	}

	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);
		this.setSession(pageContext.getSession());
	}

	private HttpSession getSession() {
		return this.mSession;
	}

	private void setSession(HttpSession session) {
		this.mSession = session;
	}

	private boolean checkSecurity() {
		boolean result = true;
		if (this.getSecurityString() != null && this.getSession() != null) {
			Object o = this.getSession().getAttribute("cpContext");
			if (o != null && o instanceof CPContext) {
				CPContext conn = (CPContext) o;
				result = conn.getUserContext().hasSecurity(this.getSecurityString());
			}
		}

		return result;
	}

	public void release() {
		super.release();
		this.mSecurityString = null;
	}
}
