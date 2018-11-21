
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Replace {
    private Notepad notepad = new Notepad();
    void replace() {
        final JDialog findDialog = new JDialog(new JFrame(), "替换", true);
        Container con = findDialog.getContentPane();
        con.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel searchContentLabel = new JLabel("查找内容(N) :");
        JLabel replaceContentLabel = new JLabel("替换为(P)　 :");
        final JTextField findText = new JTextField(22);
        final JTextField replaceText = new JTextField(22);
        final JCheckBox matchcase = new JCheckBox("区分大小写");
        ButtonGroup bGroup = new ButtonGroup();
        final JRadioButton up = new JRadioButton("向上(U)");
        final JRadioButton down = new JRadioButton("向下(D)");
        down.setSelected(true);
        bGroup.add(up);
        bGroup.add(down);
        JButton searchNext = new JButton("查找下一个(F)");
        JButton replace = new JButton("替换(R)");
        final JButton replaceAll = new JButton("全部替换(A)");
        searchNext.setPreferredSize(new Dimension(110, 22));
        replace.setPreferredSize(new Dimension(110, 22));
        replaceAll.setPreferredSize(new Dimension(110, 22));
        // "替换"按钮的事件处理
        替换(replaceText, replace);

        // "替换全部"按钮的事件处理
        替换全部(findDialog, findText, replaceText, matchcase, up, down, replaceAll);

        // "查找下一个"按钮事件处理
        查找下一个(findText, matchcase, up, down, searchNext);
        // "取消"按钮及事件处理
        JButton cancel = dispose(findDialog);

        // 创建"查找与替换"对话框的界面
        replaceDialog(findDialog, con, searchContentLabel, replaceContentLabel, findText, replaceText, matchcase, up, down, searchNext, replace, replaceAll, cancel);
    }

    private JButton dispose(final JDialog findDialog) {
        JButton cancel = new JButton("取消");
        cancel.setPreferredSize(new Dimension(110, 22));
        cancel.addActionListener(e -> findDialog.dispose());
        return cancel;
    }

    private void replaceDialog(JDialog findDialog, Container con, JLabel searchContentLabel, JLabel replaceContentLabel, JTextField findText, JTextField replaceText, JCheckBox matchcase, JRadioButton up, JRadioButton down, JButton searchNext, JButton replace, JButton replaceAll, JButton cancel) {
        JPanel bottomPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel topPanel = new JPanel();

        JPanel direction = new JPanel();
        direction.setBorder(BorderFactory.createTitledBorder("方向 "));
        direction.add(up);
        direction.add(down);
        direction.setPreferredSize(new Dimension(170, 60));
        JPanel replacePanel = new JPanel();
        replacePanel.setLayout(new GridLayout(2, 1));
        replacePanel.add(replace);
        replacePanel.add(replaceAll);
        topPanel.add(searchContentLabel);
        topPanel.add(findText);
        topPanel.add(searchNext);
        centerPanel.add(replaceContentLabel);
        centerPanel.add(replaceText);
        centerPanel.add(replacePanel);
        bottomPanel.add(matchcase);
        bottomPanel.add(direction);
        bottomPanel.add(cancel);
        con.add(topPanel);
        con.add(centerPanel);
        con.add(bottomPanel);

        // 设置"查找与替换"对话框的大小、可更改大小(否)、位置和可见性

        findDialog.setSize(480, 220);
        findDialog.setResizable(false);
        findDialog.setLocation(660, 450);
        findDialog.setVisible(true);
    }

    private void 查找下一个(final JTextField findText, final JCheckBox matchcase, final JRadioButton up, final JRadioButton down, JButton searchNext) {
        searchNext.addActionListener(e -> {
            int a = 0, b = 0;
            int findStarTpos = notepad.textArea.getCaretPosition();
            String str1, str2, str3, str4, strA, strB;
            str1 = notepad.textArea.getText();
            str2 = str1.toLowerCase();
            str3 = findText.getText();
            str4 = str3.toLowerCase();
            // "区分大小写"的CheckBox被选中
            if (matchcase.isSelected()) {
                strA = str1;
                strB = str3;
            } else {
                strA = str2;
                strB = str4;
            }

            if (up.isSelected()) {
                if (notepad.textArea.getSelectedText() == null) {
                    a = strA.lastIndexOf(strB, findStarTpos - 1);
                } else {
                    a = strA.lastIndexOf(strB, findStarTpos - findText.getText().length() - 1);
                }
            } else if (down.isSelected()) {
                if (notepad.textArea.getSelectedText() == null) {
                    a = strA.indexOf(strB, findStarTpos);
                } else {
                    a = strA.indexOf(strB, findStarTpos - findText.getText().length() + 1);
                }

            }
            if (a > -1) {
                if (up.isSelected()) {
                    notepad.textArea.setCaretPosition(a);
                    b = findText.getText().length();
                    notepad.textArea.select(a, a + b);
                } else if (down.isSelected()) {
                    notepad.textArea.setCaretPosition(a);
                    b = findText.getText().length();
                    notepad.textArea.select(a, a + b);
                }
            } else {
                JOptionPane.showMessageDialog(null, "找不到您查找的内容!", "记事本", JOptionPane.INFORMATION_MESSAGE);
            }

        });/* "查找下一个"按钮事件处理结束 */
    }

    private void 替换(final JTextField replaceText, JButton replace) {
        replace.addActionListener(e -> {

            if (replaceText.getText().length() == 0 && notepad.textArea.getSelectedText() != null) {
                notepad.textArea.replaceSelection("");
            }
            if (replaceText.getText().length() > 0 && notepad.textArea.getSelectedText() != null) {
                notepad.textArea.replaceSelection(replaceText.getText());
            }
        });
    }

    private void 替换全部(final JDialog findDialog, final JTextField findText, final JTextField replaceText, final JCheckBox matchcase, final JRadioButton up, final JRadioButton down, JButton replaceAll) {
        replaceAll.addActionListener(e -> {
            notepad.textArea.setCaretPosition(0);
            int a = 0, b = 0, replaceCount = 0;

            if (findText.getText().length() == 0) {
                JOptionPane.showMessageDialog(findDialog, "请填写查找内容!", "提示", JOptionPane.WARNING_MESSAGE);
                findText.requestFocus(true);
                return;
            }
            while (a > -1) {

                int fenestration = notepad.textArea.getCaretPosition();
                String str1, str2, str3, str4, strA, strB;
                str1 = notepad.textArea.getText();
                str2 = str1.toLowerCase();
                str3 = findText.getText();
                str4 = str3.toLowerCase();

                if (matchcase.isSelected()) {
                    strA = str1;
                    strB = str3;
                } else {
                    strA = str2;
                    strB = str4;
                }

                if (up.isSelected()) {
                    if (notepad.textArea.getSelectedText() == null) {
                        a = strA.lastIndexOf(strB, fenestration - 1);
                    } else {
                        a = strA.lastIndexOf(strB, fenestration - findText.getText().length() - 1);
                    }
                } else if (down.isSelected()) {
                    if (notepad.textArea.getSelectedText() == null) {
                        a = strA.indexOf(strB, fenestration);
                    } else {
                        a = strA.indexOf(strB, fenestration - findText.getText().length() + 1);
                    }

                }

                if (a > -1) {
                    if (up.isSelected()) {
                        notepad.textArea.setCaretPosition(a);
                        b = findText.getText().length();
                        notepad.textArea.select(a, a + b);
                    } else if (down.isSelected()) {
                        notepad.textArea.setCaretPosition(a);
                        b = findText.getText().length();
                        notepad.textArea.select(a, a + b);
                    }
                } else {
                    if (replaceCount == 0) {
                        JOptionPane.showMessageDialog(findDialog, "找不到您查找的内容!", "记事本", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(findDialog, "成功替换" + replaceCount + "个匹配内容!", "替换成功", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                if (replaceText.getText().length() == 0 && notepad.textArea.getSelectedText() != null) {
                    notepad.textArea.replaceSelection("");
                    replaceCount++;
                }
                if (replaceText.getText().length() > 0 && notepad.textArea.getSelectedText() != null) {
                    notepad.textArea.replaceSelection(replaceText.getText());
                    replaceCount++;
                }
            }// end while
        }); /* "替换全部"按钮的事件处理结束 */
    }
}
