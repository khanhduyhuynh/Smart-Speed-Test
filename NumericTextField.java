//  Author: Khanh Duy Huynh
//  Date: 23/07/2011
//  File Name: NumericTextField.java

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

class NumericTextField extends JTextField
{
	// Creates the default implementation of the model to be used at construction if one isn't explicitly given
    protected Document createDefaultModel()
    {
        return new NumericDocument();
    }

    private static class NumericDocument extends PlainDocument
    {
        // The regular expression to match input against (zero or more digits)
        private final static Pattern DIGITS = Pattern.compile("\\d");

        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
        {
            // Only insert the text if it matches the regular expression
            if (str != null && DIGITS.matcher(str).matches())
            {
                super.insertString(offs, str, a);
			}
        }
    }
}
