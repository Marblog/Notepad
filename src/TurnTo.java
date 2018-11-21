import javax.swing.*;
import java.awt.*;

/**
 * @author Marblog
 */
public class TurnTo {
    private Notepad notepad = new Notepad();

    void turnTo() {
        final JDialog gotoDialog = new JDialog(new JFrame(), "转到下列行");
        JLabel gotoLabel = new JLabel("行数(L):");
        final JTextField linenum = new JTextField(5);
        linenum.setText("1");
        linenum.selectAll();

        JButton okButton = 确定(gotoDialog, linenum);
        JButton cancelButton = 取消(gotoDialog);

        initContainer(gotoDialog, gotoLabel, linenum, okButton, cancelButton);
    }

    private JButton 确定(final JDialog gotoDialog, final JTextField linenum) {
        JButton okButton = new JButton("确定");

        okButton.addActionListener(e -> {
            int totalLine = notepad.textArea.getLineCount();
            int[] lineNumber = new int[totalLine + 1];
            String s = notepad.textArea.getText();
            int pos = 0, t = 0;

            while (true) {
                pos = s.indexOf('\12', pos);
                if (pos == -1) {
                    break;
                }
                lineNumber[t++] = pos++;
            }

            int gt = 1;
            try {
                gt = Integer.parseInt(linenum.getText());
            } catch (NumberFormatException efe) {
                JOptionPane.showMessageDialog(null, "请输入行数!", "提示", JOptionPane.WARNING_MESSAGE);
                linenum.requestFocus(true);
                return;
            }

            if (gt < 2 || gt >= totalLine) {
                if (gt < 2) {
                    notepad.textArea.setCaretPosition(0);
                } else {
                    notepad.textArea.setCaretPosition(s.length());
                }
            } else {
                notepad.textArea.setCaretPosition(lineNumber[gt - 2] + 1);
            }

            gotoDialog.dispose();
        });
        return okButton;
    }

    private JButton 取消(final JDialog gotoDialog) {
        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(e -> gotoDialog.dispose());
        return cancelButton;
    }

    private void initContainer(JDialog gotoDialog, JLabel gotoLabel, JTextField linenum, JButton okButton, JButton cancelButton) {
        Container con = gotoDialog.getContentPane();
        con.setLayout(new FlowLayout());
        con.add(gotoLabel);
        con.add(linenum);
        con.add(okButton);
        con.add(cancelButton);
        gotoDialog.setSize(200, 110);
        gotoDialog.setResizable(false);
        gotoDialog.setLocation(300, 280);
        gotoDialog.setVisible(true);
    }
}
