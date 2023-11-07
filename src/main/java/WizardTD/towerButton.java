package WizardTD;
import java.util.ArrayList;
import processing.core.PApplet;

public class towerButton extends Button{

    boolean placedAble = false;
    Tower tower;
    ArrayList<Tower> towerArray = new ArrayList<Tower>();
    Mana calMana;
    float gameSpeed;
    int CELLSIZE = 32;
    
    towerButton(PApplet parent, Mana calMana, float gameSpeed, int x, int y, int w, int h, String t, String et, int r, int g, int b) {
        super(parent, x, y, w, h, t, et, r, g, b);
        this.calMana = calMana;
        this.gameSpeed = gameSpeed;
    }

    void update() {
        super.pressedDetected();
    }

    void keyPressed() {
        super.keyPressed();
    }

    void hover() {
        super.mouseHover();
    }

    void towerPlaced(ArrayList<ArrayList<Integer>> pathPos, ArrayList<ArrayList<Integer>> shurbPos, ArrayList<ArrayList<Integer>> wizardPos) {
        
        // System.out.println("pathPos: " + pathPos);
        // System.out.println("shurbPos: " + shurbPos);
        // System.out.println("wizardPos: " + wizardPos);

        // int towerPosX = (int)Math.floor(parent.mouseX/CELLSIZE);
        // int towerPosY = (int)Math.floor(parent.mouseY/CELLSIZE);

        // ArrayList<Integer> towerPos = new ArrayList<Integer>();
        // towerPos.add(towerPosY);
        // towerPos.add(towerPosX);

        if (super.Clicked && super.clickCount > 1 && calMana.getManaValue() >= 100) {
            if (parent.mouseX >= 0 && parent.mouseX <= 640 && parent.mouseY >= 40 && parent.mouseY <= 680){
                
                // check for the ban pos
                int towerPosX = (int)Math.floor(parent.mouseX/CELLSIZE);
                int towerPosY = (int)Math.floor(parent.mouseY/CELLSIZE)-1;

                ArrayList<Integer> towerPos = new ArrayList<Integer>();
                towerPos.add(towerPosY);
                towerPos.add(towerPosX);

                // System.out.println("towerPosX: " + towerPosX);
                // System.out.println("towerPosY: " + towerPosY);

                if (!pathPos.contains(towerPos) && !shurbPos.contains(towerPos) && !wizardPos.contains(towerPos)) {
                    placedAble = true;
                }

                if (placedAble) {
                    tower = new Tower(parent, this.gameSpeed, parent.mouseX, parent.mouseY-40);
                    tower.setRange();
                    tower.setDamage();
                    tower.setFireRate();
                    towerArray.add(tower);
                    calMana.countDown(100);
                    placedAble = false;
                }
                // tower = new Tower(parent, this.gameSpeed, parent.mouseX, parent.mouseY-40);
                // tower.setRange();
                // tower.setDamage();
                // tower.setFireRate();
                // towerArray.add(tower);
                // calMana.countDown(100);
            }
        }
    }

    ArrayList<Tower> getTowerArray() {
        return towerArray;
    }
}
