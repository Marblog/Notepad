import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("serial")
public class Notepad extends JFrame {

    private JMenu jMenu;
    JMenuBar jmb = new JMenuBar();
    JTextArea textArea = new JTextArea(20, 50);
    JPanel panel = new JPanel();
    JLabel statusLabel1, statusLabel2;
    PrintJob p = null;
    Graphics g = null;// 要打印的对象

    private File currentFile;
    private boolean isNewFile;

    public Notepad() {
        JFrame();
        panel.setLayout(new BorderLayout());// 设置BorderLayout，让JTextArea自动充满
        JScrollPane jScrollPane = new JScrollPane(textArea);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(jScrollPane, BorderLayout.CENTER);
        Menu1();
        Menu2();
        Menu3();
        Menu4();
        Menu5();
        RightClickMenu();// 右击菜单
        AddComp(); // 添加组件
        WindowsClose();//Windows关闭事件

    }

    private void AddComp() {
        this.add(panel);
        this.setJMenuBar(jmb);
    }

    private void RightClickMenu() {
        final JPopupMenu popupMenu = new JPopupMenu();
        final JMenuItem menuOpen = new JMenuItem("打开");
        JMenuItem menuItemSave = new JMenuItem("保存");
        JMenuItem menuItemCopy = new JMenuItem("复制");
        JMenuItem menuItemCut = new JMenuItem("剪切");
        JMenuItem menuItemPaste = new JMenuItem("粘贴");
        popupMenu.add(menuOpen);
        popupMenu.addSeparator();// 添加分割线
        popupMenu.add(menuItemSave);
        popupMenu.addSeparator();// 添加分割线
        popupMenu.add(menuItemCopy);
        popupMenu.addSeparator();// 添加分割线
        popupMenu.add(menuItemCut);
        popupMenu.addSeparator();// 添加分割线
        popupMenu.add(menuItemPaste);

        textArea.add(popupMenu);

        RightClickMenuListener(popupMenu, menuOpen, menuItemSave, menuItemCopy, menuItemCut, menuItemPaste);

    }

    private void JFrame() {
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        Image img = new ImageIcon("image/logo.jpg").getImage();
        this.setIconImage(img);
        this.setTitle("Notepad");

    } // Jframe

