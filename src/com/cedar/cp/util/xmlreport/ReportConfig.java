package com.cedar.cp.util.xmlreport;

import com.cedar.cp.util.XmlUtils;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

public class ReportConfig implements Serializable {
	private String mReportKey;
	private String mTitle;
	private String mModelVisId;
	private String mFinanceCubeVisId;
	private Layout mLayout;
	private Display mDisplay;
	private Format mFormat;
	private boolean mSuppressGroupings;
	private boolean mSuppressIndentation;
	private boolean mSuppressRowZeros;
	private boolean mSuppressColumnZeros;
	private boolean mShowReportTitle;
	private String mAggregationRule = "sum";

	public ReportConfig(String reportKey) {
		mReportKey = reportKey;
		mTitle = "Report Title";
	}

	public String getReportId() {
		return mReportKey;
	}

	public void setReportId(String reportId) {
		mReportKey = reportId;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public String getFinanceCubeVisId() {
		return mFinanceCubeVisId;
	}

	public String getModelVisId() {
		return mModelVisId;
	}

	public void setModelVisId(String modelVisId) {
		mModelVisId = modelVisId;
	}

	public void setFinanceCubeVisId(String financeCubeVisId) {
		mFinanceCubeVisId = financeCubeVisId;
	}

	public Layout getLayout() {
		return mLayout;
	}

	public void setLayout(Layout layout) {
		mLayout = layout;
	}

	public Layout clearLayout() {
		mLayout = new Layout();
		return mLayout;
	}

	public Display getDisplay() {
		return mDisplay;
	}

	public void setDisplay(Display display) {
		mDisplay = display;
	}

	public Display clearDisplay() {
		mDisplay = new Display();
		return mDisplay;
	}

	public Format getFormat() {
		return mFormat;
	}

	public void setFormat(Format format) {
		mFormat = format;
	}

	public Format clearFormat() {
		mFormat = new Format();
		return mFormat;
	}

	public boolean isSuppressGroupings() {
		return mSuppressGroupings;
	}

	public void setSuppressGroupings(boolean suppressGroupings) {
		mSuppressGroupings = suppressGroupings;
	}

	public boolean isSuppressIndentation() {
		return mSuppressIndentation;
	}

	public void setSuppressIndentation(boolean suppressIndentation) {
		mSuppressIndentation = suppressIndentation;
	}

	public boolean isSuppressRowZeros() {
		return mSuppressRowZeros;
	}

	public void setSuppressRowZeros(boolean suppressRowZeros) {
		mSuppressRowZeros = suppressRowZeros;
	}

	public boolean isSuppressColumnZeros() {
		return mSuppressColumnZeros;
	}

	public void setSuppressColumnZeros(boolean suppressColumnZeros) {
		mSuppressColumnZeros = suppressColumnZeros;
	}

	public boolean isShowReportTitle() {
		return mShowReportTitle;
	}

	public void setShowReportTitle(boolean showReportTitle) {
		mShowReportTitle = showReportTitle;
	}

	public String getAggregationRule() {
		return mAggregationRule;
	}

	public void setAggregationRule(String aggregationRule) {
		mAggregationRule = aggregationRule;
	}

	public void writeSchemaXml(Writer out, String schema) throws IOException {
		out.write("<cp-report " + schema + " title=\"");
		out.write(XmlUtils.escapeStringForXML(mTitle) + "\"");
		out.write(" modelVisId=\"" + XmlUtils.escapeStringForXML(mModelVisId) + "\"");
		out.write(" financeCubeVisId=\"" + XmlUtils.escapeStringForXML(mFinanceCubeVisId) + "\"");
		out.write(" suppressGroupings=\"" + mSuppressGroupings + "\"");
		out.write(" suppressIndentation=\"" + mSuppressIndentation + "\"");
		out.write(" suppressRowZeros=\"" + mSuppressRowZeros + "\"");
		out.write(" suppressColumnZeros=\"" + mSuppressColumnZeros + "\"");
		out.write(" showReportTitle=\"" + mShowReportTitle + "\"");
		out.write(" aggregationRule=\"" + mAggregationRule + "\"");
		out.write(">");
		if (mLayout != null)
			mLayout.writeXml(out);
		if (mDisplay != null)
			mDisplay.writeXml(out);
		if (mFormat != null)
			mFormat.writeXml(out);
		out.write("</cp-report>");
	}
}