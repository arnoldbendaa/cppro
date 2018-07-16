package com.cedar.cp.util.flatform.gui.format;

import com.cedar.cp.util.awt.TwoColumnLayout;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import com.cedar.cp.util.flatform.model.format.CellFormatEntry;
import com.cedar.cp.util.flatform.model.format.ReportCellFormat;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collection;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ColorsPanel extends JPanel {
	private JPanel mBackground;
	private JPanel mText;
	private JLabel mExample = new JLabel("Example");

	private Color mBackgroundColor = Color.white;
	private Color mTextColor = Color.black;
	private boolean mApplyBackgroundColor;
	private boolean mApplyTextColor;
	private boolean mUseDefaults;
	private JCheckBox mDefaultRows;
	private JButton mBackgroundButton;
	private boolean mIsForAnalysis;

	public ColorsPanel() {
		this(false);
	}

	public ColorsPanel(boolean isAnalyzer) {
		super(new TwoColumnLayout(5, 5));
		mIsForAnalysis = isAnalyzer;
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		mBackground = new JPanel();
		mBackground.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		mBackground.add(new JLabel(" "));

		mText = new JPanel();
		mText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		mText.add(mExample);

		mDefaultRows = new JCheckBox("Use defaults for Background");

		mDefaultRows.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					if (mBackgroundButton != null)
						mBackgroundButton.setEnabled(false);
					mUseDefaults = true;
				} else {
					mUseDefaults = false;
					if (mBackgroundButton != null)
						mBackgroundButton.setEnabled(true);
				}
				revalidate();
				repaint();
			}
		});
		mBackgroundButton = new JButton("Background");
		mBackgroundButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(ColorsPanel.this, "Background Color", mBackgroundColor);
				if (newColor != null) {
					mApplyBackgroundColor = true;
					mBackgroundColor = newColor;
					mBackground.setBackground(newColor);
					mText.setBackground(newColor);
				}
			}
		});
		JButton text = new JButton("Text");
		text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(ColorsPanel.this, "Text Color", mTextColor);
				if (newColor != null) {
					mApplyTextColor = true;
					mTextColor = newColor;
					mExample.setForeground(newColor);
				}
			}
		});
		if (mIsForAnalysis) {
			add(mDefaultRows);
			add(new JLabel(" "));
		}
		add(mBackgroundButton);
		add(mBackground);
		add(text);
		add(mText);
	}

	public void populateFromCell(CellFormat format, Map<String, Collection<CellFormatEntry>> activeFormats) {
		if (CellFormatEntry.hasMultipleFormats(activeFormats, "backgroundColor")) {
			mApplyBackgroundColor = false;
			mBackgroundColor = getBackground();
		} else {
			mApplyBackgroundColor = true;
			mBackgroundColor = format.getBackgroundColor();
		}

		if (CellFormatEntry.hasMultipleFormats(activeFormats, "textColor")) {
			mApplyTextColor = false;
			mTextColor = getForeground();
		} else {
			mApplyTextColor = true;
			mTextColor = format.getTextColor();
		}

		mBackground.setBackground(mBackgroundColor);
		mText.setBackground(mBackgroundColor);
		mExample.setForeground(mTextColor);
	}

	public void populateFromCell(CellFormat format) {
		mApplyBackgroundColor = true;
		mBackgroundColor = format.getBackgroundColor();
		mApplyTextColor = true;
		mTextColor = format.getTextColor();
		mBackground.setBackground(mBackgroundColor);
		mText.setBackground(mBackgroundColor);
		mExample.setForeground(mTextColor);
	}

	public void populateFromReportCell(ReportCellFormat format) {
		populateFromCell(format);
		mDefaultRows.setSelected(format.isDefaultRowFormatting());
	}

	public void updateCell(CellFormat format) {
		if (mApplyBackgroundColor)
			format.setBackgroundColor(mBackgroundColor);
		if (mApplyTextColor)
			format.setTextColor(mTextColor);
	}

	public void updateReportCell(ReportCellFormat format) {
		if (mApplyBackgroundColor)
			format.setBackgroundColor(mBackgroundColor);
		if (mApplyTextColor)
			format.setTextColor(mTextColor);
		format.setDefaultRowFormatting(mUseDefaults);
	}
}