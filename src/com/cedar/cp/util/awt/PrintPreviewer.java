// Decompiled by: Fernflower v0.8.6
// Date: 11.08.2012 20:22:07
// Copyright: 2008-2012, Stiver
// Home page: http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.border.MatteBorder;

import com.cedar.cp.util.ImageLoader;

public class PrintPreviewer extends JFrame {
	protected int mPageWidth;
	protected int mPageHeight;
	protected Printable mTarget;
	protected JComboBox mScale;
	protected PreviewContainer mPreview;
	protected PageFormat mPageFormat;
	protected int mScaleFactor;

	public PrintPreviewer(Printable target, boolean directPrint)
	{
		super("Print Previewer");
		setDefaultCloseOperation(2);
		setSize(800, 600);
		mTarget = target;

		PrinterJob prnJob = PrinterJob.getPrinterJob();
		PageFormat userPageFormat = prnJob.defaultPage();
		userPageFormat.setOrientation(0);
		Paper paper = userPageFormat.getPaper();
		paper.setImageableArea(0.0D, 0.0D, 10000.0D, 10000.0D);
		userPageFormat.setPaper(paper);
		userPageFormat = prnJob.validatePage(userPageFormat);
		mPageFormat = prnJob.pageDialog(userPageFormat);
		if (userPageFormat == mPageFormat)
		{
			dispose();
			return;
		}

		if ((mPageFormat.getHeight() == 0.0D) || (mPageFormat.getWidth() == 0.0D))
		{
			System.err.println("Unable to determine default page size");
			dispose();
			return;
		}

		if (directPrint)
		{
			Thread runner = new Thread()
			{
				public void run()
				{
					PrintPreviewer.this.print();
				}
			};
			runner.start();
		}
		else
		{
			buildToolbar();
			buildPreview();
			setVisible(true);
		}
	}

