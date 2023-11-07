package WizardTD;
import processing.core.PApplet;

public class pauseButton extends Button {

    float originalSpeed;
    
    pauseButton(PApplet parent, int x, int y, int w, int h, String t, String et, int r, int g, int b) {
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

    float pause(float currentSpeed) {
        // record the original speed
        originalSpeed = currentSpeed;
        if (super.Clicked) {
            currentSpeed = 0;
        } else {
            currentSpeed = originalSpeed;
        }
        return currentSpeed;
    }
    
}
