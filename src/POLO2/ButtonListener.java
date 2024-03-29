package POLO2;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.BorderUIResource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;

public class ButtonListener implements ActionListener {
    private final World world;
    private JTextField field;
    private ButtonListener parent, related;
    private JLabel feedback;
    private String label;
    private CustomAction customAction;

    public ButtonListener(World world) {
        this.world = world;
    }

    public void setCustomAction(CustomAction customAction) {
        this.customAction = customAction;
    }

    public void setParent(ButtonListener parent) {
        this.parent = parent;
    }

    public void setRelatedForBoth(ButtonListener related) {
        this.related = related;
        related.related = this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Next Turn":
                world.performTurns();
                break;
            case "Save World":
            case "Load World":
                changeVisibility();
                break;
            case "Confirm":
                parent.field.setVisible(false);
                parent.customAction.startAction(parent.field.getText());
                break;
            default:
                System.out.print("Unknown Listener " + e.getActionCommand());
        }
    }

    protected void buildTextField(JComponent footer, String text) {
        field = new JTextField();
        field.setBorder(new BevelBorder(BorderUIResource.BevelBorderUIResource.RAISED));
        field.setBounds(footer.getSize().width / 4, footer.getSize().height / 16, footer.getSize().width / 2, footer.getSize().height / 2);
        field.setVisible(false);

        feedback = new JLabel("Feedback");
        feedback.setVisible(false);
        feedback.setVerticalAlignment(SwingConstants.CENTER);
        feedback.setHorizontalAlignment(SwingConstants.CENTER);
        feedback.setBounds(footer.getSize().width / 4, footer.getSize().height * 9 / 16, footer.getSize().width / 2, footer.getSize().height / 2);
        footer.add(feedback);

        label = text;

        JButton confirmButton = new JButton("Confirm");
        ButtonListener confirmListener = new ButtonListener(world);
        confirmListener.setParent(this);
        confirmButton.addActionListener(confirmListener);
        confirmButton.setSize(field.getSize().width / 4, field.getSize().height * 3 / 4);
        confirmButton.setLocation(field.getSize().width * 3 / 4, field.getSize().height / 8);
        field.add(confirmButton);
        footer.add(field);
    }

    protected void checkForSave(String fileName) {
        Path filePath = returnPathOfFile(fileName);
        if (!fileName.isEmpty()) {
            try {
                world.saveWorld(filePath);
                feedback.setText("Save Successful to " + fileName + ".");
            } catch (CustomException e) {
                feedback.setText("Save Failed -> " + e.getMessage());
                System.out.println(e.getMessage());
            }
        } else {
            feedback.setText(("File name cannot be empty."));
        }
    }

    protected void checkForLoad(String fileName) {
        Path filePath = returnPathOfFile(fileName);
        if (!Files.exists(filePath)) {
            feedback.setText("Desired path doesn't lead to any file!");
        } else {
            try {
                world.loadWorld(filePath);
                feedback.setText("Load Successful!");
            } catch (CustomException e) {
                feedback.setText("Load Failed -> " + e.getMessage());
                System.out.println(e.getMessage());
            }
        }
    }

    private void changeVisibility() {
        field.setVisible((!field.isVisible()));
        related.field.setVisible(false);
        related.feedback.setVisible(false);
        feedback.setText(label);
        feedback.setVisible((field.isVisible()));
    }

    private Path returnPathOfFile(String fileName) {
        fileName = fileName.toLowerCase();
        if (!fileName.endsWith(".txt")) {
            fileName += ".txt";
        }
        return Path.of(fileName);
    }
}
