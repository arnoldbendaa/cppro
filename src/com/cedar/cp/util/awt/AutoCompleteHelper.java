package com.cedar.cp.util.awt;

import com.cedar.cp.util.StringUtils;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;

public class AutoCompleteHelper {
	private Map<JTextComponent, String[]> mPossibilities;
	private List<JTextComponent> mComponents;
	private Map<JTextComponent, Boolean> mSuggestedMap;
	private String mLastCompletion;
	private int mLastPos;

	public AutoCompleteHelper() {
		mComponents = new ArrayList();
	}

	public void bindOptions(String[] options, List<JTextComponent> components) {
		for (JTextComponent component : components) {
			if (mSuggestedMap == null) {
				mSuggestedMap = new HashMap();
			}
			popPossibilities(options, component);

			mSuggestedMap.put(component, Boolean.valueOf(false));

			start(component);
		}
	}

	public void bindOptions(String[] options, JTextComponent component) {
		if (mSuggestedMap == null) {
			mSuggestedMap = new HashMap();
		}
		popPossibilities(options, component);

		if (!mSuggestedMap.containsKey(component)) {
			mSuggestedMap.put(component, Boolean.valueOf(false));
		}
		start(component);
	}

	private void popPossibilities(String[] options, JTextComponent component) {
		if (mPossibilities == null) {
			mPossibilities = new HashMap();
		}

		if (mPossibilities.containsKey(component)) {
			String[] existingoptions = (String[]) mPossibilities.get(component);
			String[] both = StringUtils.mungeArraysNoDups(new String[][] { existingoptions, options });
			mPossibilities.put(component, both);
		} else {
			mPossibilities.put(component, options);
		}
	}

	private void setSuggestion(JTextComponent comp, boolean value) {
		mSuggestedMap.put(comp, Boolean.valueOf(value));
	}

