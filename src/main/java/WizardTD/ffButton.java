package WizardTD;
import processing.core.PApplet;

public class ffButton extends Button {
    
    ffButton(PApplet parent, int x, int y, int w, int h, String t, String et, int r, int g, int b) {
        super(parent, x, y, w, h, t, et, r, g, b);
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

    float ff(float currentSpeed) {
        if (super.Clicked) {
            currentSpeed = 2;
        } else {
            currentSpeed = 1;
        }
        return currentSpeed;
    }
}
