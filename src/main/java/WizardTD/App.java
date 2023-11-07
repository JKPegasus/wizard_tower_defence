package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
//import processing.core.PImage;
//import processing.data.JSONArray;
//import processing.data.JSONObject;
import processing.event.MouseEvent;

//import java.awt.Graphics2D;
//import java.awt.geom.AffineTransform;
//import java.awt.image.BufferedImage;

// import java.io.*;
import java.util.*;

public class App extends PApplet {

    public static final int CELLSIZE = 32;
    public static final int SIDEBAR = 120;
    public static final int TOPBAR = 40;
    public static final int BOARD_WIDTH = 20;
    

    public static int WIDTH = CELLSIZE*BOARD_WIDTH+SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH*CELLSIZE+TOPBAR;

    public static final int FPS = 60;

    public String configPath;

    public Random random = new Random();

    // initialise the variable
    private int x;
    private int y;
    private int line;
    private float mana_value;
    private float mana_cap_value;
    private float text_mana_value;
    private float mana_pool_width = 340;
    private float mana_pool_height = 20;
    private float mana_height = 20;
    private boolean gameOver = false;
    ArrayList<ArrayList<Integer>> shrub_pos_array;
    ArrayList<ArrayList<Integer>> path_pos_array;
    ArrayList<ArrayList<Integer>> path0_pos_array;
    ArrayList<ArrayList<Integer>> path1_pos_array;
    ArrayList<ArrayList<Integer>> path2_pos_array;
    ArrayList<ArrayList<Integer>> path3_pos_array;
    ArrayList<ArrayList<Integer>> degree_90_path0; // 90 degree of path0
    ArrayList<ArrayList<Integer>> degree_90_path1; // 90 degree of path1
    ArrayList<ArrayList<Integer>> degree_270_path1; // 180 degree of path1
    ArrayList<ArrayList<Integer>> degree_90_path2; // 90 degree of path2
    ArrayList<ArrayList<Integer>> degree_180_path2; // 180 degree of path2
    ArrayList<ArrayList<Integer>> wizard_pos_array;

    // monster properties
    private int wave_num = 0;
    private int wave_cap;
    private float frameCountSum = 0;
    private int monster_num = 0;
    private ArrayList<Integer> duration = new ArrayList<Integer>();
    private ArrayList<Float> pre_wave_pause = new ArrayList<Float>();
    private ArrayList<Integer> wave_quantity = new ArrayList<Integer>();
    private int waveRate;
    private boolean duration_flag = true;
    private int totalFrames = 0;
    private int framesRemaining = 0;
    private int deathFrame;
    private ArrayList<Monsters> monsters_array = new ArrayList<Monsters>();
    private float mana_gain_multiplier = 1;
    private float waveCount = 0;
    private float spwanCount = 0;

    // tower properties
    private float gameSpeed = 1;
    private ArrayList<Tower> tower_array = new ArrayList<Tower>();

    // mana properties
    private boolean display_spell_cost = false;

    // call the class
    public Gain_json gain_json;
    public Mana calMana;
    public Initial_map initial_map;
    public Monsters monsters;
    public Tower tower;
    public Button button;
    public ffButton ffButton;
    public pauseButton pauseButton;
    public towerButton towerButton;
    public rangeButton rangeButton;
    public speedButton speedButton;
    public damageButton damageButton;
    public manaButton manaButton;
    PImage grass_img;
    PImage shrub_img;
    PImage path0_img;
    PImage path1_img;
    PImage path2_img;
    PImage path3_img;
    PImage wizard_img;
    PImage gremlin_img;
    PImage death1_img;
    PImage death2_img;
    PImage death3_img;
    PImage death4_img;
    PImage death5_img;
    PImage tower0_img;
    PImage tower1_img;
    PImage tower2_img;
    PImage fireBall_img;
	
	// Feel free to add any additional methods or attributes you want. Please put classes in different files.

