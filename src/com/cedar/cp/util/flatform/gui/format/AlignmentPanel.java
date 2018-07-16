// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui.format;

import com.cedar.cp.util.awt.TwoColumnLayout;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import com.cedar.cp.util.flatform.model.format.CellFormatEntry;
import java.util.Collection;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AlignmentPanel extends JPanel {

	private JComboBox mHorizontal;
	private JComboBox mVertical;

	public AlignmentPanel() {
		super(new TwoColumnLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.mHorizontal = new JComboBox(new String[] { "Left", "Center", "Right", "" });
		this.mVertical = new JComboBox(new String[] { "Top", "Middle", "Bottom", "" });
		this.add(new JLabel("Horizontal :"));
		this.add(this.mHorizontal);
		this.add(new JLabel("Vertical :"));
		this.add(this.mVertical);
	}

	public void populateFromCell(CellFormat format, Map<String, Collection<CellFormatEntry>> activeFormats) {
		if (CellFormatEntry.hasMultipleFormats(activeFormats, "horizontalAlignment")) {
			this.mHorizontal.setSelectedIndex(3);
		} else {
			switch (format.getHorizontalAlignment()) {
			case 0:
				this.mHorizontal.setSelectedIndex(1);
			case 1:
			case 3:
			default:
				break;
			case 2:
				this.mHorizontal.setSelectedIndex(0);
				break;
			case 4:
				this.mHorizontal.setSelectedIndex(2);
			}
		}

		if (CellFormatEntry.hasMultipleFormats(activeFormats, "verticalAlignment")) {
			this.mVertical.setSelectedIndex(3);
		} else {
			switch (format.getVerticalAlignment()) {
			case 0:
				this.mVertical.setSelectedIndex(1);
				break;
			case 1:
				this.mVertical.setSelectedIndex(0);
			case 2:
			default:
				break;
			case 3:
				this.mVertical.setSelectedIndex(2);
			}
		}

	}

	public void populateFromCell(CellFormat format) {
		switch (format.getHorizontalAlignment()) {
		case 2:
			mHorizontal.setSelectedIndex(0);
			break;
		case 0:
			mHorizontal.setSelectedIndex(1);
			break;
		case 4:
			mHorizontal.setSelectedIndex(2);
		case 1:
		case 3:
		}
		switch (format.getVerticalAlignment()) {
		case 1:
			mVertical.setSelectedIndex(0);
			break;
		case 0:
			mVertical.setSelectedIndex(1);
			break;
		case 3:
			mVertical.setSelectedIndex(2);
		case 2:
		}
	}

	public void updateCell(CellFormat format) {
		byte alignment;
		if (this.mHorizontal.getSelectedIndex() != 3) {
			alignment = 2;
			switch (this.mHorizontal.getSelectedIndex()) {
			case 0:
				alignment = 2;
				break;
			case 1:
				alignment = 0;
				break;
			case 2:
				alignment = 4;
			}

			format.setHorizontalAlignment(alignment);
		}

		if (this.mVertical.getSelectedIndex() != 3) {
			alignment = 1;
			switch (this.mVertical.getSelectedIndex()) {
			case 0:
				alignment = 1;
				break;
			case 1:
				alignment = 0;
				break;
			case 2:
				alignment = 3;
			}

			format.setVerticalAlignment(alignment);
		}

	}
}
