package POLO2;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

import static POLO2.Settings.*;

public class Window {
    public final JFrame window;
    private final JPanel content;
    private final JPanel legend;
    private final JPanel footer;
    private final JPanel events;
    private final JButton nextTurnButton;
    private final JButton saveButton;
    private final JButton loadButton;

    private final JLabel feedback;
    public Window() {
        window = new JFrame();
        window.setTitle("Game Of Life Java");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setBounds(0,0,windowSizeX,windowSizeY);
        window.setLocationRelativeTo(null);
        //window.setResizable(false);
        window.setLayout(null);
        window.setVisible(true);

        content = createPanel(new Color(255,255,220),0,0,contentWidth,contentHeight);
        window.add(content);

        legend = createPanel(new Color(229,255,220),contentWidth,0,windowSizeX-contentWidth,eventHeight);
        window.add(legend);

        events = createPanel(new Color(228,220,255),contentWidth,eventHeight,windowSizeX-contentWidth,eventHeight);
        window.add(events);

        footer = createPanel(new Color(220,255,255),0,contentHeight,windowSizeX,windowSizeY-contentHeight-40);
        window.add(footer);

        JPanel footerLeftSide = createPanel(new Color(100,100,100),0,0,footer.getSize().width/5,windowSizeY-contentHeight-40);
        footer.add(footerLeftSide);
        JPanel footerRightSide = createPanel(new Color(200,200,200),footer.getSize().width*4/5,0,footer.getSize().width/5,windowSizeY-contentHeight-40);
        footer.add(footerRightSide);
        footer.setLayout(null);

        JTextField field = new JTextField();
        field.setBorder(new BevelBorder(BorderUIResource.BevelBorderUIResource.RAISED));
        field.setBounds(footer.getSize().width/4,footer.getSize().height/16,footer.getSize().width/2,footer.getSize().height/2);
        field.setVisible(false);

        feedback = new JLabel("Feedback");
        feedback.setVisible(false);
        feedback.setVerticalAlignment(SwingConstants.CENTER);
        feedback.setHorizontalAlignment(SwingConstants.CENTER);
        feedback.setBounds(footer.getSize().width/4,footer.getSize().height*9/16,footer.getSize().width/2,footer.getSize().height/2);
        footer.add(feedback);

        JButton confirmButton = new JButton("Confirm");
        ButtonListener confirmListener = new ButtonListener(feedback);
        confirmButton.addActionListener(confirmListener);
        confirmButton.setSize(field.getSize().width/4,field.getSize().height*3/4);
        confirmButton.setLocation(field.getSize().width*3/4,field.getSize().height/8);
        field.add(confirmButton);

        nextTurnButton = new JButton("Next Turn");
        ButtonListener turnListener = new ButtonListener(field);
        nextTurnButton.addActionListener(turnListener);
        footerLeftSide.add(nextTurnButton);

        saveButton = new JButton("Save World");
        ButtonListener saveListener = new ButtonListener(field);
        saveButton.addActionListener(saveListener);
        footerRightSide.add(saveButton);

        loadButton = new JButton("Load World");
        ButtonListener loadListener = new ButtonListener(field);
        loadButton.addActionListener(loadListener);
        footerRightSide.add(loadButton);

        JLabel label = new JLabel("Author: Marek Krasiński 192573");
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        footerLeftSide.add(label);

        footerLeftSide.setLayout(new GridLayout(2,1,10,footer.getSize().height/4));
        footerRightSide.setLayout(new GridLayout(2,1,10,footer.getSize().height/4));
        footer.add(field);
    }

    private JPanel createPanel(Color color,int x, int y, int width, int height){
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setBounds(x,y,width,height);
        return panel;
    }
}