    public App() {
        this.configPath = "config.json";
        this.gain_json = new Gain_json(this, this.configPath);
        this.calMana = new Mana(mana_pool_width, this.gain_json, FPS);
        this.initial_map = new Initial_map("level1.txt");
        this.ffButton = new ffButton(this, 645, 50, 45, 45, "FF", "2x speed", 132, 115, 74); // set the ff button
        this.pauseButton = new pauseButton(this, 645, 105, 45, 45, "P", "PAUSE", 132, 115, 74); // set the pause button
        this.towerButton = new towerButton(this, this.calMana, gameSpeed, 645, 160, 45, 45, "T", "Build\nTower", 132, 115, 74); // set the tower button
        this.rangeButton = new rangeButton(this, this.calMana, 645, 215, 45, 45, "U1", "Upgrade\nRange", 132, 115, 74); // set the range button
        this.speedButton = new speedButton(this, this.calMana, 645, 270, 45, 45, "U2", "Upgrade\nSpeed", 132, 115, 74); // set the speed button
        this.damageButton = new damageButton(this, this.calMana, 645, 325, 45, 45, "U3", "Upgrade\nDamage", 132, 115, 74); // set the damage button
        this.manaButton = new manaButton(this, this.calMana, mana_gain_multiplier, 645, 380, 45, 45, "M", "Mana\npool", 132, 115, 74); // set the mana button
    }

    /**
     * Initialise the setting of the window size.
     */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
    public void setup() {
        
        frameRate(FPS);

        // set the initial properties of the mana
        calMana.setManaCap();
        calMana.setManaValue();
        calMana.setManaGainSpeed();

        // load the image
        this.grass_img = loadImage("src\\main\\resources\\WizardTD\\grass.png");
        this.shrub_img = loadImage("src\\main\\resources\\WizardTD\\shrub.png");
        this.wizard_img = loadImage("src\\main\\resources\\WizardTD\\wizard_house.png");
        this.path0_img = loadImage("src\\main\\resources\\WizardTD\\path0.png");
        this.path1_img = loadImage("src\\main\\resources\\WizardTD\\path1.png");
        this.path2_img = loadImage("src\\main\\resources\\WizardTD\\path2.png");
        this.path3_img = loadImage("src\\main\\resources\\WizardTD\\path3.png");
        this.gremlin_img = loadImage("src\\main\\resources\\WizardTD\\gremlin.png");
        this.death1_img = loadImage("src\\main\\resources\\WizardTD\\gremlin1.png");
        this.death2_img = loadImage("src\\main\\resources\\WizardTD\\gremlin2.png");
        this.death3_img = loadImage("src\\main\\resources\\WizardTD\\gremlin3.png");
        this.death4_img = loadImage("src\\main\\resources\\WizardTD\\gremlin4.png");
        this.death5_img = loadImage("src\\main\\resources\\WizardTD\\gremlin5.png");
        this.tower0_img = loadImage("src\\main\\resources\\WizardTD\\tower0.png");
        this.tower1_img = loadImage("src\\main\\resources\\WizardTD\\tower1.png");
        this.tower2_img = loadImage("src\\main\\resources\\WizardTD\\tower2.png");
        this.fireBall_img = loadImage("src\\main\\resources\\WizardTD\\fireball.png");

        // load the initial map
        initial_map.readFile();

        // get the position of the shrub
        shrub_pos_array = initial_map.getShrubPosArray();

        // get the position of the path
        initial_map.select_path();
        path0_pos_array = initial_map.getPath0PosArray();
        path1_pos_array = initial_map.getPath1PosArray();
        path2_pos_array = initial_map.getPath2PosArray();
        path3_pos_array = initial_map.getPath3PosArray();
        degree_90_path0 = initial_map.getDegree90Path0();
        degree_90_path1 = initial_map.getDegree90Path1();
        degree_270_path1 = initial_map.getDegree270Path1();
        degree_90_path2 = initial_map.getDegree90Path2();
        degree_180_path2 = initial_map.getDegree180Path2();

        // get the position of the wizard
        wizard_pos_array = initial_map.getWizardPosArray();

        // set the initial mana value
        mana_value = calMana.getManaValue();
        mana_cap_value = calMana.getManaCap();

        // get the waveCap for wave
        Monsters set_monster = new Monsters(gain_json, wave_num, initial_map);
        wave_cap = set_monster.getWaveCap();

        // set the property for wave
        for (int i=0; i < wave_cap; i++){
            Monsters init_monster = new Monsters(gain_json, i, initial_map);
            // set duration
            duration.add(init_monster.getWaveDuration());
            // set pre_wave_pause
            pre_wave_pause.add(init_monster.getPreWavePause());
            // set quantity
            init_monster.setQuantity();
            wave_quantity.add(init_monster.getQuantity());
        }
    }

