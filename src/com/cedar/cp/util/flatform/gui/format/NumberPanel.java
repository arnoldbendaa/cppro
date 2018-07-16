// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui.format;

import com.cedar.cp.util.flatform.gui.format.CurrencyDetailPanel;
import com.cedar.cp.util.flatform.gui.format.CustomDetailPanel;
import com.cedar.cp.util.flatform.gui.format.DateDetailPanel;
import com.cedar.cp.util.flatform.gui.format.GeneralDetailPanel;
import com.cedar.cp.util.flatform.gui.format.NumberDetailPanel;
import com.cedar.cp.util.flatform.gui.format.NumberPanel$1;
import com.cedar.cp.util.flatform.gui.format.PercentageDetailPanel;
import com.cedar.cp.util.flatform.gui.format.TextDetailPanel;
import com.cedar.cp.util.flatform.gui.format.TimeDetailPanel;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import com.cedar.cp.util.flatform.model.format.CellFormatEntry;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.Collection;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class NumberPanel extends JPanel {

	private String[] mCategories = new String[] { "General", "Number", "Currency", "Date", "Time", "Percentage", "Text", "Custom" };
	private String[] mCategoryDescriptions = new String[] { "General format cells have no specific number format.", "Number is used for general display of numbers. Currency offer specialized formatting for monetary values.", "Currency formats are used for general monetary values.",
			"Date formats are used for date and time values. Use Time format for time only parts.", "Time formats are used to display time parts only.", "Percentage formats multiply the cell by 100 and displays the result with a percent symbol.", "Text format cells treat the value in the cell as a text value.", "A custom format code" };
	private JLabel mFeedbackMessage;
	private JPanel mDetailPanel;
	private CardLayout mCardLayout;
	private JPanel mEmptyPanel = new JPanel();
	private JPanel[] mDetailPanels = new JPanel[] { new GeneralDetailPanel(), new NumberDetailPanel(), new CurrencyDetailPanel(), new DateDetailPanel(), new TimeDetailPanel(), new PercentageDetailPanel(), new TextDetailPanel(), new CustomDetailPanel() };
	private JList mTypes;

	public NumberPanel() {
		super(new BorderLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.mCardLayout = new CardLayout(5, 5);
		this.mDetailPanel = new JPanel(this.mCardLayout);
		this.add(this.mDetailPanel, "Center");
		this.mDetailPanel.add("Empty", this.mEmptyPanel);

		for (int i = 0; i < this.mCategories.length; ++i) {
			this.mDetailPanel.add(this.mCategories[i], this.mDetailPanels[i]);
		}

		this.mTypes = new JList(this.mCategories);
		this.add(new JScrollPane(this.mTypes), "West");
		this.mTypes.setSelectionMode(0);
		this.mTypes.getSelectionModel().addListSelectionListener(new NumberPanel$1(this));
		this.mFeedbackMessage = new JLabel("Please select a category");
		this.add(this.mFeedbackMessage, "South");
	}

	private void resetPanels() {
		((NumberDetailPanel) this.mDetailPanels[1]).resetPanel();
		((CurrencyDetailPanel) this.mDetailPanels[2]).resetPanel();
		((DateDetailPanel) this.mDetailPanels[3]).resetPanel();
		((TimeDetailPanel) this.mDetailPanels[4]).resetPanel();
		((PercentageDetailPanel) this.mDetailPanels[5]).resetPanel();
		((CustomDetailPanel) this.mDetailPanels[7]).resetPanel();
	}

	public void populateFromCell(CellFormat format, Map<String, Collection<CellFormatEntry>> activeFormats) {
		resetPanels();

		switch (format.getFormatType()) {
		case 0:
			break;
		case 1:
			NumberDetailPanel number = (NumberDetailPanel) mDetailPanels[1];

			if (CellFormatEntry.hasMultipleFormats(activeFormats, "decimalPlaces"))
				number.mDecPlaces.setValue(Integer.valueOf(-1));
			else {
				number.mDecPlaces.setValue(Integer.valueOf(format.getDecimalPlaces()));
			}
			if (CellFormatEntry.hasMultipleFormats(activeFormats, "showComma")) {
				number.mSeparator.setAllowIntermediateState(true);
				number.mSeparator.setQuantumState(2);
			} else {
				number.mSeparator.setAllowIntermediateState(false);
				number.mSeparator.setQuantumState(format.isShowComma() ? 1 : 0);
			}

			if (CellFormatEntry.hasMultipleFormats(activeFormats, "negativeColor"))
				number.mSetNegativeColor = false;
			else
				number.mSetNegativeColor = true;
			number.setNegativeColor(format.getNegativeColor());

			break;
		case 2:
			CurrencyDetailPanel curr = (CurrencyDetailPanel) mDetailPanels[2];

			if (CellFormatEntry.hasMultipleFormats(activeFormats, "decimalPlaces"))
				curr.mDecPlaces.setValue(Integer.valueOf(-1));
			else {
				curr.mDecPlaces.setValue(Integer.valueOf(format.getDecimalPlaces()));
			}

			if (CellFormatEntry.hasMultipleFormats(activeFormats, "currencySymbol")) {
				curr.mSymbol.setSelectedIndex(-1);
			} else {
				curr.mSymbol.setSelectedIndex(-1);
				curr.mSymbol.setSelectedItem(format.getCurrencySymbol());
			}

			if (CellFormatEntry.hasMultipleFormats(activeFormats, "negativeColor"))
				curr.mSetNegativeColor = false;
			else
				curr.mSetNegativeColor = true;
			curr.mTextColor = format.getNegativeColor();

			break;
		case 3:
			DateDetailPanel date = (DateDetailPanel) mDetailPanels[3];
			if (CellFormatEntry.hasMultipleFormats(activeFormats, "formatPattern"))
				date.selectPattern(null);
			else
				date.selectPattern(format.getFormatPattern());
			break;
		case 4:
			TimeDetailPanel time = (TimeDetailPanel) mDetailPanels[4];
			if (CellFormatEntry.hasMultipleFormats(activeFormats, "formatPattern"))
				time.selectPattern(null);
			else
				time.selectPattern(format.getFormatPattern());
			break;
		case 5:
			PercentageDetailPanel percent = (PercentageDetailPanel) mDetailPanels[5];

			if (CellFormatEntry.hasMultipleFormats(activeFormats, "decimalPlaces"))
				percent.mDecPlaces.setValue(Integer.valueOf(-1));
			else {
				percent.mDecPlaces.setValue(Integer.valueOf(format.getDecimalPlaces()));
			}

			if (CellFormatEntry.hasMultipleFormats(activeFormats, "negativeColor"))
				percent.mSetNegativeColor = false;
			else
				percent.mSetNegativeColor = true;
			percent.mTextColor = format.getNegativeColor();
			break;
		case 6:
			break;
		case 7:
			CustomDetailPanel custom = (CustomDetailPanel) mDetailPanels[7];
			if (CellFormatEntry.hasMultipleFormats(activeFormats, "formatPattern"))
				custom.selectPattern(null);
			else {
				custom.selectPattern(format.getFormatPattern());
			}
			if (CellFormatEntry.hasMultipleFormats(activeFormats, "negativeColor"))
				custom.mSetNegativeColor = false;
			else
				custom.mSetNegativeColor = true;
			custom.mTextColor = format.getNegativeColor();
		}

		mTypes.setSelectedIndex(format.getFormatType());
	}

	public void populateFromCell(CellFormat format)
	  {
	    resetPanels();

	    switch (format.getFormatType())
	    {
	    case 0:
	      break;
	    case 1:
	      NumberDetailPanel number = (NumberDetailPanel)mDetailPanels[1];

	      number.mDecPlaces.setValue(Integer.valueOf(format.getDecimalPlaces()));
	      number.mSeparator.setAllowIntermediateState(false);
	      number.mSeparator.setQuantumState(format.isShowComma() ? 1 : 0);
	      number.mSetNegativeColor = true;
	      number.setNegativeColor(format.getNegativeColor());
	      break;
	    case 2:
	      CurrencyDetailPanel curr = (CurrencyDetailPanel)mDetailPanels[2];
	      curr.mDecPlaces.setValue(Integer.valueOf(format.getDecimalPlaces()));
	      curr.mSymbol.setSelectedIndex(-1);
	      curr.mSymbol.setSelectedItem(format.getCurrencySymbol());
	      curr.mSetNegativeColor = true;
	      curr.mTextColor = format.getNegativeColor();
	      break;
	    case 3:
	      DateDetailPanel date = (DateDetailPanel)mDetailPanels[3];
	      date.selectPattern(format.getFormatPattern());
	      break;
	    case 4:
	      TimeDetailPanel time = (TimeDetailPanel)mDetailPanels[4];
	      time.selectPattern(format.getFormatPattern());
	      break;
	    case 5:
	      PercentageDetailPanel percent = (PercentageDetailPanel)mDetailPanels[5];
	      percent.mDecPlaces.setValue(Integer.valueOf(format.getDecimalPlaces()));
	      percent.mSetNegativeColor = true;
	      percent.mTextColor = format.getNegativeColor();
	      break;
	    case 6:
	      break;
	    case 7:
	      CustomDetailPanel custom = (CustomDetailPanel)mDetailPanels[7];
	      custom.selectPattern(format.getFormatPattern());
	      custom.mSetNegativeColor = true;
	      custom.mTextColor = format.getNegativeColor();
	    }

	    mTypes.setSelectedIndex(format.getFormatType());
	  }
	
	public void updateFormat(CellFormat format) {
		int type = this.mTypes.getSelectedIndex();
		if (type < 0) {
			type = 0;
		}

		format.setFormatType(type);
		switch (format.getFormatType()) {
		case 0:
		case 6:
		default:
			break;
		case 1:
			NumberDetailPanel number = (NumberDetailPanel) this.mDetailPanels[1];
			if (((Integer) number.mDecPlaces.getValue()).intValue() >= 0) {
				format.setDecimalPlaces(((Integer) number.mDecPlaces.getValue()).intValue());
			}

			if (number.mSeparator.getQuantumState() != 2) {
				format.setShowComma(number.mSeparator.isOn());
			}

			if (number.mSetNegativeColor) {
				format.setNegativeColor(number.mTextColor);
			}
			break;
		case 2:
			CurrencyDetailPanel curr = (CurrencyDetailPanel) this.mDetailPanels[2];
			if (((Integer) curr.mDecPlaces.getValue()).intValue() >= 0) {
				format.setDecimalPlaces(((Integer) curr.mDecPlaces.getValue()).intValue());
			}

			if (curr.mSymbol.getSelectedIndex() >= 0) {
				format.setCurrencySymbol(curr.mSymbol.getSelectedItem().toString());
			}

			if (curr.mSetNegativeColor) {
				format.setNegativeColor(curr.mTextColor);
			}
			break;
		case 3:
			DateDetailPanel date = (DateDetailPanel) this.mDetailPanels[3];
			if (date.getFormatPattern() != null) {
				format.setFormatPattern(date.getFormatPattern());
			}
			break;
		case 4:
			TimeDetailPanel time = (TimeDetailPanel) this.mDetailPanels[4];
			if (time.getFormatPattern() != null) {
				format.setFormatPattern(time.getFormatPattern());
			}
			break;
		case 5:
			PercentageDetailPanel percent = (PercentageDetailPanel) this.mDetailPanels[5];
			if (((Integer) percent.mDecPlaces.getValue()).intValue() >= 0) {
				format.setDecimalPlaces(((Integer) percent.mDecPlaces.getValue()).intValue());
			}

			if (percent.mSetNegativeColor) {
				format.setNegativeColor(percent.mTextColor);
			}
			break;
		case 7:
			CustomDetailPanel custom = (CustomDetailPanel) this.mDetailPanels[7];
			if (custom.getFormatPattern() != null) {
				format.setFormatPattern(custom.getFormatPattern());
			}

			if (custom.mSetNegativeColor) {
				format.setNegativeColor(custom.mTextColor);
			}
		}

	}

	// $FF: synthetic method
	static JList accessMethod000(NumberPanel x0) {
		return x0.mTypes;
	}

	// $FF: synthetic method
	static JLabel accessMethod100(NumberPanel x0) {
		return x0.mFeedbackMessage;
	}

	// $FF: synthetic method
	static JPanel accessMethod200(NumberPanel x0) {
		return x0.mDetailPanel;
	}

	// $FF: synthetic method
	static CardLayout accessMethod300(NumberPanel x0) {
		return x0.mCardLayout;
	}

	// $FF: synthetic method
	static String[] accessMethod400(NumberPanel x0) {
		return x0.mCategoryDescriptions;
	}

	// $FF: synthetic method
	static String[] accessMethod500(NumberPanel x0) {
		return x0.mCategories;
	}
}
