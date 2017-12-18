package it.codeSmell.aDoctor.ui;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:08
 * Description:
 */
public class TextAreaOutputStream extends OutputStream {
    private final JTextArea textControl;

    public TextAreaOutputStream(JTextArea control) {
        this.textControl = control;
    }

    public void write(int b) throws IOException {
        this.textControl.append(String.valueOf((char)b));
        this.textControl.update(this.textControl.getGraphics());
        this.textControl.setCaretPosition(this.textControl.getText().length() - 1);
    }
}