	private void start(final JTextComponent comp) {
		if (mComponents.contains(comp)) {
			return;
		}
		mComponents.add(comp);

		comp.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				InputMap im = comp.getInputMap();
				im.put(KeyStroke.getKeyStroke("ENTER"), "commit");
				im.put(KeyStroke.getKeyStroke("UP"), "prev");
				im.put(KeyStroke.getKeyStroke("DOWN"), "next");

				ActionMap am = comp.getActionMap();
				am.put("commit", new AutoCompleteHelper.CommitAction(comp));
				am.put("prev", new AutoCompleteHelper.PrevAction(comp));
				am.put("next", new AutoCompleteHelper.NextAction(comp));
			}

			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
			}
		});
		comp.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				if (((Boolean) mSuggestedMap.get(comp)).booleanValue()) {
					AutoCompleteHelper.this.setSuggestion(comp, false);
					return;
				}

				AutoCompleteHelper.this.processSuggsAgainstUserEntry(comp, AutoCompleteHelper.mNavType.NORMAL, false);
			}

			public void removeUpdate(DocumentEvent e) {
			}

			public void changedUpdate(DocumentEvent e) {
			}
		});
	}

	private void processSuggsAgainstUserEntry(JTextComponent comp, mNavType type, boolean repeated) {
		boolean potMAtch = false;

		int carat = type != mNavType.NORMAL ? comp.getCaretPosition() : comp.getCaretPosition() + 1;

		if (comp.getSelectionEnd() == carat) {
			carat = comp.getSelectionStart();
		}

		String userstring = comp.getText().substring(getBackToLastSpace(comp.getCaretPosition(), comp.getText()), carat);

		if (type != mNavType.BACKWARDS) {
			label316: for (int i = (!repeated) && (mLastPos < ((String[]) mPossibilities.get(comp)).length) ? mLastPos : 0; i < ((String[]) mPossibilities.get(comp)).length; i++) {
				String pos = ((String[]) mPossibilities.get(comp))[i];

				for (int n = 0; (n < pos.length()) && (n < userstring.length()); n++) {
					if ((type == mNavType.FORWARD) && (pos.equals(mLastCompletion))) {
						break label316;
					}
					Character possChar = Character.valueOf(pos.charAt(n));
					Character usrChar = Character.valueOf(userstring.charAt(n));

					if (usrChar.toString().equals(possChar.toString())) {
						potMAtch = true;
					} else {
						potMAtch = false;
						break label316;
					}

				}

				if ((potMAtch) && (userstring.length() < pos.length())) {
					mLastCompletion = pos;
					mLastPos = i;

					String tofillin = pos.substring(userstring.length(), pos.length());

					SwingUtilities.invokeLater(new CompletionTask(comp, tofillin, carat));
					setSuggestion(comp, true);
					break;
				}

			}

			if ((!potMAtch) && (!repeated)) {
				mLastPos = 0;
				processSuggsAgainstUserEntry(comp, type, true);
			}
		} else {
			label575: for (int i = (!repeated) && (mLastPos > 0) ? mLastPos - 1 : ((String[]) mPossibilities.get(comp)).length - 1; i >= 0; i--) {
				String pos = ((String[]) mPossibilities.get(comp))[i];

				for (int n = 0; (n < pos.length()) && (n < userstring.length()); n++) {
					if (pos.equals(mLastCompletion)) {
						break label575;
					}
					Character possChar = Character.valueOf(pos.charAt(n));
					Character usrChar = Character.valueOf(userstring.charAt(n));

					if (usrChar.toString().equals(possChar.toString())) {
						potMAtch = true;
					} else {
						potMAtch = false;
						break label575;
					}

				}

				if ((potMAtch) && (userstring.length() < pos.length())) {
					mLastCompletion = pos;
					mLastPos = i;

					String tofillin = pos.substring(userstring.length(), pos.length());

					SwingUtilities.invokeLater(new CompletionTask(comp, tofillin, carat));
					setSuggestion(comp, true);
					break;
				}

			}

			if ((!potMAtch) && (!repeated)) {
				mLastPos = (((String[]) mPossibilities.get(comp)).length - 1);
				processSuggsAgainstUserEntry(comp, type, true);
			}
		}
	}

	private static int getBackToLastSpace(int pos, String text) {
		if (pos > 0)
			pos -= 1;

		for (int i = pos > text.length() ? text.length() : pos; i >= 0; i--) {
			Character myCahr = Character.valueOf(text.charAt(i));
			if (Character.isWhitespace(myCahr.charValue())) {
				return i + 1;
			}
		}
		return 0;
	}

	private class CompletionTask implements Runnable {
		private String mCompletion;
		private int mPosition;
		private JTextComponent mComp;

		public CompletionTask(JTextComponent comp, String completion, int position) {
			mCompletion = completion;
			mPosition = position;
			mComp = comp;
		}

		public void run() {
			AutoCompleteHelper.this.setSuggestion(mComp, true);
			try {
				if (mComp.getSelectedText() != null) {
					mComp.getDocument().remove(mComp.getSelectionStart(), mComp.getSelectedText().length());
				}
				mComp.getDocument().insertString(mPosition, mCompletion, new SimpleAttributeSet());
			} catch (BadLocationException be) {
				be.printStackTrace();
				return;
			}

			mComp.setCaretPosition(mPosition + mCompletion.length());
			mComp.moveCaretPosition(mPosition);
		}
	}

	private class PrevAction extends AbstractAction {
		private JTextComponent mComp;

		public PrevAction(JTextComponent comp) {
			mComp = comp;
		}

		public void actionPerformed(ActionEvent ev) {
			if ((mComp.getSelectedText() != null) && (!mComp.getSelectedText().equals("")))
				AutoCompleteHelper.this.processSuggsAgainstUserEntry(mComp, AutoCompleteHelper.mNavType.BACKWARDS, false);
		}
	}

	private class NextAction extends AbstractAction {
		private JTextComponent mComp;

		public NextAction(JTextComponent comp) {
			mComp = comp;
		}

		public void actionPerformed(ActionEvent ev) {
			if ((mComp.getSelectedText() != null) && (!mComp.getSelectedText().equals("")))
				AutoCompleteHelper.this.processSuggsAgainstUserEntry(mComp, AutoCompleteHelper.mNavType.FORWARD, false);
		}
	}

	private class CommitAction extends AbstractAction {
		private JTextComponent mComp;

		public CommitAction(JTextComponent comp) {
			mComp = comp;
		}

		public void actionPerformed(ActionEvent ev) {
			AutoCompleteHelper.this.setSuggestion(mComp, true);
			int pos = mComp.getSelectionEnd();
			try {
				mComp.getDocument().insertString(pos, " ", new SimpleAttributeSet());
			} catch (BadLocationException be) {
				be.printStackTrace();
				return;
			}
			mComp.setCaretPosition(pos + 1);
			mLastPos = 0;
			mLastCompletion = null;
		}
	}

	private static enum mNavType {
		FORWARD, BACKWARDS, NORMAL;
	}
}