import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class Help {
    private JTextPane textPane;

    public Help(){
        textPane=new JTextPane();
        textPane.setBackground(Color.black);
        textPane.setEditable(false);
    }
    public void setYellow_Bold_20(String str){
        SimpleAttributeSet attrset=new SimpleAttributeSet();
        StyleConstants.setForeground(attrset,Color.yellow);
        StyleConstants.setBold(attrset,true);
        StyleConstants.setFontSize(attrset,40);
        insert(str,attrset);
    }
    public void setBlue_Italic_Bold_22(String str){
        SimpleAttributeSet attrset=new SimpleAttributeSet();
        StyleConstants.setForeground(attrset,Color.blue);
        StyleConstants.setItalic(attrset,true);
        StyleConstants.setFontSize(attrset,24);
        insert(str,attrset);
    }
    public void setRed_UnderLine_Italic_24(String str){
        SimpleAttributeSet attrset=new SimpleAttributeSet();
        StyleConstants.setForeground(attrset,Color.red);
        StyleConstants.setUnderline(attrset,true);
        StyleConstants.setItalic(attrset,true);
        StyleConstants.setFontSize(attrset,24);
        insert(str,attrset);
    }
    //这个方法最主要的用途是将字符串插入到JTextPane中。
    public void insert(String str,AttributeSet attrset){
        Document docs=textPane.getDocument();//利用getDocument()方法取得JTextPane的Document instance.0
        str=str+"\n";
        try{
            docs.insertString(docs.getLength(),str,attrset);
        }catch(BadLocationException ble){
            System.out.println("BadLocationException:"+ble);
        }
    }
    public Component getComponent(){
        return textPane;
    }
    public static void Star(){
        Help pane=new Help();
        pane.setYellow_Bold_20("记事本");
        pane.setBlue_Italic_Bold_22("采用JavaSwing设计的记事本，和Windows自带的差不多,");
        pane.setRed_UnderLine_Italic_24("源码放在了 帮助>>官方网站里面.");
        JFrame f=new JFrame("关于");
        f.getContentPane().add(pane.getComponent());
        f.setSize(400,400);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setVisible(true);
    }
}