	private void buildToolbar()
	{
		JToolBar tb = new JToolBar();
		tb.setRollover(true);
		AbstractAction print = new AbstractAction("Print", ImageLoader.getImageIcon("print.png"))
		{
			public void actionPerformed(ActionEvent e)
			{
				PrintPreviewer.this.print();
			}
		};
		tb.add(print);
		tb.addSeparator();

		AbstractAction zoomIn = new AbstractAction("Zoom in", ImageLoader.getImageIcon("zoomIn.png"))
		{
			public void actionPerformed(ActionEvent e)
			{
				PrintPreviewer.this.zoomIn();
			}
		};
		tb.add(zoomIn);
		AbstractAction zoomOut = new AbstractAction("Zoom out", ImageLoader.getImageIcon("zoomOut.png"))
		{
			public void actionPerformed(ActionEvent e)
			{
				PrintPreviewer.this.zoomOut();
			}
		};
		tb.add(zoomOut);

		String[] scales = { "10 %", "25 %", "50 %", "100 %" };
		mScale = new JComboBox(scales);
		ActionListener lst = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Thread runner = new Thread()
				{
					public void run()
					{
						String str = mScale.getSelectedItem().toString();
						if (str.endsWith("%")) {
							str = str.substring(0, str.length() - 1);
						}
						str = str.trim();
						try
						{
							mScaleFactor = Integer.parseInt(str);
						}
						catch (NumberFormatException ex)
						{
							mScaleFactor = 10;
						}
						if (mScaleFactor <= 0)
							mScaleFactor = 10;
						PrintPreviewer.this.scalePages();
					}
				};
				runner.start();
			}
		};
		mScale.addActionListener(lst);
		mScale.setMaximumSize(mScale.getPreferredSize());
		mScale.setEditable(true);

		tb.addSeparator();
		tb.add(mScale);

		getContentPane().add(tb, "North");
	}

	private void buildPreview()
	{
		mPreview = new PreviewContainer();

		mPageWidth = ((int) mPageFormat.getWidth());
		mPageHeight = ((int) mPageFormat.getHeight());

		mScaleFactor = 10;
		int w = mPageWidth * mScaleFactor / 100;
		int h = mPageHeight * mScaleFactor / 100;

		int pageIndex = 0;
		try
		{
			while (true)
			{
				BufferedImage img = new BufferedImage(mPageWidth, mPageHeight, 1);
				Graphics2D g = img.createGraphics();
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g.setColor(Color.white);
				g.fillRect(0, 0, mPageWidth, mPageHeight);

				if (mTarget.print(g, mPageFormat, pageIndex) != 0) {
					break;
				}
				PagePreview pp = new PagePreview(w, h, img);
				mPreview.add(pp);
				pageIndex++;
			}
		} catch (PrinterException e)
		{
			e.printStackTrace();
			System.err.println("Printing error: " + e.toString());
		}

		JScrollPane ps = new JScrollPane(mPreview);
		getContentPane().add(ps, "Center");
	}

	private void zoomIn()
	{
		mScaleFactor *= 2;
		scalePages();
	}

	private void zoomOut()
	{
		mScaleFactor /= 2;
		if (mScaleFactor <= 0)
			mScaleFactor = 10;
		scalePages();
	}

	private void scalePages()
	{
		int w = mPageWidth * mScaleFactor / 100;
		int h = mPageHeight * mScaleFactor / 100;
		Component[] comps = mPreview.getComponents();

		for (int k = 0; k < comps.length; k++)
		{
			if ((comps[k] instanceof PagePreview))
			{
				PagePreview pp = (PagePreview) comps[k];
				pp.setScaledSize(w, h);
			}
		}
		mPreview.doLayout();
		mPreview.getParent().getParent().validate();
	}

	private void print()
	{
		try
		{
			PrinterJob prnJob = PrinterJob.getPrinterJob();
			// if (!prnJob.printDialog())
			// return;
			prnJob.setPrintable(mTarget, mPageFormat);
			setCursor(Cursor.getPredefinedCursor(3));
			prnJob.print();
			setCursor(Cursor.getPredefinedCursor(0));
			dispose();
		} catch (PrinterException ex)
		{
			ex.printStackTrace();
			System.err.println("Printing error: " + ex.toString());
		}
	}

	class PagePreview extends JPanel
	{
		protected int m_w;
		protected int m_h;
		protected Image m_source;
		protected Image m_img;

		public PagePreview(int w, int h, Image source)
		{
			m_w = w;
			m_h = h;
			m_source = source;
			m_img = m_source.getScaledInstance(m_w, m_h, 4);
			m_img.flush();

			setBackground(Color.white);

			setBorder(new MatteBorder(1, 1, 2, 2, Color.black));
		}

		public void setScaledSize(int w, int h)
		{
			m_w = w;
			m_h = h;
			m_img = m_source.getScaledInstance(m_w, m_h, 4);

			repaint();
		}

		public Dimension getPreferredSize()
		{
			Insets ins = getInsets();
			return new Dimension(m_w + ins.left + ins.right, m_h + ins.top + ins.bottom);
		}

		public Dimension getMaximumSize()
		{
			return getPreferredSize();
		}

		public Dimension getMinimumSize()
		{
			return getPreferredSize();
		}

		public void paint(Graphics g)
		{
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(m_img, 0, 0, this);

			paintBorder(g);
		}
	}

	class PreviewContainer extends JPanel
	{
		protected int H_GAP = 16;
		protected int V_GAP = 10;

		public PreviewContainer()
		{
			setBackground(Color.lightGray);
		}

		public Dimension getPreferredSize()
		{
			int n = getComponentCount();
			if (n == 0) {
				return new Dimension(H_GAP, V_GAP);
			}
			Component comp = getComponent(0);
			Dimension dc = comp.getPreferredSize();

			int w = dc.width;
			int h = dc.height;

			Dimension dp = getParent().getSize();

			int nCol = Math.max((dp.width - H_GAP) / (w + H_GAP), 1);
			int nRow = n / nCol;
			if (nRow * nCol < n) {
				nRow++;
			}
			int ww = nCol * (w + H_GAP) + H_GAP;
			int hh = nRow * (h + V_GAP) + V_GAP;

			Insets ins = getInsets();

			return new Dimension(ww + ins.left + ins.right, hh + ins.top + ins.bottom);
		}

		public Dimension getMaximumSize()
		{
			return getPreferredSize();
		}

		public Dimension getMinimumSize()
		{
			return getPreferredSize();
		}

		public void doLayout()
		{
			Insets ins = getInsets();

			int x = ins.left + H_GAP;
			int y = ins.top + V_GAP;
			int n = getComponentCount();

			if (n == 0) {
				return;
			}
			Component comp = getComponent(0);

			Dimension dc = comp.getPreferredSize();

			int w = dc.width;
			int h = dc.height;

			Dimension dp = getParent().getSize();

			int nCol = Math.max((dp.width - H_GAP) / (w + H_GAP), 1);
			int nRow = n / nCol;
			if (nRow * nCol < n) {
				nRow++;
			}
			int index = 0;

			for (int k = 0; k < nRow; k++)
			{
				for (int m = 0; m < nCol; m++)
				{
					if (index >= n) {
						return;
					}
					comp = getComponent(index++);
					comp.setBounds(x, y, w, h);

					x += w + H_GAP;
				}

				y += h + V_GAP;
				x = ins.left + H_GAP;
			}
		}
	}
}