    /**
     * Draw all elements in the game by current frame.
     */
    public void draw() {
        background(132, 115, 74);
        drawManaPool();
        drawButton();
        drawWaveIndicator();
        drawGrass();
        drawShrub();
        drawPath();

        //System.out.println(frameRate);

        // set the frameCount

        waveCount += gameSpeed;
        spwanCount += gameSpeed;

        // set different wave
        if (wave_num < wave_cap) {

            waveRate = (duration.get(wave_num) * FPS) / wave_quantity.get(wave_num);
            if (duration_flag) {
                frameCountSum += duration.get(wave_num) * FPS;
                duration_flag = false;
            }

            //System.out.println("Wave: " + wave_num + ", frameCount: " + frameCount);

            try{
                if ((spwanCount >= waveRate) && (monster_num < wave_quantity.get(wave_num))) {
                    Monsters monster = new Monsters(gain_json, wave_num, initial_map);
                    monster.setType();
                    monster.setHp();
                    monster.setSpeed();
                    monster.setArmour();
                    monster.setMana_gained_value();
                    monster.setQuantity();

                    // set the initial position for monster and find the path for monsters
                    int[] startPos = monster.findStartPos();
                    monster.getMoveStack(startPos);
                    monster.setMovingPath();

                    monsters_array.add(monster);
                    monster_num++;
                    spwanCount = 0;
                } else if (monster_num == wave_quantity.get(wave_num) && waveCount >= (frameCountSum+pre_wave_pause.get(wave_num+1) * FPS)) {
                    frameCountSum += pre_wave_pause.get(wave_num+1) * FPS;
                    wave_num++;
                    waveCount = 0;
                    frameCountSum = 0;
                    monster_num = 0;
                    duration_flag = true;
                }
            } catch (IndexOutOfBoundsException e) {
                {};
            }
        }

        for (int i = 0; i < monsters_array.size(); i++) {

            // check the death of monster
            if (!monsters_array.get(i).deathCheck()) {
                monsters_array.get(i).movement();
                // check the gameSpeed
                //System.out.println("gameSpeed: " + gameSpeed);
                monsters_array.get(i).changeSpeed(gameSpeed);

                drawMonster(monsters_array.get(i));

            } else {
                monsters_array.get(i).addDeadFrame();
                calMana.addUp(monsters_array.get(i).getMana_gained_value() * mana_gain_multiplier);
                drawDeath(monsters_array.get(i));
                monsters_array.remove(i);
            }

            // check the monster reach the wizard house
            try{
                if (monsters_array.get(i).reachWizard) {
                    //System.out.println("hp: " + monsters_array.get(i).getHp());
                    calMana.countDown(monsters_array.get(i).getHp());

                    //game over
                    if (calMana.getManaValue() <= 0) {
                        gameSpeed = 0;
                        gameOver = true;
                    }
                    monsters_array.remove(i);
                }
            }
            catch (IndexOutOfBoundsException e) {
                {};
            }
        }
        drawTower(monsters_array);
        drawWizard();
        drawSpellCost();

        // game over
        if (gameOver) {
            textSize(50);
            textAlign(CENTER, CENTER);
            text("GAME OVER", 320, 320);
        }
    }

