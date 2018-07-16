package com.cedar.cp.utc.common;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.RepaintManager;

public abstract class BasePrintable implements Printable {
	protected Graphics mTempGraphics;
	protected PageFormat mCurrentPageFormat;
	protected List mPages;
	protected Font mFont = new Font("SansSerif", 0, 5);
	protected Font mBoldFont = new Font("SansSerif", 1, 5);
	protected Font mHeadingFont = new Font("SansSerif", 1, 8);
	protected FontMetrics mFontMetrics;
	protected FontMetrics mBoldFontMetrics;
	protected FontMetrics mHeadingFontMetrics;
	protected int mHeadingHeight;
	protected int mFontBaseline;
	protected int mBoldFontBaseline;
	protected int mHeadingFontBaseline;
	protected int mFooterHeight;
	protected int mFontHeight;
	protected int mBoldFontHeight;
	protected int mHeadingFontHeight;
	protected int mTableHeight;
	protected int mTableRowsPerPage;
	protected int mHeaderHeight;
	protected String mPageHeading;
	protected String mPageFooter;
	protected static final int INDENT = 4;
	protected static final int MARGIN = 1;
	protected static final int LINE_SPACING = 0;
	protected static final int HALF_PADDING = 2;
	protected static final int PADDING = 4;
	protected static final Color LINE_COLOR = Color.gray;
	protected static final Color TEXT_COLOR = Color.black;
	protected static final Color CREDIT_COLOR = Color.black;
	protected static final Color FILL_COLOR = new Color(15658734);

	protected static final Stroke LINE_STROKE = new BasicStroke(0.5F);
	protected static final Stroke TEXT_STROKE = new BasicStroke(1.0F);

	public BasePrintable(String pageHeading) {
		mPageHeading = (pageHeading != null ? pageHeading : " ");
		mPageFooter = ("Printed : " + new Date());
	}

	protected void setPageHeading(String heading) {
		if (heading != null)
			mPageHeading = heading;
		else
			mPageHeading = " ";
	}

	protected void calculateSizes(PageFormat pf) {
		mFontMetrics = mTempGraphics.getFontMetrics(mFont);
		mBoldFontMetrics = mTempGraphics.getFontMetrics(mBoldFont);
		mHeadingFontMetrics = mTempGraphics.getFontMetrics(mHeadingFont);

		mFontHeight = (mFontMetrics.getMaxAscent() + mFontMetrics.getMaxDescent());
		mFontBaseline = mFontMetrics.getMaxDescent();
		mBoldFontHeight = (mBoldFontMetrics.getMaxAscent() + mBoldFontMetrics.getMaxDescent());
		mBoldFontBaseline = mBoldFontMetrics.getMaxDescent();
		mHeadingFontHeight = (mHeadingFontMetrics.getMaxAscent() + mHeadingFontMetrics.getMaxDescent());
		mHeadingFontBaseline = mHeadingFontMetrics.getMaxDescent();

		mHeadingHeight = (mHeadingFontHeight + 0);
		mFooterHeight = (mFontHeight + 0);
		mTableHeight = ((int) pf.getImageableHeight() - mHeadingHeight - mFooterHeight);
		mTableRowsPerPage = (mTableHeight / (mFontHeight + 0));
		mHeaderHeight = (mBoldFontHeight + 0);
	}

	protected void setPageFormat(Graphics2D g, PageFormat pageFormat) {
		if (mCurrentPageFormat != pageFormat) {
			mCurrentPageFormat = pageFormat;
			mTempGraphics = g;
			calculateSizes(pageFormat);
			mPages = repaginate(g, pageFormat);
		}
	}

	public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
		Graphics2D g2d = (Graphics2D) g;

