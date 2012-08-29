package it.gas.altichierock.add;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class FloatDocument extends PlainDocument {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {
		if (str == null)
			return;
		StringBuilder bulder = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (Character.isDigit(c) || c == '.' || c == '-')
				bulder.append(c);
		}
		super.insertString(offs, bulder.toString(), a);
	}

}
