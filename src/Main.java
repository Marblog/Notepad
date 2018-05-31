import javax.swing.*;

public class Main {
    public static void main(String args[]) {
        SplashWindow splashWindow = new SplashWindow("Hello", new JFrame(), 5000);
        splashWindow.star();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Notepad().setVisible(true);
    }
}
