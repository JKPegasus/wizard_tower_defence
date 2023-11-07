package WizardTD;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Button{

    // initialise the App
    PApplet parent;
    // initialise the button position, width, height, text, colour
    PVector Pos = new PVector(0, 0);
    float Width = 0;
    float Height = 0;
    int Red;
    int Green;
    int Blue;
    String Text;
    String explainText;
    boolean Pressed = false;
    boolean Clicked = false;
    int clickStaus = 0;
    int clickCount = 0;

    Button(PApplet parent, int x, int y, int w, int h, String t, String et, int r, int g, int b) {
        this.parent = parent;
        Pos.x = x;
        Pos.y = y;
        Width = w;
        Height = h;
        Text = t;
        explainText = et;
        Red = r;
        Green = g;
        Blue = b;
    }
    
    void pressedDetected() {
        if (parent.mouseButton == PConstants.LEFT) {
            if (parent.mouseX >= Pos.x && parent.mouseX <= Pos.x + Width && parent.mouseY >= Pos.y && parent.mouseY <= Pos.y + Height) {

                clickStaus = (clickStaus + 1) % 2;
                clickCount++;
                
                // change the button colour when clicked
                if (clickStaus == 1) {
                    selected();
                    Clicked = true;
                } else if (clickStaus == 0) {
                    unselected();
                    Clicked = false;
                    clickCount = 0;
                }
            } else {
                if (isClicked()) {
                    clickCount++;
                }
            }
        }
    }

    void keyPressed() {

        ArrayList<Character> gameKey = new ArrayList<Character>();
        gameKey.add('P');
        gameKey.add('p');
        gameKey.add('T');
        gameKey.add('t');
        gameKey.add('M');
        gameKey.add('m');
        gameKey.add('F');
        gameKey.add('f');
        gameKey.add('1');
        gameKey.add('2');
        gameKey.add('3');

        if (gameKey.contains(parent.key)) {

            clickStaus = (clickStaus + 1) % 2;
            clickCount++;
            
            // change the button colour when clicked
            if (clickStaus == 1) {
                selected();
                Clicked = true;
            } else if (clickStaus == 0) {
                unselected();
                Clicked = false;
                clickCount = 0;
            }
        } else {
            if (isClicked()) {
                clickCount++;
            }
        }
    }

    void mouseHover() {

        if (!Clicked) {
            if (parent.mouseX >= Pos.x && parent.mouseX <= Pos.x + Width && parent.mouseY >= Pos.y && parent.mouseY <= Pos.y + Height) {
                // change the button colour when mouse hover
                this.Red = 206;
                this.Green = 206;
                this.Blue = 206;
            } else if (parent.mouseX < Pos.x || parent.mouseX > Pos.x + Width || parent.mouseY < Pos.y || parent.mouseY > Pos.y + Height) {
                this.Red = 132;
                this.Green = 115;
                this.Blue = 74;
            }
        }

        if (Clicked) {
            this.Red = 255;
            this.Green = 255;
            this.Blue = 8;
        }
    }
    
    void render() {

        // initialise the button
        parent.fill(Red, Green, Blue);
        parent.rect(Pos.x, Pos.y, Width, Height);

        // initialise the button text
        parent.fill(0);
        parent.textSize(27);
        parent.textAlign(PConstants.CENTER, PConstants.CENTER);
        parent.text(Text, Pos.x + (Width/2), Pos.y + (Height/2) - 3);

        // initialise the explain button text
        parent.fill(0);
        parent.textSize(13);
        parent.textAlign(PConstants.LEFT, PConstants.TOP);
        parent.text(explainText, Pos.x + Width + 5, Pos.y + 3);
    }

    void selected() {
        this.Red = 255;
        this.Green = 255;
        this.Blue = 8;
    }

    void unselected() {
        this.Red = 132;
        this.Green = 115;
        this.Blue = 74;
    }

    boolean isClicked(){
        return Clicked;
    }

}
