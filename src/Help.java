import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

/**
 * @author Marblog
 */
public class Help {
    private JTextPane textPane;

    private Help(){
        textPane=new JTextPane();
        textPane.setBackground(Color.black);
        textPane.setEditable(false);
    }
    private void setYellowBold20(){
        SimpleAttributeSet attrset=new SimpleAttributeSet();
        StyleConstants.setForeground(attrset,Color.yellow);
        StyleConstants.setBold(attrset,true);
        StyleConstants.setFontSize(attrset,40);
        insert("记事本",attrset);
    }
    private void setBlueItalicBold22(){
        SimpleAttributeSet attrset=new SimpleAttributeSet();
        StyleConstants.setForeground(attrset,Color.blue);
        StyleConstants.setItalic(attrset,true);
        StyleConstants.setFontSize(attrset,24);
        insert("采用JavaSwing设计的记事本，和Windows自带的差不多,",attrset);
    }
    private void setRedUnderlineItalic24(){
        SimpleAttributeSet attrset=new SimpleAttributeSet();
        StyleConstants.setForeground(attrset,Color.red);
        StyleConstants.setUnderline(attrset,true);
        StyleConstants.setItalic(attrset,true);
        StyleConstants.setFontSize(attrset,24);
        insert("源码放在了 帮助>>官方网站里面.",attrset);
    }
    private void insert(String str, AttributeSet attrset){
        Document docs=textPane.getDocument();
        str=str+"\n";
        try{
            docs.insertString(docs.getLength(),str,attrset);
        }catch(BadLocationException ble){
            System.out.println("BadLocationException:"+ble);
        }
    }
    private Component getComponent(){
        return textPane;
    }
    static void star(){
        Help pane=new Help();
        pane.setYellowBold20();
        pane.setBlueItalicBold22();
        pane.setRedUnderlineItalic24();
        JFrame f=new JFrame("关于");
        f.getContentPane().add(pane.getComponent());
        f.setSize(400,400);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setVisible(true);
    }
}