    /**
     * draw the grass
     */
    public void drawGrass() {

        x = 0;
        y = 40;
        while (y < 680) {
            while (x < 640) {
            image(grass_img, x, y, CELLSIZE, CELLSIZE);
            x += CELLSIZE;
            }
            x = 0;
            y += CELLSIZE;
        }

    }

    /**
     * draw the shrub
     */
    public void drawShrub() {
        int line = 0;
        while (line < shrub_pos_array.size()) {

            x = shrub_pos_array.get(line).get(1);
            y = shrub_pos_array.get(line).get(0);
            image(shrub_img, x*CELLSIZE, 40+y*CELLSIZE, CELLSIZE, CELLSIZE);
            line++;
        }
    }

    /**
     * draw the path
     */
    public void drawPath() {

        // draw the path0
        line = 0;
        while (line < path0_pos_array.size()) {
            x = path0_pos_array.get(line).get(1);
            y = path0_pos_array.get(line).get(0);
            image(path0_img, x*CELLSIZE, 40+y*CELLSIZE, CELLSIZE, CELLSIZE);
            line++;
        }

        // draw the path1
        line = 0;
        while (line < path1_pos_array.size()) {
            x = path1_pos_array.get(line).get(1);
            y = path1_pos_array.get(line).get(0);
            image(path1_img, x*CELLSIZE, 40+y*CELLSIZE, CELLSIZE, CELLSIZE);
            line++;
        }

        // draw the path2
        line = 0;
        while (line < path2_pos_array.size()) {
            x = path2_pos_array.get(line).get(1);
            y = path2_pos_array.get(line).get(0);
            image(path2_img, x*CELLSIZE, 40+y*CELLSIZE, CELLSIZE, CELLSIZE);
            line++;
        }

        // draw the path3
        line = 0;
        while (line < path3_pos_array.size()) {
            x = path3_pos_array.get(line).get(1);
            y = path3_pos_array.get(line).get(0);
            image(path3_img, x*CELLSIZE, 40+y*CELLSIZE, CELLSIZE, CELLSIZE);
            line++;
        }

        // draw degree_90_path0
        line = 0;
        while (line < degree_90_path0.size()) {
            x = degree_90_path0.get(line).get(1);
            y = degree_90_path0.get(line).get(0);

            pushMatrix();
            // Translate to the position where you want the CENTER of the image to be
            translate(x*CELLSIZE, 40+y*CELLSIZE); 
            // Rotate around the translated point
            rotate(PI/2); 
            // Draw the image centered on the translation point
            image(path0_img, 0, -CELLSIZE, CELLSIZE, CELLSIZE);
            popMatrix(); // Restore transformation state
            line++;
        }

        // draw degree_90_path1
        line = 0;
        while (line < degree_90_path1.size()) {
            x = degree_90_path1.get(line).get(1);
            y = degree_90_path1.get(line).get(0);

            pushMatrix();
            // Translate to the position where you want the CENTER of the image to be
            translate(x*CELLSIZE, 40+y*CELLSIZE); 
            // Rotate around the translated point
            rotate(PI/2); 
            // Draw the image centered on the translation point
            image(path1_img, 0, -CELLSIZE, CELLSIZE, CELLSIZE);
            popMatrix(); // Restore transformation state
            line++;
        }

        // draw degree_270_path1
        line = 0;
        while (line < degree_270_path1.size()) {
            x = degree_270_path1.get(line).get(1);
            y = degree_270_path1.get(line).get(0);

            pushMatrix();
            // Translate to the position where you want the CENTER of the image to be
            translate(x*CELLSIZE, 40+y*CELLSIZE); 
            // Rotate around the translated point
            rotate(3*PI/2);
            // Draw the image centered on the translation point
            image(path1_img, -CELLSIZE, 0, CELLSIZE, CELLSIZE);
            popMatrix(); // Restore transformation state
            line++;
        }

        // draw degree_90_path2
        line = 0;
        while (line < degree_90_path2.size()) {
            x = degree_90_path2.get(line).get(1);
            y = degree_90_path2.get(line).get(0);

            pushMatrix();
            // Translate to the position where you want the CENTER of the image to be
            translate(x*CELLSIZE, 40+y*CELLSIZE); 
            // Rotate around the translated point
            rotate(PI/2); 
            // Draw the image centered on the translation point
            image(path2_img, 0, -CELLSIZE, CELLSIZE, CELLSIZE);
            popMatrix(); // Restore transformation state
            line++;
        }

        // draw degree_180_path2
        line = 0;
        while (line < degree_180_path2.size()) {
            x = degree_180_path2.get(line).get(1);
            y = degree_180_path2.get(line).get(0);

            pushMatrix();
            // Translate to the position where you want the CENTER of the image to be
            translate(x*CELLSIZE, 40+y*CELLSIZE); 
            // Rotate around the translated point
            rotate(PI); 
            // Draw the image centered on the translation point
            image(path2_img, -CELLSIZE, -CELLSIZE, CELLSIZE, CELLSIZE);
            popMatrix(); // Restore transformation state
            line++;
        }
    }

