package WizardTD;

import java.util.ArrayList;

import processing.core.PApplet;

public class speedButton extends Button {

    float posX;
    float posY;
    Mana calMana;
    
    speedButton(PApplet parent, Mana calMana, int x, int y, int w, int h, String t, String et, int r, int g, int b) {
        super(parent, x, y, w, h, t, et, r, g, b);
        this.calMana = calMana;
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

    void judgeTowerPos(ArrayList<Tower> towers) {
        if (super.Clicked && super.clickCount > 1) {
            for (Tower tower : towers) {
                posX = tower.getPos()[0];
                posY = tower.getPos()[1];
                if (parent.mouseX >= posX && parent.mouseX <= posX+32 && parent.mouseY >= (posY+40) && parent.mouseY <= (posY+32+40) && tower.speedLevel < 3 && calMana.getManaValue() >= 20+10*tower.speedLevel) {
                    tower.speedLevel++;
                    tower.fire_rate += 0.5;
                    calMana.countDown(20+10*tower.speedLevel);
                    // set the initial
                    super.clickStaus = (clickStaus + 1) % 2;
                    super.unselected();
                    super.Clicked = false;
                    super.clickCount = 0;
                }
            }
        }
    }
}
