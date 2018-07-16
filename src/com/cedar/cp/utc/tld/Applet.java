// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:29:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.tld;

import java.io.IOException;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.bean.MessageTag;

/**
 * 
 * @author oracle
 *
 * Wersje jarow musza miec koniecznie postac xxx.xxx.xxx.xxx
 * Najlepiej, zeby to byly liczby.
 */
public class Applet extends MessageTag {

	private static final String LIB_DIR="jnlp/";
	
	static String[] sArchives = new String[] { 
			LIB_DIR+"webworkclient.jar",
			LIB_DIR+"commons-logging.jar", 
			LIB_DIR+"log4j.jar", 
			LIB_DIR+"bsh.jar",
			LIB_DIR+"poi.jar", 
			LIB_DIR+"xmlbeans.jar", 
			LIB_DIR+"poi-ooxml.jar",
			LIB_DIR+"httpclient.jar", 
			LIB_DIR+"commons-codec.jar",
			LIB_DIR+"jcommon.jar", 
			LIB_DIR+"jfreechart.jar",
			LIB_DIR+"commons-digester.jar", 
			LIB_DIR+"commons-collections.jar",
			LIB_DIR+"commons-beanutils.jar", 
			LIB_DIR+"antlr-runtime.jar",
			LIB_DIR+"antlr.jar", 
			LIB_DIR+"stringtemplate.jar", 
			LIB_DIR+"cp-coaInf-lnf.jar", 
			LIB_DIR+"jxcell.jar" 
			};
	
	static String[] sArchivesVersion = new String[] {
			"6.0.0.1",	// webworkclient
			"6.0.0.1", 	// commons-logging
			"1.2.15.0", // log4j
			"6.0.0.1", 	// bsh
			"3.5.0.0", 	// poi
			"2.3.0.0", 	// xmlbeans
			"3.5.0.0", 	// poi-ooxml
			"3.0.0.0", 	// httpclient
			"1.3.0.0", 	// commons-codec
			"1.0.13.0", // jcommon
			"1.0.10.0", // jfreechart
			"6.0.0.1", 	// commons-digester
			"6.0.0.1", 	// commons-collections
			"6.0.0.1", 	// commons-beanutils
			"3.2.0.0", 	// antlr-runtime
			"2.7.7.0",  // antlr
			"3.2.1.0",	// stringtemplate
			"1.0.0.0",	// cp-coaInf-lnf
			"3.0.0.12"	// jxcell
			};
	
	static String[] sCPArchives = new String[] { 
			LIB_DIR+"dataEntry.jar",
			LIB_DIR+"cp-util.jar", 
			LIB_DIR+"cp-awt.jar", 
			LIB_DIR+"cp-common.jar", 
			LIB_DIR+"cp-api.jar",
			LIB_DIR+"coaInf.jar",
			LIB_DIR+"cp-tc.jar"
			};
	private String mNameID;
	private String mHeight;
	private String mWidth;
	private String mRequestContext;
	private String mAppClass;
	private String mExtraParamNames;
	private String mExtraParamValues;
	private Map<String, String> mExtraParamMap;
	private String mLogLevel;
	private String mJREExecutable = "jre-6u16-windows-i586.exe";

	public String getNameID() {
		return this.mNameID;
	}

	public void setNameID(String nameID) {
		this.mNameID = nameID;
	}

	public String getHeight() {
		return this.mHeight;
	}

	public void setHeight(String height) {
		this.mHeight = height;
	}

	public String getWidth() {
		return this.mWidth;
	}

	public void setWidth(String width) {
		this.mWidth = width;
	}

	public String getRequestContext() {
		return this.mRequestContext;
	}

	public void setRequestContext(String requestContext) {
		this.mRequestContext = requestContext;
	}

	public String getAppClass() {
		return this.mAppClass;
	}

	public void setAppClass(String appClass) {
		this.mAppClass = appClass;
	}

	public String getExtraParamNames() {
		return this.mExtraParamNames;
	}