    /**
     * draw the Wizard house
     */
    public void drawWizard() {
        // draw the wizard house
        x = wizard_pos_array.get(0).get(1);
        y = wizard_pos_array.get(0).get(0);
        float offsetX = (CELLSIZE-48)/2;
        float offsetY = (CELLSIZE-48)/2;

        image(wizard_img, x*CELLSIZE+offsetX, 40+y*CELLSIZE+offsetY, CELLSIZE+16, CELLSIZE+16);
    }

    /**
     * draw the monster
     */
    public void drawMonster(Monsters monster) {

        // get the position of the monster
        float[] monster_pos = monster.getPos();
        float x_monster = monster_pos[0];
        float y_monster = monster_pos[1];

        // draw the monster
        image(gremlin_img, x_monster+6, 40+y_monster+6);

        // draw the health bar
        float health_bar_width = 20;
        float health_bar_height = 2;
        noStroke();
        fill(255, 2, 2);
        rect(x_monster+6, 40+y_monster+6-5, health_bar_width, health_bar_height);

        // hp
        float hp = monster.getHp();
        float initHp = monster.getInitHp();
        float hp_width = (hp/initHp)*health_bar_width;
        float hp_height = 2;

        fill(2, 250, 19);
        rect(x_monster+6, 40+y_monster+6-5, hp_width, hp_height);


    }

    /**
     * draw the death anime of monster
     */
    public void drawDeath(Monsters monster) {
        // get the position of the monster
        float[] monster_pos = monster.getPos();
        float x_monster = monster_pos[0];
        float y_monster = monster_pos[1];

        deathFrame = monster.getDeadFrame();

        if (deathFrame == 1) {
            // draw the first frame of death
            image(death1_img, x_monster+6, 40+y_monster+6);
        } else if (deathFrame == 2) {
            // draw the second frame of death
            image(death2_img, x_monster+6, 40+y_monster+6);
        } else if (deathFrame == 3) {
            // draw the third frame of death
            image(death3_img, x_monster+6, 40+y_monster+6);
        } else if (deathFrame == 4) {
            // draw the fourth frame of death
            image(death4_img, x_monster+6, 40+y_monster+6);
        } else if (deathFrame == 5) {
            // draw the fifth frame of death
            image(death5_img, x_monster+6, 40+y_monster+6);
        }
    }

