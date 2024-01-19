package POLO2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import static POLO2.Settings.*;
import static java.util.Collections.swap;

public class World {
    int size;
    int turn;
    Legend legend;
    List<SystemEvent> eventList = new ArrayList<>();
    List<Organism> worldOrganisms = new ArrayList<>();

    private Window window;
    private JComponent contentComponent,eventComponent,legendComponent;

    World(){
        this.size = Settings.N;
        this.turn = 0;
        this.legend = new Legend();
    }
    void performTurns(){
        turn++;
        sortOrganisms();
        for (int i=0;i<worldOrganisms.size();i++) {
            Organism var = worldOrganisms.get(i);
                System.out.println(var.getID() + "  " + var.getPosition().print() + "  " + var.getIsDead());
            if(!var.getIsDead()){
                Position position = var.getPosition();
                JComponent component = (JComponent) contentComponent.getComponent((this.size + 1) * position.getY() + position.getX());
                component.setBackground(new Color(238, 238, 238));
                if (var.isAfterTurn()) {
                    var.setAfterAction(false);
                } else if (var.getStun() != 0) {
                    this.pushEvent(new SystemEvent(var.getID() + " is stunned."));
                    var.setStun(var.getStun() - 1);
                } else {
                    var.action();
                }
            }
        }
        this.removeFromWorld();
        for (Organism worldOrganism : worldOrganisms) {
            worldOrganism.setAfterAction(false);
        }
        paintTheWorld();
    }

