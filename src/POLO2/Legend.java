package POLO2;

import POLO2.Animals.*;
import POLO2.Plants.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Legend {
    private int length = 0, height = 0;

    private final List<Class<?>> classList = new ArrayList<>();
    private Map<String,Color> legendMap;

    void resize(List<Organism> list) {
        classList.clear();
        for (Organism obj : list) {
            boolean match = false;
            if (!classList.isEmpty()) {
                for (var typ : classList) {
                    if (obj.getClass() == typ) {
                        match = true;
                        break;
                    }
                }
            }
            if (!match) {
                classList.add(obj.getClass());
            }
        }
        height = classList.size();
        length = 0;
        legendMap = namesToLegendMap(classList);
    }
    int getLength() { return length; }
    int getHeight() { return height; }
    public Map<String,Color> getLegendMap() { return legendMap; }

    Map<String,Color> namesToLegendMap(List<Class<?>> list) {
        Map<String,Color> tempMap = new HashMap<>();
        for (Class<?> objectClass : list) {
            String s = objectClass.getSimpleName();
            tempMap.put(s,getClassColor(objectClass));
            if (s.length() > length) length = s.length();
        }
        return tempMap;
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

