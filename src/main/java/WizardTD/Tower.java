package WizardTD;

import processing.core.PApplet;

import java.util.ArrayList;

public class Tower {
    float cost;
    float damage;
    float init_damage;
    float range;
    float fire_rate;
    float Vx;
    float Vy;
    float distanceTwoPoints;
    Gain_json gainJson;
    float posX;
    float posY;
    float fireBallPosX;
    float fireBallPosY;
    int CELLSIZE = 32;
    int FPS = 60;
    PApplet parent;
    boolean startFire = false;
    int inRangeFrame = 0;
    float gameSpeed;
    int rangeLevel = 0;
    int damageLevel = 0;
    int speedLevel = 0;
    ArrayList<Monsters> inRangeList = new ArrayList<Monsters>();
    ArrayList<fireBall> fireBall_array = new ArrayList<fireBall>();


    Tower(PApplet parent, float gameSpeed, float posX, float posY) {
        this.parent = parent;
        this.gameSpeed = gameSpeed;
        this.gainJson = new Gain_json(parent, "config.json");
        this.posX = posX;
        this.posY = posY;
        this.fireBallPosX = (float)(Math.floor(posX/CELLSIZE)*CELLSIZE)+CELLSIZE/2;
        this.fireBallPosY = 40+(float)(Math.floor(posY/CELLSIZE)*CELLSIZE)+CELLSIZE/2;
    }

    void setCost() {
        this.cost = ((Integer) gainJson.gain_method("tower_cost")).floatValue();
    }

    void setDamage() {
        this.damage = ((Integer) gainJson.gain_method("initial_tower_damage")).floatValue();
        this.init_damage = ((Integer) gainJson.gain_method("initial_tower_damage")).floatValue();
    }

    void setRange() {
        this.range = ((Integer) gainJson.gain_method("initial_tower_range")).floatValue();
    }

    void setFireRate() {
        this.fire_rate = ((Double) gainJson.gain_method("initial_tower_firing_speed")).floatValue();
        this.fire_rate *= gameSpeed;
    }

    float getCost() {
        return cost;
    }

    float getDamage() {
        return damage;
    }

    float getRange() {
        return range;
    }

    float getFireRate() {
        return fire_rate;
    }

    float[] getPos() {

        float[] pos = {(float)(Math.floor(posX/CELLSIZE)*CELLSIZE), (float)(Math.floor(posY/CELLSIZE)*CELLSIZE)};

        // System.out.println(Pos_x);
        // System.out.println(Pos_y);

        return pos;
    }

    void displayRange() {

        // set the range of the tower
        parent.noFill();
        parent.stroke(255, 255, 8);
        parent.ellipse((float)(Math.floor(posX/CELLSIZE)*CELLSIZE)+CELLSIZE/2, 40+(float)(Math.floor(posY/CELLSIZE)*CELLSIZE)+CELLSIZE/2, range*2, range*2);


    }

    void setStartFire() {
        if (inRangeList.size() > 0) {
            this.startFire = true;
        } else {
            this.startFire = false;
        }
    }

    boolean getStartFire() {
        return startFire;
    }

    void countUpInRangeFrame() {
        if (startFire) {
            this.inRangeFrame++;
        }
    }

    void resetInRangeFrame() {
        this.inRangeFrame = 0;
    }

    int getInRangeFrame() {
        return inRangeFrame;
    }

    void addInRangeList(Monsters monster) {
        this.inRangeList.add(monster);
    }

    void removeInRangeList() {
        for (int i = 0; i < inRangeList.size(); i++) {
            if (!inRangeList.get(i).getInRange() || inRangeList.get(i).deathCheck()) {
                inRangeList.remove(i);
            }
        }
    }

    ArrayList<Monsters> getInRangeList() {
        return inRangeList;
    }

    void addFireBall(fireBall fireBall) {
        fireBall_array.add(fireBall);
    }

    ArrayList<fireBall> getFireBallArray() {
        return fireBall_array;
    }

    void upgradeRange() {
        
        for (int i = 0; i < rangeLevel; i++) {
            // set the range of the tower
            parent.noFill();
            parent.noStroke();
            parent.stroke(168, 50, 135);
            parent.strokeWeight(2);
            float[] pos = getPos();
            parent.ellipse(pos[0] + i*5, pos[1]+42, 2*2, 2*2);
        }
    }

    void upgradeDamage() {
        
        for (int i = 0; i < damageLevel; i++) {

            // set the range of the tower
            parent.noFill();
            parent.noStroke();
            parent.stroke(168, 50, 135);
            parent.strokeWeight(2);
            float[] pos = getPos();
            float centerX = pos[0] + i*5 + 2;
            float centerY = pos[1]+40+26 + 2;
            float lineLength = 4;
            parent.line(centerX - lineLength / 2, centerY - lineLength / 2, centerX + lineLength / 2, centerY + lineLength / 2);
            parent.line(centerX + lineLength / 2, centerY - lineLength / 2, centerX - lineLength / 2, centerY + lineLength / 2);
            // parent.ellipse(pos[0] + i*5, pos[1]+42, 2*2, 2*2);
        }
    }

    void upgradeSpeed() {

        //System.out.println("speed: "+fire_rate);

        // set the range of the tower
        parent.noFill();
        parent.noStroke();
        parent.stroke(45, 135, 237);
        parent.strokeWeight(speedLevel*1);
        float[] pos = getPos();
        parent.rect(pos[0]+5, pos[1]+44, CELLSIZE-10, CELLSIZE-10);
    }
}
