package POLO2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {
    private final JComponent field;
    private World world;
    public ButtonListener(JComponent field, World world){
        this.field = field;
        this.world = world;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        switch(e.getActionCommand()){
            case "Next Turn":
                System.out.println("NextTurn");
                world.performTurns();
                break;
            case "Save World":
                field.setVisible(false);
                System.out.print("Save");
                break;
            case "Load World":
                field.setVisible(true);
                System.out.print("Load");
                break;
            case "Confirm":
                field.setVisible((!field.isVisible()));
                break;
            default:
                System.out.print("Unknown Listener " + e.getActionCommand());
        }
    }
}