    /**
     * draw the ManaPool
     */
    public void drawManaPool() {
        noStroke();
        fill(255, 255, 255);
        rect(0, 40, 640, 640);

        // text
        fill(0);
        textSize(20);
        textAlign(CENTER, CENTER);
        text("MANA:", 340, 18);

        // draw the mana pool
        stroke(0);
        strokeWeight(2);
        fill(255,255,255);
        rect(380, 10, mana_pool_width, mana_pool_height);

        // change the mana value
        calMana.countUp(gameSpeed);
        mana_value = calMana.getManaValue();
        float mana_pool_value = calMana.getManaPoolValue();
        mana_cap_value = calMana.getManaCap();

        // draw the inital mana
        fill(0, 214, 214);
        rect(380, 10, mana_pool_value, mana_height);

        // draw the inital mana text
        fill(0);
        textAlign(CENTER, CENTER);
        textSize(20);
        // gain the text mana value
        text_mana_value = mana_value;
        text((int)(text_mana_value), 500, 18);
        text("/", 530, 18);
        text((int)(mana_cap_value), 560, 18);

    }

    /**
     * draw the ManaPool
     */

     public void drawWaveIndicator() {
        fill(0);
        textSize(25);
        textAlign(CENTER, CENTER);
        text("Wave: " + nf(wave_num+1, 0, 0), 100, 18);
        try {
            if (framesRemaining <= 0) {
                // Only calculate totalFrames when framesRemaining is 0 or less
                if (wave_num < duration.size()) {
                    totalFrames = (int) ((duration.get(wave_num) + pre_wave_pause.get(wave_num+1)) * FPS);
                    framesRemaining = totalFrames;
                }
            }
        
            if (framesRemaining > 0) {
                framesRemaining-=gameSpeed;
        
                // Calculate the time remaining in seconds
                int timeRemaining = framesRemaining / FPS;
        
                // Display the time remaining until the wave starts
                text("Start: " + nf(timeRemaining+1, 0, 0), 210, 18);
            }
        
        } catch (IndexOutOfBoundsException e) {
            {};
        }
     }

     /**
     * draw the button
     */
    public void drawButton() {

        ffButton.render();
        pauseButton.render();
        towerButton.render();
        rangeButton.render();
        speedButton.render();
        damageButton.render();
        manaButton.render();
    }

    /**
     * draw the tower
     */
    public void drawTower(ArrayList<Monsters> monsters) {
        // get the position of the monster
        for (Monsters monster : monsters) {
            float[] monster_pos = monster.getPos();
            float x_monster = monster_pos[0];
            float y_monster = monster_pos[1];
            tower_array = towerButton.getTowerArray();
            for (Tower tower : tower_array) {
                float[] tower_pos = tower.getPos();
                float x_tower = tower_pos[0];
                float y_tower = tower_pos[1];
                

                // draw the range of tower
                if (mouseX >= x_tower && mouseX <= x_tower + CELLSIZE && mouseY >= 40+y_tower && mouseY <= 40+y_tower + CELLSIZE) {
                    tower.displayRange();
                }

                // check the monster in the range of tower
                if (Math.ceil(Math.abs(Math.sqrt(Math.pow(6+x_monster-x_tower, 2)+Math.pow(46+y_monster-(40+y_tower), 2)))) <= tower.getRange()) {
                    monster.setInRange(true);
                    if (!tower.inRangeList.contains(monster)) {
                        tower.addInRangeList(monster);
                    }
                } else {
                    monster.setInRange(false);
                }
                // remove the monster out of the range of tower
                tower.removeInRangeList();
            }
        }
        
        // count up the inRangeFrame
        for (Tower tower : tower_array) {
            float[] tower_pos = tower.getPos();
            float x_tower = tower_pos[0];
            float y_tower = tower_pos[1];

            if (tower.rangeLevel == 1 && tower.damageLevel == 1 && tower.speedLevel == 1) {
                image(tower1_img, x_tower, 40+y_tower, CELLSIZE, CELLSIZE);
            } else if (tower.rangeLevel == 2 && tower.damageLevel == 2 && tower.speedLevel == 2) {
                image(tower2_img, x_tower, 40+y_tower, CELLSIZE, CELLSIZE);
            } else {
                image(tower0_img, x_tower, 40+y_tower, CELLSIZE, CELLSIZE);

                // display the upgrade range
                tower.upgradeRange();

                // display the upgrade damage
                tower.upgradeDamage();

                // display the upgrade speed
                tower.upgradeSpeed();
            }

            //System.out.println(tower.getStartFire());

            // set the start fire of tower
            tower.setStartFire();

            // reset the inRangeFrame
            if (!tower.getStartFire()) {
                tower.resetInRangeFrame();
            }

            // create the fireBall
            if (tower.getStartFire() && ((tower.getInRangeFrame() % ((int)(tower.getFireRate()*FPS))) == 0)) {
                
                //System.out.println(tower.getInRangeFrame());

                Random random = new Random();
                int upperbound = tower.inRangeList.size();
                int int_random = random.nextInt(upperbound);
                Monsters target = tower.inRangeList.get(int_random);
                fireBall fireBall = new fireBall(target, gameSpeed, x_tower, 40+y_tower);
                tower.fireBall_array.add(fireBall);
            }
            
            //System.out.println(tower.inRangeList.size());

            // draw the fireBall
            for (fireBall fireBall : tower.fireBall_array) {
                if (!fireBall.getIsHit() && !fireBall.getIsOut() && fireBall.alive()) {
                    float[] fireBall_pos = fireBall.getFireBallPos();
                    float fireBall_posX = fireBall_pos[0];
                    float fireBall_posY = fireBall_pos[1];
                    image(fireBall_img, fireBall_posX, fireBall_posY, 6, 6);
                    fireBall.changePos();
                }
            }
            tower.countUpInRangeFrame();
        }
    }

