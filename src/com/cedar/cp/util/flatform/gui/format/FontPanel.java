// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui.format;

import com.cedar.cp.util.awt.QuantumCheckBox;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import com.cedar.cp.util.flatform.model.format.CellFormatEntry;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

public class FontPanel extends JPanel {

	private JList mFont;
	private JList mStyle;
	private JList mSize;
	private QuantumCheckBox mUnderline;
	private Font mNewFont = null;
	private static final String sBOLD_STYLE_TEXT = "Bold";
	private static final String sITALIC_STYLE_TEXT = "Italic";
	private static final String sREGULAR_STYLE_TEXT = "Regular";
	private static final String sBOLD_ITALIC_STYLE_TEXT = "Bold Italic";

	public FontPanel() {
		super(new GridLayout(1, 3, 5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JPanel fontPanel = new JPanel(new BorderLayout(5, 5));
		fontPanel.add(new JLabel("Font:"), "North");
		this.mFont = new JList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
		this.mFont.setSelectionMode(0);
		fontPanel.add(new JScrollPane(this.mFont), "Center");
		JPanel stylePanel = new JPanel(new BorderLayout(5, 5));
		stylePanel.add(new JLabel("Style:"), "North");
		this.mStyle = new JList(new String[] { "Regular", "Italic", "Bold", "Bold Italic" });
		this.mStyle.setSelectionMode(0);
		stylePanel.add(new JScrollPane(this.mStyle), "Center");
		JPanel underlinePanel = new JPanel(new FlowLayout(1));
		underlinePanel.setBorder(BorderFactory.createEtchedBorder());
		underlinePanel.add(this.mUnderline = new QuantumCheckBox("Underline"));
		stylePanel.add(underlinePanel, "South");
		JPanel sizePanel = new JPanel(new BorderLayout(5, 5));
		sizePanel.add(new JLabel("Size:"), "North");
		this.mSize = new JList(new Object[] { Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(12), Integer.valueOf(14), Integer.valueOf(16), Integer.valueOf(18), Integer.valueOf(20), Integer.valueOf(22), Integer.valueOf(24), Integer.valueOf(26), Integer.valueOf(28), Integer.valueOf(36),
				Integer.valueOf(48), Integer.valueOf(72) });
		this.mSize.setSelectionMode(0);
		sizePanel.add(new JScrollPane(this.mSize), "Center");
		this.add(fontPanel);
		this.add(stylePanel);
		this.add(sizePanel);
	}

	public void populateFromCell(CellFormat format, Map<String, Collection<CellFormatEntry>> activeFormats) {
		Font f = format.getFont();
		if (f == null) {
			f = UIManager.getFont("Table.font");
		}

		if (CellFormatEntry.hasMultipleFormats(activeFormats, "fontName")) {
			this.mFont.setSelectedIndex(-1);
		} else {
			this.mFont.setSelectedValue(format.getFontName(), true);
		}

		Boolean boldFontSelected = null;
		if (CellFormatEntry.hasMultipleFormats(activeFormats, "boldFont")) {
			boldFontSelected = null;
		} else {
			boldFontSelected = Boolean.valueOf(format.isBoldFont());
		}

		Boolean italicFontSelected = null;
		if (CellFormatEntry.hasMultipleFormats(activeFormats, "italicFont")) {
			italicFontSelected = null;
		} else {
			italicFontSelected = Boolean.valueOf(format.isItalicFont());
		}

		Boolean underlineFontSelected = null;
		if (CellFormatEntry.hasMultipleFormats(activeFormats, "underlineFont")) {
			underlineFontSelected = null;
		} else {
			underlineFontSelected = Boolean.valueOf(format.isUnderlineFont());
		}

		if (boldFontSelected != null && italicFontSelected != null) {
			if (boldFontSelected.booleanValue() && italicFontSelected.booleanValue()) {
				this.mStyle.setSelectedIndex(3);
			} else if (italicFontSelected.booleanValue()) {
				this.mStyle.setSelectedIndex(1);
			} else if (boldFontSelected.booleanValue()) {
				this.mStyle.setSelectedIndex(2);
			} else {
				this.mStyle.setSelectedIndex(0);
			}
		} else {
			this.mStyle.setSelectedIndex(-1);
		}

		if (CellFormatEntry.hasMultipleFormats(activeFormats, "fontSize")) {
			this.mSize.setSelectedIndex(-1);
		} else {
			this.mSize.setSelectedValue(Integer.valueOf(format.getFontSize()), true);
		}

		if (underlineFontSelected == null) {
			this.mUnderline.setAllowIntermediateState(true);
			this.mUnderline.setQuantumState(2);
		} else {
			this.mUnderline.setAllowIntermediateState(false);
			this.mUnderline.setSelected(underlineFontSelected.booleanValue());
		}

	}

	public void populateFromCell(CellFormat format) {
		Font f = format.getFont();

		if (f == null) {
			f = UIManager.getFont("Table.font");
		}
		mFont.setSelectedValue(format.getFontName(), true);

		Boolean boldFontSelected = Boolean.valueOf(format.isBoldFont());

		Boolean italicFontSelected = Boolean.valueOf(format.isItalicFont());

		Boolean underlineFontSelected = Boolean.valueOf(format.isUnderlineFont());

		if ((boldFontSelected.booleanValue()) && (italicFontSelected.booleanValue()))
			mStyle.setSelectedIndex(3);
		else if (italicFontSelected.booleanValue())
			mStyle.setSelectedIndex(1);
		else if (boldFontSelected.booleanValue())
			mStyle.setSelectedIndex(2);
		else {
			mStyle.setSelectedIndex(0);
		}
		mSize.setSelectedValue(Integer.valueOf(format.getFontSize()), true);

		if (underlineFontSelected == null) {
			mUnderline.setAllowIntermediateState(true);
			mUnderline.setQuantumState(2);
		} else {
			mUnderline.setAllowIntermediateState(false);
			mUnderline.setSelected(underlineFontSelected.booleanValue());
		}
	}

	public void updateCell(CellFormat format) {
		String fontStyle;
		if (this.mFont.getSelectedIndex() >= 0) {
			fontStyle = this.mFont.getSelectedValue() != null ? String.valueOf(this.mFont.getSelectedValue()) : null;
			if (fontStyle != null && fontStyle.trim().length() > 0) {
				format.setFontName(fontStyle);
			}
		}

		if (this.mSize.getSelectedIndex() >= 0) {
			fontStyle = this.mSize.getSelectedValue() != null ? String.valueOf(this.mSize.getSelectedValue()) : null;
			if (fontStyle != null && fontStyle.trim().length() > 0) {
				format.setFontSize(Integer.parseInt(fontStyle));
			}
		}

		if (this.mStyle.getSelectedIndex() >= 0) {
			fontStyle = this.mStyle.getSelectedValue() != null ? String.valueOf(this.mStyle.getSelectedValue()) : null;
			if (fontStyle != null && fontStyle.trim().length() > 0) {
				if (fontStyle.equalsIgnoreCase("Bold")) {
					format.setItalicFont(false);
					format.setBoldFont(true);
				} else if (fontStyle.equalsIgnoreCase("Italic")) {
					format.setItalicFont(true);
					format.setBoldFont(false);
				} else if (fontStyle.equalsIgnoreCase("Bold Italic")) {
					format.setItalicFont(true);
					format.setBoldFont(true);
				} else {
					format.setItalicFont(false);
					format.setBoldFont(false);
				}
			}
		}

		if (this.mUnderline.getQuantumState() != 2) {
			format.setUnderlineFont(this.mUnderline.isOn());
		}

	}
}
