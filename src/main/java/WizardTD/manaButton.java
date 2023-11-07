package WizardTD;

import processing.core.PApplet;

public class manaButton extends Button {

    Mana calMana;
    float mana_gain_multiplier;
    
    manaButton(PApplet parent, Mana calMana, float mana_gain_multiplier, int x, int y, int w, int h, String t, String et, int r, int g, int b) {
        super(parent, x, y, w, h, t, et, r, g, b);
        this.calMana = calMana;
        this.mana_gain_multiplier = mana_gain_multiplier;
    }

    void update() {
        super.pressedDetected();
    }

    void keyPressed() {
        super.keyPressed();
    }

    boolean display_spell_cost() {
        if (parent.mouseX >= Pos.x && parent.mouseX <= Pos.x + Width && parent.mouseY >= Pos.y && parent.mouseY <= Pos.y + Height) {
            // change the button colour when mouse hover
            return true;
        }

        return false;
    }

    void hover() {
        super.mouseHover();
        display_spell_cost();
    }

    float upgradePool() {
        if (super.Clicked) {
            calMana.upgradeMana();
            mana_gain_multiplier *= 1.1;
            // set the initial
            super.clickStaus = (clickStaus + 1) % 2;
            super.unselected();
            super.Clicked = false;
            super.clickCount = 0;
        }
        return mana_gain_multiplier;
    }
}
