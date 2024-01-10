package POLO2;

import POLO2.Animals.*;
import POLO2.Plants.*;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Legend {
    private final List<Class<?>> classList = new ArrayList<>();
    private List<String> legendList;

    void resize(List<Organism> list, JComponent component) {
        classList.clear();
        for (Organism obj : list) {
            boolean match = false;
            if (!classList.isEmpty()) {
                for (var type : classList) {
                    if (obj.getClass() == type) {
                        match = true;
                        break;
                    }
                }
            }
            if (!match) {
                classList.add(obj.getClass());
            }
        }
        repaintLegend(component, classList);
    }

    void repaintLegend(JComponent component,List<Class<?>> list){
        int panelWidth = Settings.windowSizeX-Settings.contentWidth;
        component.removeAll();
        component.updateUI();
        component.setLayout(new GridLayout(list.size(),1));
        int wordLength = 0;
        for (Class<?> objectClass : list) {
            int panelHeight = Settings.eventHeight/list.size();
            String s = objectClass.getSimpleName();
            if(s.length() > wordLength) wordLength = s.length();
            Color c = getClassColor(objectClass);
            int lighterRed = c.getRed(),lighterGreen = c.getGreen(),lighterBlue = c.getBlue();
            while(lighterGreen != 255 && lighterBlue != 255 && lighterRed != 255){
                lighterRed++;
                lighterGreen++;
                lighterBlue++;
            }
            //ODCIENIE BACKGROUNDU DO OGARNIECIA
            Color lighterColor = new Color(lighterRed,lighterGreen,lighterBlue);
            JPanel classPanel = new JPanel();
            component.add(classPanel);
            classPanel.setLayout(null);
            classPanel.setBackground(lighterColor);
            JPanel colorSpace = new JPanel();
            colorSpace.setBorder(new LineBorder(Color.BLACK,2));
            colorSpace.setBackground(c);
            colorSpace.setSize(panelHeight/2,panelHeight/2);
            colorSpace.setLocation(panelWidth/35,panelHeight/4);
            classPanel.add(colorSpace);
            JLabel textSpace = new JLabel(s);
            textSpace.setForeground(Color.BLACK);
            textSpace.setFont(textSpace.getFont().deriveFont(panelHeight/2f));
            textSpace.setSize(panelWidth-colorSpace.getWidth()-(panelWidth*2/25),panelHeight);
            textSpace.setLocation(panelWidth*2/35+colorSpace.getWidth(),0);
            textSpace.setVerticalAlignment(SwingConstants.CENTER);
            textSpace.setHorizontalAlignment(SwingConstants.LEFT);
            JPanel test = new JPanel();
            textSpace.add(test);
            test.setBackground(Color.red);
            test.setSize(5,panelHeight);
            test.setLocation((int) (s.length()*textSpace.getFont().getSize()/1.65f),0);
            while(test.getX()+10>panelWidth-colorSpace.getWidth()-(panelWidth*2/25)){
                textSpace.setFont(textSpace.getFont().deriveFont((float)textSpace.getFont().getSize()-1));
                test.setLocation((int) (s.length()*textSpace.getFont().getSize()/1.65f),0);
            }
            textSpace.remove(test);
            classPanel.add(textSpace);
        }
    }

    Color getClassColor(Class<?> objectClass){
        if(Mosquito.class.isAssignableFrom(objectClass)){
            return Mosquito.color;
        }else if(Sheep.class.isAssignableFrom(objectClass)){
            return Sheep.color;
        }
        else if(Wolf.class.isAssignableFrom(objectClass)){
            return Wolf.color;
        }
        else if(Wolfberries.class.isAssignableFrom(objectClass)){
            return Wolfberries.color;
        }
        else if(Guarana.class.isAssignableFrom(objectClass)){
            return Guarana.color;
        }
        else if(Hedgehog.class.isAssignableFrom(objectClass)){
            return Hedgehog.color;
        }
        else if(Turtle.class.isAssignableFrom(objectClass)){
            return Turtle.color;
        }
        else if(Grass.class.isAssignableFrom(objectClass)){
            return Grass.color;
        }
        else if(Zebra.class.isAssignableFrom(objectClass)){
            return Zebra.color;
        }
        else {
            return new Color(255,255,255);
        }
    }
}