    Icon createIcon(Color color,int size){
        BufferedImage image = new BufferedImage(size,size,BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = image.createGraphics();

        graphics2D.setColor(color);
        graphics2D.fillRect(0,0,size,size);
        graphics2D.dispose();

        return new ImageIcon(image);
    }

    void alterGrid(int x, int y){
        if(isObjectOnPosition(x, y)){
            Organism var = returnObjectFromPosition(x,y);
            String objectInformation;
            if(var instanceof Plant){
                objectInformation = "Position: " + var.getPosition().print() +
                        "\nSpawn rate: " + spawnRate+
                        "\nSince turn: " + var.getAliveSince()
                        ;
            }else{
                objectInformation = "Position: " + var.getPosition().print() +
                        "\nStrength: " + var.getStrength()+
                        "\nInitiative: " + var.getInitiative()+
                        "\nStun: " + var.getStun()+
                        "\nSince turn: " + var.getAliveSince()
                        ;
            }
            JOptionPane.showMessageDialog(contentComponent,
                    objectInformation,
                    "Information of ~~ "+var.getID(),
                    JOptionPane.INFORMATION_MESSAGE,
                    createIcon(var.color,50)
                    );
        }else{
            String selectedOrganismName = (String) JOptionPane.showInputDialog(
                    contentComponent,
                    "Choose class of a new organism:",
                    "Add an organism",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    this.legend.returnSimpleListAsObjectArray(),
                    null
            );
            if(selectedOrganismName != null){
                Organism var = new OrganismBuilder(this).setPosition(x,y).build(selectedOrganismName);
                JComponent component = (JComponent) contentComponent.getComponent((this.size+1) *y + x);
                component.setBackground(var.paint());
            }
        }
    }

    void paintTheWorld(){
        JLabel turnLabel = (JLabel) contentComponent.getComponent(0);
        turnLabel.setText("T:"+turn);
        int width = Window.returnGraphicsTextLength(turnLabel,turnLabel.getText());
        while(width >= (contentWidth/((this.size+1)))-2){
            turnLabel.setFont(turnLabel.getFont().deriveFont((float)turnLabel.getFont().getSize()-1));
            width = Window.returnGraphicsTextLength(turnLabel,turnLabel.getText());
        }
        for(Organism var : worldOrganisms) {
            if(!var.getIsDead()){
                Position position = var.getPosition();
                JComponent component = (JComponent) contentComponent.getComponent((this.size+1) *position.getY() + position.getX());
                component.setBackground(var.paint());
            }
        }
        printEvents();
        this.legend.resize(worldOrganisms,legendComponent);
    }

    void printEvents(){
        eventComponent.removeAll();
        eventComponent.updateUI();
        eventComponent.setLayout(new GridLayout(eventList.size(),1));
        for(int i=1;i<=eventList.size();i++){
            JLabel label = new JLabel(i+ " => "  + eventList.get(i-1).getEvent());
            label.setFont(label.getFont().deriveFont((float)eventHeight/(10+eventList.size())));
            eventComponent.add(label);
            int width = Window.returnGraphicsTextLength(label,label.getText());
            while(width >= windowSizeX-contentWidth){
                label.setFont(label.getFont().deriveFont((float)label.getFont().getSize()-1));
                width = Window.returnGraphicsTextLength(label,label.getText());
            }
        }
        eventList.clear();
    }

    void saveWorld(Path filePath) throws CustomException{
        String header = size + ";" + turn + ";" + Organism.getNextID();
        try{
            if(!Files.exists(filePath)){
                Files.write(filePath,header.getBytes(), StandardOpenOption.CREATE_NEW);
            }else{
                Files.write(filePath,header.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
            }
            for(Organism o : worldOrganisms){
                Files.write(filePath,createStringOfOrganism(o).getBytes(), StandardOpenOption.APPEND);
            }
        }catch(IOException e){
            throw new CustomException(e.toString());
        }
    }

    String createStringOfOrganism(Organism o){
        return  "\n" + o.getClass().getSimpleName() + ";" + o.getID() + ";" + o.getPosition().print() +
                ";" + o.getAliveSince() + ";" + o.getStrength() + ";" + o.getInitiative() + ";" +
                o.getStun() + ";" + o.isAfterTurn();
    }

    void setContentComponent(JComponent contentComponent){
        this.contentComponent = contentComponent;
    }

    void setEventComponent(JComponent eventComponent){
        this.eventComponent = eventComponent;
    }

    void setLegendComponent(JComponent legendComponent){
        this.legendComponent = legendComponent;
    }

    void setTurn(int turn){
        this.turn = turn;
    }

    void setSize(int size){
        this.size = size;
    }
    void loadWorld(Path filePath) throws CustomException{
        List<String> stringsFromFile;
        try{
            stringsFromFile = Files.readAllLines(filePath);
        }catch(IOException e){
            throw new CustomException(e.toString());
        }

        String header = stringsFromFile.getFirst();
        String[] headerFragments =  header.split(";");

        setSize(Integer.parseInt(headerFragments[0]));
        setTurn(Integer.parseInt(headerFragments[1]));
        Organism.setNextID(Integer.parseInt(headerFragments[2]));

        stringsFromFile.removeFirst();
        this.worldOrganisms.clear();
        window.createContent((JPanel) contentComponent,this.size);
        while(!stringsFromFile.isEmpty()){
            String line = stringsFromFile.getFirst();
            String[] lineFragments = line.split(";");
            Organism newOrganism = new OrganismBuilder(this)
                    .setID(lineFragments[1])
                    .setPosition(Position.stringDecoder(lineFragments[2]))
                    .setAfterAction(Boolean.parseBoolean(lineFragments[7]))
                    .build(lineFragments[0]);
            newOrganism.setAliveSince(Integer.parseInt(lineFragments[3]));
            newOrganism.setStrength(Integer.parseInt(lineFragments[4]));
            newOrganism.setInitiative(Integer.parseInt(lineFragments[5]));
            newOrganism.setStun(Integer.parseInt(lineFragments[6]));
            stringsFromFile.removeFirst();
        }

        paintTheWorld();
    }

    public Organism checkCollisionOnPosition(Position p, Organism attacker){
        Organism objectOnPosition = null;
        for (Organism var : worldOrganisms) {
            if (p.equals(var.getPosition()) && !var.equals(attacker)) {
                objectOnPosition = var;
                break;
            }
        }
        return objectOnPosition;
    }
    public boolean isObjectOnPosition(Position p){
        for (Organism var : worldOrganisms) {
            if (p.equals(var.getPosition())) {
                return true;
            }
        }
        return false;
    }

    public boolean isObjectOnPosition(int x, int y){
        for (Organism var : worldOrganisms) {
            if (var.getPosition() != null && x == var.getPosition().getX() && y == var.getPosition().getY()) {
                return true;
            }
        }
        return false;
    }
    public Organism returnObjectFromPosition(Position p){
        for (Organism var : worldOrganisms) {
            if (p.equals(var.getPosition())) {
                return var;
            }
        }
        return null;
    }
    public Organism returnObjectFromPosition(int x, int y){
        for (Organism var : worldOrganisms) {
            if (x == var.getPosition().getX() && y == var.getPosition().getY()) {
                return var;
            }
        }
        return null;
    }

    public int getSize() {return size;}

    public void setWindow(Window window) { this.window = window;}

    public int getTurn() { return turn; }

    public void pushEvent(SystemEvent e){
        boolean doNotPush = false;
        for (SystemEvent event : eventList) {
            if (e.getEvent().equals(event.getEvent())) {
                doNotPush = true;
                break;
            }
        }
        if (!doNotPush) {
            this.eventList.add(e);
        }
    }
    public void pushToRemove(Organism o){
        o.setPosition(-1,-1);
        o.setIsDead(true);
        o.setAfterAction(true);
    }
    void pushToWorld(Organism o){
        this.worldOrganisms.add(o);
    }

    void removeFromWorld(){
        worldOrganisms.removeIf(Organism::getIsDead);
    }

    public void repaintCell(Position position){
        System.out.println("Breaker: " +((this.size + 1) * position.getY() + position.getX()));
        JComponent component = (JComponent) contentComponent.getComponent((this.size + 1) * position.getY() + position.getX());
        component.setBackground(new Color(238, 238, 238));
    }

    void sortOrganisms(){
        for (int i = 0; i < worldOrganisms.size() - 1; i++) {
            for (int j = 0; j < worldOrganisms.size() - i - 1; j++) {
                if (worldOrganisms.get(j).getInitiative() < worldOrganisms.get(j + 1).getInitiative() ||
                        (worldOrganisms.get(j).getInitiative() == worldOrganisms.get(j + 1).getInitiative() &&
                        worldOrganisms.get(j).getAliveSince() > worldOrganisms.get(j + 1).getAliveSince())) {
                    swap(worldOrganisms,j,j + 1);
                }
            }
        }
    }
}
