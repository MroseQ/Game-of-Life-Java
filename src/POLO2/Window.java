package POLO2;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import static POLO2.Settings.*;

public class Window {
    private final World world;
    private final JPanel footer;

    public Window(World world) {

        this.world = Objects.requireNonNullElseGet(world, World::new);
        this.world.setWindow(this);
        JFrame window = new JFrame();
        setSettings(window);

        JPanel content = createPanel(new Color(255, 255, 220), 0, 0, contentWidth, contentHeight);
        content.setAlignmentY(Component.CENTER_ALIGNMENT);
        content.setAlignmentX(Component.CENTER_ALIGNMENT);
        createContent(content, this.world.getSize());
        window.add(content);

        JPanel legend = createPanel(new Color(229, 255, 220), contentWidth, 0, windowSizeX - contentWidth, eventHeight);
        window.add(legend);

        JPanel events = createPanel(new Color(235, 225, 255), contentWidth, eventHeight, windowSizeX - contentWidth, contentHeight - eventHeight);
        events.setLayout(null);
        JLabel eventsHeader = new JLabel("EVENTS:");
        eventsHeader.setVerticalAlignment(SwingConstants.CENTER);
        eventsHeader.setHorizontalAlignment(SwingConstants.CENTER);
        eventsHeader.setFont(eventsHeader.getFont().deriveFont(eventHeight / 10f));
        eventsHeader.setBounds(0, 0, windowSizeX - contentWidth, eventsHeader.getFont().getSize() + 5);
        events.add(eventsHeader);

        JPanel eventsContent = createPanel(new Color(235, 225, 255), contentWidth, eventHeight + eventsHeader.getHeight(), windowSizeX - contentWidth, eventHeight);
        eventsContent.setBounds(0, eventsHeader.getHeight(), windowSizeX - contentWidth, contentHeight - eventHeight - eventsHeader.getHeight());
        events.add(eventsContent);
        window.add(events);

        footer = createPanel(new Color(220, 255, 255), 0, contentHeight, windowSizeX, windowSizeY - contentHeight - 40);
        window.add(footer);

        JPanel footerLeftSide = createPanel(new Color(100, 100, 100), 0, 0, footer.getSize().width / 5, windowSizeY - contentHeight - 40);
        footer.add(footerLeftSide);
        JPanel footerRightSide = createPanel(new Color(200, 200, 200), footer.getSize().width * 4 / 5, 0, footer.getSize().width / 5, windowSizeY - contentHeight - 40);
        footer.add(footerRightSide);
        footer.setLayout(null);

        createButtonWithListener(footerLeftSide, "Next Turn");

        ButtonListener saveListener = createButtonAndReturnListener(footerRightSide, "Save World", "Save:");
        ButtonListener loadListener = createButtonAndReturnListener(footerRightSide, "Load World", "Load:");
        loadListener.setRelatedForBoth(saveListener);

        JLabel label = new JLabel("Author: Marek Krasiński 192573");
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        footerLeftSide.add(label);

        footerLeftSide.setLayout(new GridLayout(2, 1, 10, footer.getSize().height / 10));
        footerRightSide.setLayout(new GridLayout(2, 1, 10, footer.getSize().height / 10));

        this.world.setContentComponent(content);
        this.world.setEventComponent(eventsContent);
        this.world.setLegendComponent(legend);
    }

    public static int returnGraphicsTextLength(JComponent component, String text) {
        FontMetrics fontMetrics = component.getFontMetrics(component.getFont());
        return fontMetrics.stringWidth(text);
    }

    protected void createContent(JPanel content, int size) {
        content.removeAll();
        content.updateUI();

        content.setLayout(new GridLayout(size + 1, size + 1, 1, 1));
        for (int i = 0; i <= size; i++) {
            for (int j = 0; j <= size; j++) {
                if (i == 0) {
                    createTextPanel(content, i, j);
                    continue;
                } else if (j == 0) {
                    createTextPanel(content, i, j);
                    continue;
                }
                createGridPanel(i, j, content);
            }
        }
    }

    private void setSettings(JFrame window) {
        window.setTitle("Game Of Life Java");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setBounds(0, 0, windowSizeX, windowSizeY);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setLayout(null);
        window.setVisible(true);
    }

    private ButtonListener createButtonAndReturnListener(JComponent motherPanel, String buttonPrompt, String feedbackPrompt) {
        JButton button = new JButton(buttonPrompt);
        ButtonListener listener = new ButtonListener(this.world);
        listener.buildTextField(footer, feedbackPrompt);
        if (feedbackPrompt.equals("Save:")) {
            listener.setCustomAction(listener::checkForSave);
        } else {
            listener.setCustomAction(listener::checkForLoad);
        }
        button.addActionListener(listener);
        motherPanel.add(button);
        return listener;
    }

    private void createButtonWithListener(JComponent motherPanel, String buttonPrompt) {
        JButton button = new JButton(buttonPrompt);
        ButtonListener listener = new ButtonListener(this.world);
        button.addActionListener(listener);
        motherPanel.add(button);
    }

    private void createGridPanel(int y, int x, JComponent motherPanel) {
        JPanel frame = new JPanel();
        frame.setBorder(new LineBorder(Color.black, 1));
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                world.alterGrid(x, y);
                super.mouseClicked(e);
            }
        });
        motherPanel.add(frame);
    }

    private void createTextPanel(JComponent motherPanel, int i, int j) {
        JLabel label = new JLabel();
        label.setBorder(new LineBorder(Color.black, 1));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont((float) contentWidth / (2 * (world.getSize() + 1))));
        if (i != 0 || j != 0) {
            if (i == 0) {
                if (j < 10) {
                    label.setText("0" + j);
                } else {
                    label.setText(String.valueOf(j));
                }
            } else if (j == 0) {
                if (i < 10) {
                    label.setText("0" + i);
                } else {
                    label.setText(String.valueOf(i));
                }
            }
        }
        motherPanel.add(label);
    }

    private JPanel createPanel(Color color, int x, int y, int width, int height) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setBounds(x, y, width, height);
        return panel;
    }
}
