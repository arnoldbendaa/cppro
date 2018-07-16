package com.cedar.cp.util.flatform.gui.format;

import com.cedar.cp.util.awt.OkCancelDialog;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import com.cedar.cp.util.flatform.model.format.CellFormatEntry;
import com.cedar.cp.util.flatform.model.format.ReportCellFormat;
import com.cedar.cp.util.xmlreport.Purpose;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.util.Collection;
import java.util.Map;
import javax.swing.JTabbedPane;

public class CellFormatDialog extends OkCancelDialog {
	private NumberPanel mNumberPanel;
	private AlignmentPanel mAlignmentPanel;
	private FontPanel mFontPanel;
	private BorderPanel mBorderPanel;
	private ColorsPanel mColorPanel;
	private ProtectionPanel mProtectionPanel;
	private Purpose mPurpose = Purpose.XML_FORM;

	public CellFormatDialog(Frame owner, String title, Purpose purpose) {
		super(owner, title);
		mPurpose = purpose;
		init();
	}

	public CellFormatDialog(Dialog owner, String title, Purpose purpose) {
		super(owner, title);
		mPurpose = purpose;
		init();
	}

	private boolean isAnalyzer() {
		return mPurpose == Purpose.ANALYSER;
	}

	protected void buildCenterPanel(Container center) {
		JTabbedPane tabs = new JTabbedPane();
		mNumberPanel = new NumberPanel();
		tabs.add("Number", mNumberPanel);
		mAlignmentPanel = new AlignmentPanel();
		tabs.add("Alignment", mAlignmentPanel);
		mFontPanel = new FontPanel();
		tabs.add("Font", mFontPanel);
		if (!isAnalyzer()) {
			mBorderPanel = new BorderPanel();
			tabs.add("Border", mBorderPanel);
			mColorPanel = new ColorsPanel();
		} else {
			mColorPanel = new ColorsPanel(true);
		}
		tabs.add("Colors", mColorPanel);
		if (!isAnalyzer()) {
			mProtectionPanel = new ProtectionPanel();
			tabs.add("Protection", mProtectionPanel);
		}
		center.add(tabs, "Center");
	}

	public void populateFromFormat(CellFormat format, Map<String, Collection<CellFormatEntry>> activeFormats) {
		mNumberPanel.populateFromCell(format, activeFormats);
		mAlignmentPanel.populateFromCell(format, activeFormats);
		mFontPanel.populateFromCell(format, activeFormats);
		mBorderPanel.populateFromCell(format, activeFormats);
		mColorPanel.populateFromCell(format, activeFormats);
		mProtectionPanel.populateFromCell(format, activeFormats);
	}

	public void populateFromReportFormat(ReportCellFormat format) {
		mNumberPanel.populateFromCell(format);
		mAlignmentPanel.populateFromCell(format);
		mFontPanel.populateFromCell(format);
		mColorPanel.populateFromReportCell(format);
	}

	public void updateCell(CellFormat format) {
		mNumberPanel.updateFormat(format);
		mAlignmentPanel.updateCell(format);
		mFontPanel.updateCell(format);
		mBorderPanel.updateCell(format);
		mColorPanel.updateCell(format);
		mProtectionPanel.updateCell(format);
	}

	public void updateReportCell(ReportCellFormat format) {
		mNumberPanel.updateFormat(format);
		mAlignmentPanel.updateCell(format);
		mFontPanel.updateCell(format);
		mColorPanel.updateReportCell(format);
	}

	protected boolean isOkAllowed() {
		return true;
	}
}