		setPageFormat(g2d, pageFormat);
		if (pageIndex >= mPages.size()) {
			return 1;
		}
		renderPage(g2d, pageFormat, pageIndex);
		return 0;
	}

	protected abstract ArrayList repaginate(Graphics2D paramGraphics2D, PageFormat paramPageFormat);

	protected void renderPage(Graphics2D g, PageFormat pf, int idx) {
		int xo = (int) pf.getImageableX() + 1;
		int yo = (int) pf.getImageableY() + 1;
		int w = (int) pf.getImageableWidth();
		int h = (int) pf.getImageableHeight();

		Page p = (Page) mPages.get(idx);

		renderPageHeading(g, xo, yo, w, h);
		renderPageContent(g, xo, yo, w, h, p);
		renderFooter(g, xo, yo, w, h, idx + 1);
	}

	protected void renderPageHeading(Graphics2D g, int pageOriginX, int pageOriginY, int pageWidth, int pageHeight) {
		g.setFont(mHeadingFont);
		g.setPaint(TEXT_COLOR);
		g.setStroke(TEXT_STROKE);
		g.drawString(mPageHeading, pageOriginX, pageOriginY + mHeadingHeight - mHeadingFontBaseline - 0);
	}

	protected abstract void renderPageContent(Graphics2D paramGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Page paramPage);

	protected void renderFooter(Graphics2D g, int pageOriginX, int pageOriginY, int pageWidth, int pageHeight, int pageIndex) {
		g.setFont(mFont);
		g.setPaint(TEXT_COLOR);
		g.setStroke(TEXT_STROKE);
		g.drawString(mPageFooter, pageOriginX, pageOriginY + pageHeight - mFontBaseline - 0);
		centerAlign(g, "Page " + pageIndex, pageOriginX, pageOriginY + pageHeight - mFontBaseline - 0, pageWidth - 1);
		rightAlign(g, "Generated By ABS Ltd. Collaborative Planning", pageOriginX, pageOriginY + pageHeight - mFontBaseline - 0, pageWidth - 2);
	}

	protected int getVAlignTextBaseLine(int top, int bottom, int fontHeight, int fontBaseLine, int numLines) {
		int position = top + (1 + bottom - top) / 2;

		if (numLines == 1) {
			position += fontHeight / 2;
		} else if (numLines % 2 > 0) {
			double d = (numLines - 2.0D) / 2.0D;
			BigDecimal bd = new BigDecimal(d);
			bd = bd.setScale(1, 4);
			position = (int) (position - bd.doubleValue() * fontHeight);
		} else {
			position -= (numLines - 2) / 2 * fontHeight;
		}

		return position + 1 - fontBaseLine;
	}

	protected void leftAlign(Graphics2D g, String text, int x, int y, int w) {
		g.drawString(text, x + 2, y);
	}

	protected void centerAlign(Graphics2D g, String text, int x, int y, int w) {
		Rectangle2D rect = mFontMetrics.getStringBounds(text, g);
		g.drawString(text, x + 2 + w / 2 - (int) (rect.getWidth() / 2.0D), y);
	}

	protected void rightAlign(Graphics2D g, String text, int x, int y, int w) {
		Rectangle2D rect = mFontMetrics.getStringBounds(text, g);
		g.drawString(text, (int) (x + w - rect.getWidth()) - 1, y);
	}

	protected static void disableDoubleBuffering(Component c) {
		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(false);
	}

	protected static void enableDoubleBuffering(Component c) {
		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(true);
	}

	public static class TablePage extends BasePrintable.Page {
		protected String mName;
		protected int mRowStart;
		protected int mRowEnd;
		protected int mColStart;
		protected int mColEnd;

		public TablePage(int rowStart, int rowEnd, int colStart, int colEnd) {
			this("", rowStart, rowEnd, colStart, colEnd);
		}

		public TablePage(String name, int rowStart, int rowEnd, int colStart, int colEnd) {
			mName = name;
			mRowStart = rowStart;
			mRowEnd = rowEnd;
			mColStart = colStart;
			mColEnd = colEnd;
		}

		public String getName() {
			return mName;
		}

		public void setName(String name) {
			mName = name;
		}

		public int getRowStart() {
			return mRowStart;
		}

		public int getRowEnd() {
			return mRowEnd;
		}

		public int getColStart() {
			return mColStart;
		}

		public int getColEnd() {
			return mColEnd;
		}
	}

	public static class ChartPage extends BasePrintable.Page {
	}

	public static class ConfigPage extends BasePrintable.Page {
	}

	public static class Page {
	}
}