	public void setExtraParamNames(String extraParamNames) {
		this.mExtraParamNames = extraParamNames;
	}

	public String getExtraParamValues() {
		return this.mExtraParamValues;
	}

	public String getLogLevel() {
		return this.mLogLevel;
	}

	public void setLogLevel(String logLevel) {
		this.mLogLevel = logLevel;
	}

	public String getJREExecutable() {
		return this.mJREExecutable;
	}

	public void setJREExecutable(String JREExecutable) {
		this.mJREExecutable = JREExecutable;
	}

	public void setExtraParamValues(String extraParamValues) {
		this.mExtraParamMap = null;
		this.mExtraParamValues = extraParamValues;
	}

	public Map<String, String> getExtraParamMap() throws JspException {
		if (this.mExtraParamMap == null) {
			try {
				this.mExtraParamMap = new HashMap();
				if (this.getExtraParamNames() != null) {
					List<String> e = this.getStringAsList(this
							.getExtraParamNames());
					List<String> paramValue = this.getStringAsList(this
							.getExtraParamValues());
					int count = 0;
					Iterator<String> i$ = e.iterator();

					while (i$.hasNext()) {
						String key = (String) i$.next();
						this.mExtraParamMap.put(key, paramValue.get(count++));
					}
				}
			} catch (Exception var6) {
				throw new JspException(var6);
			}
		}

		return this.mExtraParamMap;
	}

	private List<String> getStringAsList(String s) {
		ArrayList result = new ArrayList();
		StringBuilder value = null;
		boolean flag = false;
		StringCharacterIterator charIter = new StringCharacterIterator(s);

		for (char c = charIter.first(); c != '\uffff'; c = charIter.next()) {
			if (c != 91 && c != 93) {
				if (c == 39 && !flag) {
					flag = true;
					value = new StringBuilder();
				} else if (c == 39 && flag) {
					if (44 == charIter.next()) {
						flag = false;
						result.add(value.toString());
					} else {
						charIter.previous();
					}
				} else if (value != null) {
					value.append(c);
				}
			}
		}

		if (value != null && value.length() > 0) {
			result.add(value.toString());
		}

		return result;
	}

	public String getCPArchiveVersion(int jvmVersion) throws JspException {
		StringBuilder value = new StringBuilder();
		String message = this.getMessage("cp.footer.version");
		
		// Removes revision number if any: "-r876"
		message=message.split("-")[0];
		
		switch (jvmVersion) {
		case 5:
			String[] bits = message.split("_");
			value.append(bits[bits.length - 1]);
			value.append(".0");
			break;
		default:
			value.append(message);
		}

//		return value.toString().toLowerCase();
		return value.toString();
	}

	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();