    private void Menu1() {
        JMenu jm1 = new JMenu("文件(F)");// 下拉菜单“文件”
        JMenuItem newfile = new  JMenuItem("新建");
        JMenuItem jmt1_1 = new JMenuItem("打开(O)");
        JMenuItem jmt1_2 = new JMenuItem("保存(S)");
        JMenuItem jmt1_3 = new JMenuItem("退出(X)");
        JMenuItem jmsetting = new JMenuItem("页面设置(U)");
        final JMenuItem fileSaveAs = new JMenuItem("另存为(A)...");
        JMenuItem jmprint = new JMenuItem("打印(P)");

        jm1.add(newfile);
        jm1.addSeparator();
        jm1.add(jmt1_1);
        jm1.addSeparator();
        jm1.add(jmt1_2);
        jm1.addSeparator();
        jm1.add(fileSaveAs);
        jm1.addSeparator();
        jm1.add(jmsetting);
        jm1.addSeparator();
        jm1.add(jmprint);
        jm1.addSeparator();
        jm1.add(jmt1_3);
        jm1.setIcon(new ImageIcon("image/file.jpg"));

        jmb.add(jm1);

        jm1.setMnemonic('F');// Alt+F
        /*
         * 添加事件
         */
        Jmt1_1Event(jmt1_1); // 打开

        Jmt1_2Event(jmt1_2); // 保存

        Jmt3_3Event(jmt1_3); // 退出
        jmt1_3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        PageSetting(jmsetting);// 页面设置
        jmsetting.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));

        打印(jmprint);//打印

        另存为(fileSaveAs);

    }

    private void Menu2() {
        JMenu jm2 = new JMenu("编辑(E)");// 下拉菜单“编辑”
        JMenuItem jmt2_1 = new JMenuItem("复制(C)");
        JMenuItem jmt2_2 = new JMenuItem("剪切(X)");
        JMenuItem jmt2_3 = new JMenuItem("粘贴(V)");
        JMenuItem jmt2_4 = new JMenuItem("查找(F)");
        JMenuItem jmt2_5 = new JMenuItem("全选(A)");
        JMenuItem jmt2_6 = new JMenuItem("替换(R)");
        JMenuItem jmt2_7 = new JMenuItem("清空(D)");
        JMenuItem editGoTo = new JMenuItem("转到(G)");
        JMenuItem editDate = new JMenuItem("时间/日期(D)");

        jm2.add(jmt2_1);
        jm2.addSeparator();
        jm2.add(jmt2_2);
        jm2.addSeparator();
        jm2.add(jmt2_3);
        jm2.addSeparator();
        jm2.add(jmt2_5);
        jm2.addSeparator();
        jm2.add(jmt2_4);
        jm2.addSeparator();
        jm2.add(jmt2_6);
        jm2.addSeparator();
        jm2.add(jmt2_7);
        jm2.addSeparator();
        jm2.add(editGoTo);
        jm2.addSeparator();
        jm2.add(editDate);
        jm2.setIcon( (new ImageIcon("image/edit.jpg")));
        jmb.add(jm2);
        jm2.setMnemonic('E');// Alt+E
        // 复制
        复制(jmt2_1);
        // 剪切
        剪切(jmt2_2);
        // 粘贴
        粘贴(jmt2_3);
        // 查找
        查找(jmt2_4);
        // 全选
        全选(jmt2_5);
        // 时间和日期
        时间(editDate);
        // 替换
        替换(jmt2_6);

        转到(editGoTo);

        清空(jmt2_7);
    }

    private void Menu3() {
        JMenu jm3 = new JMenu("格式(O)");// 下拉菜单“格式”
        JMenu jmt3_1 = new JMenu("格式设置");
        JMenuItem jmtext = new JMenuItem("字体设置");
        JMenuItem jmt3_1_1 = new JMenuItem("自动换行");
        JMenuItem jmt3_1_2 = new JMenuItem("取消自动换行");
        JMenuItem jmt3_1_3 = new JMenuItem("断行不断字");
        JMenuItem jmt3_1_4 = new JMenuItem("取消断行不断字");

        jmt3_1.add(jmt3_1_1);
        jmt3_1.addSeparator();
        jmt3_1.add(jmt3_1_2);
        jmt3_1.addSeparator();
        jmt3_1.add(jmt3_1_3);
        jmt3_1.addSeparator();
        jmt3_1.add(jmt3_1_4);
        jm3.add(jmt3_1);
        jm3.addSeparator();
        jm3.add(jmtext);
        jm3.setMnemonic('O');
        jm3.setIcon(new ImageIcon("image/ges.jpg"));
        jmb.add(jm3);

        // 自动换行
        自动换行(jmt3_1_1);
        // 取消自动换行
        取消自动换行(jmt3_1_2);
        // 断行不断字
        断行不断字(jmt3_1_3);
        // 取消断行不断字
        取消断行不断字(jmt3_1_4);

        字体设置(jmtext);
    }

    private void Menu4() {
        final Color c = textArea.getBackground();// 默认背景
        final Font a = textArea.getFont(); // 默认字体
        jMenu = new JMenu("设置(S)");
        JMenuItem jm4 = new JMenuItem("颜色设置");
        JMenu jmMusic = new JMenu("音乐设置");
        JMenuItem TextDefault = new JMenuItem("字体颜色");
        JMenuItem MusicOpen = new JMenuItem("音乐开");
        JMenuItem MusicClose = new JMenuItem("音乐关");

        jMenu.add(jm4);
        jMenu.addSeparator();
        jMenu.add(TextDefault);
        jMenu.addSeparator();
        jmMusic.add(MusicOpen);
        jmMusic.addSeparator();
        jmMusic.add(MusicClose);
        jMenu.add(jmMusic);
        jMenu.setMnemonic('S');
        jMenu.setIcon(new ImageIcon("image/set.jpg"));
        jmb.add(jMenu);

        TextDefault.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(Notepad.this, "颜色选择", Color.BLACK);
                textArea.setForeground(color);
            }
        });

        jm4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(Notepad.this, "颜色选择", Color.BLACK);
                textArea.setBackground(color);
            }
        });

        MusicOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Play();
            }
        });

        MusicClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Stop();
            }
        });
    }

    private void Menu5() {
        jMenu = new JMenu("关于(A)");
        final JMenuItem jmt5_1 = new JMenuItem("帮助");
        JMenuItem jmt5_2 = new JMenuItem("关于记事本");
        JMenuItem jmt5_3 = new JMenuItem("官方网站");

        jMenu.add(jmt5_1);
        jMenu.addSeparator();
        jMenu.add(jmt5_2);
        jMenu.addSeparator();
        jMenu.add(jmt5_3);
        jMenu.setMnemonic('A');
        jMenu.setIcon(new ImageIcon("image/about.jpg"));
        jmb.add(jMenu);

        帮助(jmt5_1);
        关于(jmt5_2);
        网站(jmt5_3);
    }

    /**
     * @param jmt1_1
     */
    private void Jmt1_1Event(JMenuItem jmt1_1) {
        jmt1_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int rval = fc.showOpenDialog(Notepad.this);
                if (rval == JFileChooser.APPROVE_OPTION) {
                    String fileName = fc.getSelectedFile().getName();
                    String path = fc.getCurrentDirectory().toString();
                    try {
                        FileReader fread = new FileReader(path + "/" + fileName);
                        BufferedReader bread = new BufferedReader(fread);
                        String line = bread.readLine();
                        while (line != null) {
                            textArea.append(line + "\n");
                            line = bread.readLine();
                        }
                        bread.close();
                        fread.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        jmt1_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
    }

    private void Jmt1_2Event(JMenuItem jmt1_2) {
        jmt1_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                保存文件();
            }

        });
        jmt1_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
    }

    private void 打印(JMenuItem jmprint) {
        jmprint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    p = getToolkit().getPrintJob(Notepad.this, "ok", null);// 创建一个Printfjob
                    // 对象 p
                    g = p.getGraphics();// p 获取一个用于打印的 Graphics 的对象
                    // g.translate(120,200);//改变组建的位置
                    textArea.printAll(g);
                    p.end();// 释放对象 g
                } catch (Exception a) {

                }
            }

        });// 打印
        jmprint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
    }

    private void Jmt3_3Event(JMenuItem jmt1_3) {
        jmt1_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void PageSetting(JMenuItem jmsetting) {
        jmsetting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PageFormat pf = new PageFormat();
                PrinterJob.getPrinterJob().pageDialog(pf);
            }
        });
    }

    private void 保存文件() {
        JFileChooser fc = new JFileChooser();
        int rval = fc.showSaveDialog(Notepad.this);
        if (rval == JFileChooser.APPROVE_OPTION) {
            String fileName = fc.getSelectedFile().getName();
            String path = fc.getCurrentDirectory().toString();
            try {
                FileWriter fwWriter = new FileWriter(path + "/" + fileName);
                fwWriter.write(textArea.getText());
                fwWriter.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void 另存为(JMenuItem fileSaveAs) {
        fileSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setApproveButtonText("确定");
                fileChooser.setDialogTitle("另存为");

                int result = fileChooser.showSaveDialog(Notepad.this);
                if (result == JFileChooser.CANCEL_OPTION) {
                    statusLabel2.setText("文件为保存");
                    return;
                }

                File saveFileName = fileChooser.getSelectedFile();
                if (saveFileName == null || saveFileName.getName().equals("")) {
                    JOptionPane.showMessageDialog(Notepad.this, "错误的文件名", "错误的文件名", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {

                        OutputStream os = new FileOutputStream(currentFile);
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        PrintWriter pw = new PrintWriter(osw);
                        pw.write(textArea.getText());
                        pw.flush();
                        pw.close();

                        boolean isNewFile = false;
                        currentFile = saveFileName;
                        Notepad.this.setTitle(saveFileName.getName());
                        statusLabel2.setText("当前文件名  " + saveFileName.getAbsolutePath());
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        });// 另存为
        fileSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
    }

    /**
     * @param jmt2_1
     */

    private void 复制(JMenuItem jmt2_1) {
        jmt2_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.copy();
            }
        });
        jmt2_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
    }

    private void 剪切(JMenuItem jmt2_2) {
        jmt2_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.cut();
            }
        });
        jmt2_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
    }

    private void 粘贴(JMenuItem jmt2_3) {
        jmt2_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.paste();
            }
        });
        jmt2_3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
    }

    private void 查找(JMenuItem jmt2_4) {
        jmt2_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new find().find();
            }
        });
        jmt2_4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
    }

    private void 清空(JMenuItem jmt2_7) {

        jmt2_7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });
        jmt2_7.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK));
    }

    private void 全选(JMenuItem jmt2_5) {
        jmt2_5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.selectAll();
            }
        });
        jmt2_5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
    }

    private void 时间(JMenuItem editDate) {
        editDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar now = Calendar.getInstance();
                Date date = now.getTime();
                textArea.insert(date.toString(), textArea.getCaretPosition()); // 获取当前文本编辑区的位置并插入
            }
        });
        editDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
    }

    private void 替换(JMenuItem jmt2_6) {
        jmt2_6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new replace().replace();
            }
        });
        jmt2_6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
    }

    private void 转到(JMenuItem editGoTo) {
        editGoTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnTo turnTo = new turnTo();
                turnTo.turnTo();
            }
        });
        editGoTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
    }

    /**
     * @param jmt3_1_4
     */
    private void 取消断行不断字(JMenuItem jmt3_1_4) {
        jmt3_1_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setWrapStyleWord(false);
            }
        });
    }

    private void 断行不断字(JMenuItem jmt3_1_3) {
        jmt3_1_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setWrapStyleWord(true);
                ;
            }
        });
    }

    private void 取消自动换行(JMenuItem jmt3_1_2) {
        jmt3_1_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setLineWrap(false);
            }
        });
    }

    private void 自动换行(JMenuItem jmt3_1_1) {
        jmt3_1_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setLineWrap(true); // 自动换行
            }
        });
    }

    private void 字体设置(JMenuItem jmtext) {
        jmtext.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MyFont font = new MyFont(textArea.getFont());
                int returnValue = font.showFontDialog(Notepad.this);
                if (returnValue == font.APPROVE_OPTION) {
                    Font f = font.getSelectFont();
                    textArea.setFont(f);
                } else {
                    statusLabel2.setText("未选择新字体");
                    return;
                }

            }
        });
        jmtext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK));
    }

    private void 网站(JMenuItem jmt5_3) {
        jmt5_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI("https://github.com/Marblog/Notepad"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }

            }
        });
        jmt5_3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
    }

    private void 关于(JMenuItem jmt5_2) {
        jmt5_2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(panel, "", "关于", JOptionPane.DEFAULT_OPTION,0,new ImageIcon("image/hj.jpg"));
            }
        });
        jmt5_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_DOWN_MASK));
    }

    private void 帮助(JMenuItem jmt5_1) {
        jmt5_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Help.Star();
            }
        });
        jmt5_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
    }

    /**
     * 右击菜单
     */
    private void RightClickMenuListener(final JPopupMenu popupMenu, JMenuItem menuOpen, JMenuItem menuItemSave,
                                        JMenuItem menuItemCopy, JMenuItem menuItemCut, JMenuItem menuItemPaste) {
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mods = e.getModifiers();
                if ((mods & InputEvent.BUTTON3_MASK) != 0) {
                    // 调用show方法显示弹出式菜单
                    popupMenu.show(textArea, e.getX(), e.getY());
                }
            }
        });

        Jmt1_1Event(menuOpen);
        Jmt1_2Event(menuItemSave);
        复制(menuItemCopy);
        剪切(menuItemCut);
        粘贴(menuItemPaste);
    }

    private void WindowsClose() {

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (textArea.getText().equals("")) {
                    System.exit(0);
                } else {
                    int r = JOptionPane.showConfirmDialog(panel, "是否保存？", "菜单", JOptionPane.YES_NO_CANCEL_OPTION);
                    System.out.println(r);
                    if (r == 0) {
                        保存文件();
                    }
                    if (r == 1) {
                        System.exit(1);
                    }
                    if (r == 2) {
                        Notepad.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    }
                }
            }
        });
    }

    /**
     * 音乐
     */
    public void Play() {
        try {
            URL url;
            File file = new File("m/qin.mid");
            url = file.toURL();
            AudioClip audioClip = Applet.newAudioClip(url);
            audioClip.play();
            audioClip.loop();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println("Ok");
    }

    public void Stop() {
        try {
            URL url;
            File file = new File("m/qin.mid");
            url = file.toURL();
            AudioClip audioClip = Applet.newAudioClip(url);
            audioClip.stop();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println("Close");
    }

    public static void main(String args[]) {
        SplashWindow splashWindow = new SplashWindow("Hello",new JFrame(),5000);
        splashWindow.star();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Notepad().setVisible(true);
    }

}