    /**
     * draw the tower
     */
    public void drawSpellCost() {
        if (display_spell_cost) {
            // draw the spell cost
            fill(255);
            stroke(0);
            strokeWeight(1);
            rect(manaButton.Pos.x-60, manaButton.Pos.y, 45, 20);

            // draw the spell cost text
            fill(0);
            textAlign(CENTER, CENTER);
            textSize(10);
            text("Cost:" + calMana.mana_spell_cost, manaButton.Pos.x-37, manaButton.Pos.y+10);
        }
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    public void keyPressed(){

        if (key == 'f' || key == 'F') {
            ffButton.keyPressed();
            gameSpeed = ffButton.ff(gameSpeed);
        } else if (key == 'p' || key == 'P') {
            pauseButton.keyPressed();
            gameSpeed = pauseButton.pause(gameSpeed);
        } else if (key == 'T' || key == 't') {
            towerButton.keyPressed();
        } else if (key == 'M' || key == 'm') {
            manaButton.keyPressed();
        } else if (key == '1') {
            rangeButton.keyPressed();
        } else if (key == '2') {
            speedButton.keyPressed();
        } else if (key == '3') {
            damageButton.keyPressed();
        }
        
    }

    /**
     * Receive key released signal from the keyboard.
     */
    public void keyReleased(){

    }

    public void mousePressed(MouseEvent e) {
        
        ffButton.update();
        gameSpeed = ffButton.ff(gameSpeed);
        pauseButton.update();
        gameSpeed = pauseButton.pause(gameSpeed);
        towerButton.update();
        towerButton.towerPlaced(initial_map.getPathPosArray(), initial_map.getShrubPosArray(), initial_map.getWizardPosArray());
        rangeButton.update();
        rangeButton.judgeTowerPos(tower_array);
        speedButton.update();
        speedButton.judgeTowerPos(tower_array);
        damageButton.update();
        damageButton.judgeTowerPos(tower_array);
        manaButton.update();
        mana_gain_multiplier = manaButton.upgradePool(); // upgrade the mana gain multiplier
        
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

        // hover the button
        ffButton.hover();
        pauseButton.hover();
        towerButton.hover();
        rangeButton.hover();
        speedButton.hover();
        damageButton.hover();
        manaButton.hover();
        display_spell_cost = manaButton.display_spell_cost();
    }

    /*@Override
    public void mouseDragged(MouseEvent e) {

    }*/

    public static void main(String[] args) {
        PApplet.main("WizardTD.App");
    }
}
