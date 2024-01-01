package POLO2;

import javax.swing.*;

public class Window {
    private JFrame window;

    public Window(){
        window = new JFrame();
        window.setTitle("Game Of Life Java");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(Settings.windowSizeX,Settings.windowSizeY);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
    }

    public void show(){
        window.setVisible(true);
    }
}
