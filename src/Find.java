import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Find {

    Notepad notepad = new Notepad();

    public void find() {
        final JDialog findDialog = new JDialog(new JFrame(), "查找", false);  //false时允许其他窗口同时处于激活状态(即无模式)，就是其余的窗口是可以弹出的
        Container con = findDialog.getContentPane();    //返回此对话框的contentPane对象
        con.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel findContentLabel = new JLabel("查找内容(N): ");
        final JTextField findText = new JTextField(15);
        JButton findNextButton = new JButton("查找下一个(F): ");
        final JCheckBox matchCheckBox = new JCheckBox("区分大小写(C): ");
        ButtonGroup buttonGroup = new ButtonGroup();
        final JRadioButton upButton = new JRadioButton("向上(U)");
        final JRadioButton downButton = new JRadioButton("向下(D)");
        downButton.setSelected(true);
        buttonGroup.add(upButton);
        buttonGroup.add(downButton);
        /**
         * ButtonGroup此类用于为一组按钮创建一个多斥（multiple-exclusion）作用域。
         *使用相同的 ButtonGroup 对象创建一组按钮意味着“开启”其中一个按钮时，将关闭组中的其他所有按钮。
         *JRadioButton此类实现一个单选按钮，此按钮项可被选择或取消选择，并可为用户显示其状态。
         *与 ButtonGroup 对象配合使用可创建一组按钮，一次只能选择其中的一个按钮。
         *（创建一个 ButtonGroup 对象并用其 add 方法将 JRadioButton 对象包含在此组中。）
         * */
        JButton cancel = 取消(findDialog);
        //查找下一个
        查找下一个(findText, findNextButton, matchCheckBox, upButton, downButton);
        //查找界面
        查找界面(findDialog, con, findContentLabel, findText, findNextButton, matchCheckBox, upButton, downButton, cancel);

    }

    private void 查找下一个(final JTextField findText, JButton findNextButton, final JCheckBox matchCheckBox, final JRadioButton upButton, final JRadioButton downButton) {
        findNextButton.addActionListener(e -> {
            //区分大小写(C) 的JCheckBox是否被选中
            int k = 0, m = 0;
            final String str1, str2, str3, str4, strA, strB;
            str1 = notepad.textArea.getText();
            str2 = notepad.textArea.getText();
            str3 = str1.toUpperCase();  //转换为大写
            str4 = str2.toUpperCase();
            if (matchCheckBox.isSelected()) {    //区分大小写
                strA = str1;
                strB = str2;
            } else {  //不区分大小写，全部转换为大写
                strA = str3;
                strB = str4;
            }
            //向上查找
            if (upButton.isSelected()) {
                if (notepad.textArea.getSelectedText() == null) {
                    k = strA.lastIndexOf(strB, notepad.textArea.getCaretPosition() - 1);
                } else {
                    k = strB.lastIndexOf(strB, notepad.textArea.getCaretPosition() - findText.getText().length() - 1);
                }
                if (k > -1) {
                    notepad.textArea.setCaretPosition(k);
                    notepad.textArea.select(k, k + strB.length());
                } else {
                    JOptionPane.showConfirmDialog(null, "未找到查找内容", "查找", JOptionPane.ERROR_MESSAGE);
                }
            } else if (downButton.isSelected()) {
                if (notepad.textArea.getSelectedText() == null) {
                    k = strA.indexOf(strB, notepad.textArea.getCaretPosition() + 1);
                } else {
                    k = strA.indexOf(strB, notepad.textArea.getCaretPosition() - findText.getText().length() - 1);
                }
                if (k > -1) {
                    notepad.textArea.setCaretPosition(k);
                    notepad.textArea.select(k, k + strB.length());
                } else {
                    JOptionPane.showConfirmDialog(null, "未找到查找内容", "查找", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private JButton 取消(final JDialog findDialog) {
        JButton cancel = new JButton("取消");
        cancel.addActionListener(e -> findDialog.dispose());
        return cancel;
    }

    private void 查找界面(JDialog findDialog, Container con, JLabel findContentLabel, JTextField findText, JButton findNextButton, JCheckBox matchCheckBox, JRadioButton upButton, JRadioButton downButton, JButton cancel) {
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel directionPanel = new JPanel();
        directionPanel.setBorder(BorderFactory.createTitledBorder("方向"));
        //设置directionPanel的边框
        directionPanel.add(upButton);
        directionPanel.add(downButton);
        panel1.setLayout(new GridLayout(2, 1));
        panel1.add(findNextButton);
        panel1.add(cancel);
        panel2.add(findContentLabel);
        panel2.add(findText);
        panel2.add(panel1);
        panel3.add(matchCheckBox);
        panel3.add(directionPanel);
        con.add(panel2);
        con.add(panel3);
        findDialog.setSize(410, 180);
        findDialog.setResizable(false);
        findDialog.setLocation(230, 280);
        findDialog.setVisible(true);
    }
}