		try {
			out.write(this.getStartObjectTag());
			out.write(this.getParamValues());
			out.write(this.getParamArchiveValues());
			out.write(this.getParamExtraValues());
			out.write(this.getEndEmbedStartValues());
			out.write(this.getArchiveValues());
			out.write(this.getExtraValues());
			out.write(this.getEndObjectTag());
			return 0;
		} catch (IOException var3) {
			throw new JspException(var3);
		}
	}

	private String getStartObjectTag() {
		StringBuilder sb = new StringBuilder();
		sb.append("<object classid = \"clsid:8AD9C840-044E-11D1-B3E9-00805F499D93\" codebase = \"http://");
		sb.append(this.pageContext.getRequest().getServerName()).append(":");
		sb.append(this.pageContext.getRequest().getServerPort()).append(
				"/3rdParty/");
		sb.append(this.getJREExecutable());
		sb.append("#Version=6,0,0,16\" length=\"0\" ");
		sb.append("WIDTH = \"").append(this.getWidth())
				.append("\" HEIGHT = \"").append(this.getHeight())
				.append("\" id=\"ie_").append(this.getNameID())
				.append("\" name=\"ie_").append(this.getNameID())
				.append("\" >");
		sb.append("\n");
		return sb.toString();
	}

	private String getParamValues() {
		StringBuilder sb = new StringBuilder();
		sb.append("\t<PARAM NAME = \"type\" VALUE = \"application/x-java-applet;version=1.6.0_16\" />\n");
		//sb.append("\t<PARAM NAME = \"java_arguments\" VALUE = \"-Xms2048m -Xmx2048m -XX:PermSize=2048m -XX:MaxPermSize=2048m\" />\n");
		//sb.append("\t<PARAM NAME= \"separate_jvm\" VALUE= \"true\" />\n");
		sb.append("\t<PARAM NAME= \"scriptable\" VALUE= \"true\" />\n");
		sb.append("\t<PARAM NAME= MAYSCRIPT VALUE= \"true\" />\n");
		sb.append("\t<PARAM NAME = CODE VALUE = \"").append(this.getAppClass())
				.append("\" />\n");
		sb.append("\t<PARAM NAME = CODEBASE VALUE = \"")
				.append(this.getRequestContext()).append("\" />\n");
		sb.append("\t<PARAM NAME = context VALUE = \"")
				.append(this.getRequestContext()).append("\" />\n");
		sb.append("\t<PARAM NAME = logLevel VALUE = \"")
				.append(this.getLogLevel()).append("\" />\n");
		return sb.toString();
	}

	private String getParamArchiveValues() throws JspException {
		String cpVersion=this.getCPArchiveVersion(6);
		
		StringBuilder sb = new StringBuilder();
		sb.append("\t<PARAM NAME = cache_archive VALUE = \"");

		int key;
		for (key = 0; key < sArchives.length; ++key) {
			if (key != 0) {
				sb.append(", ");
			}

			sb.append(sArchives[key]);
		}

		String[] var8 = sCPArchives;
		int count = var8.length;

		int arr$;
		for (arr$ = 0; arr$ < count; ++arr$) {
			String len$ = var8[arr$];
			sb.append(", ").append(len$);
		}

		sb.append("\" />\n");
		sb.append("\t<PARAM NAME = cache_version VALUE = \"");

		for (key = 0; key < sArchives.length; ++key) {
			if (key != 0) {
				sb.append(", ");
			}

			sb.append(sArchivesVersion[key]);
		}

		var8 = sCPArchives;
		count = var8.length;

		for (arr$ = 0; arr$ < count; ++arr$) {
			String var10000 = var8[arr$];
			sb.append(", ").append(cpVersion);
		}

		sb.append("\" />\n");
		String var10 = "archive_";
		count = 0;
		String[] var12 = sArchives;
		int var11 = var12.length;

		int i$;
		String s;
		StringBuilder var9;
		for (i$ = 0; i$ < var11; ++i$) {
			s = var12[i$];
			var9 = sb.append("\t<PARAM NAME = ").append(var10);
			++count;
			var9.append(count).append(" VALUE = \"");
			sb.append(s).append(", version=").append(sArchivesVersion[i$])
					.append("\" />\n");
		}

		var12 = sCPArchives;
		var11 = var12.length;

		for (i$ = 0; i$ < var11; ++i$) {
			s = var12[i$];
			var9 = sb.append("\t<PARAM NAME = ").append(var10);
			++count;
			var9.append(count).append(" VALUE = \"");
			sb.append(s).append(", preload, version=")
			//sb.append(s).append(", version=")
					.append(cpVersion).append("\" />\n");
		}

		return sb.toString();
	}

	private String getParamExtraValues() throws JspException {
		StringBuilder sb = new StringBuilder();
		Map m = this.getExtraParamMap();
		Iterator i$ = m.keySet().iterator();

		while (i$.hasNext()) {
			String key = (String) i$.next();
			sb.append("\t<PARAM NAME = \"").append(key).append("\" VALUE = \"")
					.append((String) m.get(key)).append("\" />\n");
		}

		return sb.toString();
	}

	private String getEndEmbedStartValues() {
		StringBuilder sb = new StringBuilder();
		sb.append("<comment>\n");
		sb.append("\t<embed \n");
		sb.append("\t\t type = \"application/x-java-applet;version=1.6\" \n");
		sb.append("\t\t CODE = \"").append(this.getAppClass()).append("\" \n");
		sb.append("\t\t JAVA_CODEBASE = \"").append(this.getRequestContext())
				.append("\" \n");
		sb.append("\t\t context = \"").append(this.getRequestContext())
				.append("\" \n");
		sb.append("\t\t MAYSCRIPT = \"true\"\n");
		sb.append("\t\t scriptable = \"true\"\n");
		sb.append("\t\t WIDTH = \"").append(this.getWidth()).append("\" \n");
		sb.append("\t\t HEIGHT = \"").append(this.getHeight()).append("\" \n");
		sb.append("\t\t NAME = \"ff_").append(this.getNameID()).append("\" \n");
		sb.append("\t\t ID = \"ff_").append(this.getNameID()).append("\" \n");
		sb.append("\t\t logLevel = \"").append(this.getLogLevel())
				.append("\" \n");
		return sb.toString();
	}

	private String getArchiveValues() throws JspException {
		String cpVersion=this.getCPArchiveVersion(6);
		
		StringBuilder sb = new StringBuilder();
		sb.append("\t\t cache_archive = \"");

		int key;
		for (key = 0; key < sArchives.length; ++key) {
			if (key != 0) {
				sb.append(", ");
			}

			sb.append(sArchives[key]);
		}

		String[] var8 = sCPArchives;
		int count = var8.length;

		int arr$;
		for (arr$ = 0; arr$ < count; ++arr$) {
			String len$ = var8[arr$];
			sb.append(", ").append(len$);
		}

		sb.append("\"\n");
		sb.append("\t\t cache_version = \"");

		for (key = 0; key < sArchives.length; ++key) {
			if (key != 0) {
				sb.append(", ");
			}

			sb.append(sArchivesVersion[key]);
		}

		var8 = sCPArchives;
		count = var8.length;

		for (arr$ = 0; arr$ < count; ++arr$) {
			String var10000 = var8[arr$];
			sb.append(", ").append(cpVersion);
		}

		sb.append("\"\n");
		String var10 = "archive_";
		count = 0;
		String[] var12 = sArchives;
		int var11 = var12.length;

		int i$;
		String s;
		StringBuilder var9;
		for (i$ = 0; i$ < var11; ++i$) {
			s = var12[i$];
			var9 = sb.append("\t\t ").append(var10);
			++count;
			var9.append(count).append(" = \"");
			sb.append(s).append(", version=").append(sArchivesVersion[i$])
					.append("\"\n");
		}

		var12 = sCPArchives;
		var11 = var12.length;

		for (i$ = 0; i$ < var11; ++i$) {
			s = var12[i$];
			var9 = sb.append("\t\t ").append(var10);
			++count;
			var9.append(count).append(" = \"");
			sb.append(s).append(", preload, version=")
			//sb.append(s).append(", version=")
					.append(cpVersion).append("\"\n");
		}

		return sb.toString();
	}

	private String getExtraValues() throws JspException {
		StringBuilder sb = new StringBuilder();
		Map m = this.getExtraParamMap();
		Iterator i$ = m.keySet().iterator();

		while (i$.hasNext()) {
			String key = (String) i$.next();
			sb.append("\t\t ").append(key).append(" = \"")
					.append((String) m.get(key)).append("\"\n");
		}

		return sb.toString();
	}

	private String getEndObjectTag() throws JspException {
		StringBuilder sb = new StringBuilder();
		sb.append("\t\t pluginspage = \"http://");
		sb.append(this.pageContext.getRequest().getServerName()).append(":")
				.append(this.pageContext.getRequest().getServerPort())
				.append("/3rdParty\">");
		sb.append("<noembed>").append(this.getMessage("cp.error.applet"))
				.append("</noembed>\n").append("\t</embed>\n")
				.append("</comment>\n").append("</object>\n");
		return sb.toString();
	}

	private String getMessage(String key) throws JspException {
		return TagUtils.getInstance().message(this.pageContext, this.bundle,
				this.localeKey, key, new Object[0]);
	}

}
