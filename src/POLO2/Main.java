package POLO2;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        World currentWorld = new World();
        new OrganismBuilder(currentWorld).build("Wolf");
        new OrganismBuilder(currentWorld).build("Wolf");
        new OrganismBuilder(currentWorld).build("Guarana");
        new OrganismBuilder(currentWorld).build("Guarana");
        new OrganismBuilder(currentWorld).build("Sheep");
        new OrganismBuilder(currentWorld).build("Sheep");
        new OrganismBuilder(currentWorld).build("Hedgehog");
        new OrganismBuilder(currentWorld).build("Hedgehog");
        new OrganismBuilder(currentWorld).build("Turtle");
        new OrganismBuilder(currentWorld).build("Turtle");
        new OrganismBuilder(currentWorld).build("Zebra");
        new OrganismBuilder(currentWorld).build("Zebra");
        new OrganismBuilder(currentWorld).build("Wolfberries");
        new OrganismBuilder(currentWorld).build("Wolfberries");
        new OrganismBuilder(currentWorld).build("Mosquito");
        new OrganismBuilder(currentWorld).build("Mosquito");
        new OrganismBuilder(currentWorld).build("Grass");
        new OrganismBuilder(currentWorld).build("Grass");
        new OrganismBuilder(currentWorld).build("Grass");
        SwingUtilities.invokeLater(() -> {
            new Window(currentWorld);
            currentWorld.paintTheWorld();
        });
    }
}