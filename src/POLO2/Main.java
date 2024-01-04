package POLO2;

import javax.swing.*;

import POLO2.OrganismBuilder;

public class Main {
    public static void main(String[] args) {
        World currentWorld = new World();
        Organism a = new OrganismBuilder(currentWorld).build("Wolf");
        System.out.println(a.getClass().getSimpleName() + "  " + a.getID());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Window window = new Window();
            }
        });
    }
}