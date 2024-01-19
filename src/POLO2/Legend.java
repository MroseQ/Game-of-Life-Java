package POLO2;

import POLO2.Animals.*;
import POLO2.Plants.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static POLO2.Settings.contentWidth;
import static POLO2.Settings.windowSizeX;

public class Legend {
    private final List<Class<?>> classList = new ArrayList<>();
    private final List<String> simpleList = new ArrayList<>();

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
        changeSimpleList();
        repaintLegend(component, classList);
    }

    void changeSimpleList(){
        simpleList.clear();
        for(Class<?> c : classList){
            simpleList.add(c.getSimpleName());
        }
    }

    public Object[] returnSimpleListAsObjectArray(){
        Object[] list = new Object[simpleList.size()];
        for(int i=0;i<simpleList.size();i++){
            list[i] = simpleList.get(i);
        }
        return list;
    }

    void repaintLegend(JComponent component,List<Class<?>> list){
        int panelWidth = windowSizeX- contentWidth;
        component.removeAll();
        component.updateUI();
        component.setLayout(new GridLayout(list.size(),1));
        for (Class<?> objectClass : list) {
            int panelHeight = Settings.eventHeight/list.size();
            String name = objectClass.getSimpleName();

            Color c = getClassColor(objectClass);
            float[] hsv = Color.RGBtoHSB(c.getRed(),c.getGreen(),c.getBlue(),null);
            hsv[1] = Math.max(hsv[1]-0.5f,0);
            hsv[2] = Math.min(hsv[2]+0.5f,1);
            Color lighterColor = Color.getHSBColor(hsv[0],hsv[1],hsv[2]);

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

            JLabel textSpace = new JLabel(name);
            textSpace.setForeground(Color.BLACK);
            textSpace.setFont(textSpace.getFont().deriveFont(panelHeight/2f));
            textSpace.setSize(panelWidth-colorSpace.getWidth()-(panelWidth*2/25),panelHeight);
            textSpace.setLocation(panelWidth*2/35+colorSpace.getWidth(),0);
            textSpace.setVerticalAlignment(SwingConstants.CENTER);
            textSpace.setHorizontalAlignment(SwingConstants.LEFT);

            int width = Window.returnGraphicsTextLength(textSpace,textSpace.getText());
            while(width >= textSpace.getWidth()){
                textSpace.setFont(textSpace.getFont().deriveFont((float)textSpace.getFont().getSize()-1));
                width = Window.returnGraphicsTextLength(textSpace,textSpace.getText());
            }

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

