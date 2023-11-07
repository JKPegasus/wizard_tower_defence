package WizardTD;
import java.util.*;
import processing.data.JSONArray;

public class Monsters {
    int CELLSIZE = 32;
    float Pos_x = -CELLSIZE;
    float origin_Y;
    float Pos_y;
    Gain_json gainJson;
    ArrayList<Object> property_array;
    JSONArray Monster_array;
    ArrayList<ArrayList<Integer>> pathArray;
    Initial_map initial_map;
    List<List<Character>> mapEleArray;
    ArrayList<ArrayList<Integer>> wizardPos;
    int[] wizardDoorPos;
    MyStack stack;
    MyStack moveStack;

    // initial the property for monster
    int waveNum;
    String type;
    float hp;
    float init_hp;
    float speed;
    float ffSpeed;
    float origin_speed;
    float armour;
    int mana_gained_value;
    int quantity;
    int deathFrame = 0;
    boolean inRange = false;
    boolean reachWizard = false;



    Monsters(Gain_json gainJson, int num, Initial_map initial_map) {
        this.gainJson = gainJson;
        this.waveNum = num;
        this.origin_Y = 3*CELLSIZE;
        this.Pos_y = origin_Y;
        this.initial_map = initial_map;
        initial_map.readFile();
        this.wizardPos = initial_map.getWizardPosArray();
        this.wizardDoorPos = new int[]{wizardPos.get(0).get(0), wizardPos.get(0).get(1)+1};
        this.mapEleArray = initial_map.GetMapPos(); // get the pos of all element
        this.stack = new MyStack();
        this.moveStack = new MyStack();
    }


    ArrayList<Object> gain(ArrayList<String> property) {
        ArrayList<String> array = new ArrayList<String>();
        array.add(property.get(0));
        if (property.size() == 2){
            array.add(property.get(1));
            property_array = (ArrayList<Object>) gainJson.gain_waves_method(array);
        } else if (property.size() == 3) {
            // add the monster to gain the property for the monster
            array.add(property.get(1));
            array.add(property.get(2));
            property_array = (ArrayList<Object>) gainJson.gain_waves_method(array);
        }
        return property_array;
    }

    void setType() {

        ArrayList<String> array = new ArrayList<String>();
        array.add("waves");
        array.add("monsters");
        array.add("type");
        this.type = (String) gain(array).get(waveNum);
    }

    String getType() {
        return type;
    }

    void setHp() {
        ArrayList<String> array = new ArrayList<String>();
        array.add("waves");
        array.add("monsters");
        array.add("hp");
        this.hp = ((Integer) gain(array).get(waveNum)).floatValue();
        this.init_hp = this.hp;
    }

    float getHp() {
        return hp;
    }

    float getInitHp() {
        return init_hp;
    }

    void setSpeed() {
        ArrayList<String> array = new ArrayList<String>();
        array.add("waves");
        array.add("monsters");
        array.add("speed");
        Object temp = gain(array).get(waveNum);
        if (temp instanceof Integer) {
            this.speed = ((Integer) gain(array).get(waveNum)).floatValue();
            this.ffSpeed = ((Integer) gain(array).get(waveNum)).floatValue()*2;
            this.origin_speed = ((Integer) gain(array).get(waveNum)).floatValue();
        } else if (temp instanceof Double) {
            this.speed = ((Double) gain(array).get(waveNum)).floatValue();
            this.ffSpeed = ((Double) gain(array).get(waveNum)).floatValue()*2;
            this.origin_speed = ((Double) gain(array).get(waveNum)).floatValue();
        }
    }

    void changeSpeed(float gameSpeed) {
        
        if (gameSpeed == 2) {
            this.speed = this.ffSpeed;
        } else if (gameSpeed == 0) {
            this.speed = 0;
        } else {
            this.speed = this.origin_speed;
        }
    }

    void setArmour() {
        ArrayList<String> array = new ArrayList<String>();
        array.add("waves");
        array.add("monsters");
        array.add("armour");
        this.armour = ((Double) gain(array).get(waveNum)).floatValue();
    }

    float getArmour() {
        return armour;
    }

    void setMana_gained_value() {
        ArrayList<String> array = new ArrayList<String>();
        array.add("waves");
        array.add("monsters");
        array.add("mana_gained_on_kill");
        this.mana_gained_value = (Integer) gain(array).get(waveNum);
    }

    int getMana_gained_value() {
        return mana_gained_value;
    }

    void setQuantity() {
        ArrayList<String> array = new ArrayList<String>();
        array.add("waves");
        array.add("monsters");
        array.add("quantity");
        this.quantity = (Integer) gain(array).get(waveNum);
    }

    int getQuantity() {
        return quantity;
    }

    int getWaveDuration() {
        ArrayList<String> array = new ArrayList<String>();
        array.add("waves");
        array.add("duration");
        int duration = (Integer) gain(array).get(waveNum);
        return duration;
    }

    float getPreWavePause() {
        ArrayList<String> array = new ArrayList<String>();
        array.add("waves");
        array.add("pre_wave_pause");
        Object temp = gain(array).get(waveNum);
        float pre_wave_pause = 0;
        if (temp instanceof Integer) {
            pre_wave_pause = ((Integer) gain(array).get(waveNum)).floatValue();
        } else if (temp instanceof Double) {
            pre_wave_pause = ((Double) gain(array).get(waveNum)).floatValue();
        }
        return pre_wave_pause;
    }

    int getWaveCap() {
        ArrayList<String> array = new ArrayList<String>();
        array.add("waves");
        array.add("duration");
        int wave_cap = gain(array).size();
        return wave_cap;
    }

    boolean upward(int[] pos) {
        try{
            
            if (mapEleArray.get(pos[0]-1).get(pos[1]) == 'X' && !stack.contains(new int[]{pos[0]-1, pos[1]})) {
                return true;
            } else {
                return false;
            }
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    boolean downward(int[] pos) {
        try{
            if (mapEleArray.get(pos[0]+1).get(pos[1]) == 'X' && !stack.contains(new int[]{pos[0]+1, pos[1]})) {
                return true;
            } else {
                return false;
            }
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    boolean leftward(int[] pos) {
        try{
            if (mapEleArray.get(pos[0]).get(pos[1]-1) == 'X' && !stack.contains(new int[]{pos[0], pos[1]-1})) {
                return true;
            } else {
                return false;
            }
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    boolean rightward(int[] pos) {
        try{
            if (mapEleArray.get(pos[0]).get(pos[1]+1) == 'X' && !stack.contains(new int[]{pos[0], pos[1]+1})) {
                return true;
            } else {
                return false;
            }
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    int[] findStartPos() {
        
        // loop the mapEleArray find the start position
        for (int row = 0; row < mapEleArray.size(); row++) {
            for (int col = 0; col < mapEleArray.get(row).size(); col++) {

                if (mapEleArray.get(row).get(col) == 'X') {
                    int[] startPos = new int[]{row, col};
                    return startPos;
                }
            }
        }
        return new int[]{};

    }
    
    // boolean getMoveStack(int[] pos) {

    //     boolean up = upward(pos);
    //     boolean down = downward(pos);
    //     boolean left = leftward(pos);
    //     boolean right = rightward(pos);
        
    //     if (Arrays.equals(pos, wizardDoorPos)) {
    //         stack.push(new int[]{pos[0], pos[1]});
    //         stack.push(new int[]{wizardPos.get(0).get(0), wizardPos.get(0).get(1)});
    //         moveStack = stack;
    //         return true;
    //     }
    //     if (right) {
    //         stack.push(new int[]{pos[0], pos[1]});
    //         right = getMoveStack(new int[]{pos[0], pos[1]+1});
    //         if (!right) {
    //             stack.pop();
    //         }
    //     }
    //     if (left) {
    //         stack.push(new int[]{pos[0], pos[1]});
    //         left = getMoveStack(new int[]{pos[0], pos[1]-1});
    //         if (!left) {
    //             stack.pop();
    //         }
    //     }
    //     if (up) {
    //         stack.push(new int[]{pos[0], pos[1]});
    //         up = getMoveStack(new int[]{pos[0]-1, pos[1]});
    //         if (!up) {
    //             stack.pop();
    //         }
    //     }
    //     if (down) {
    //         stack.push(new int[]{pos[0], pos[1]});
    //         down = getMoveStack(new int[]{pos[0]+1, pos[1]});
    //         if (!down) {
    //             stack.pop();
    //         }
    //     }
    //     if (!up && !down && !left && !right) {
    //         return false;
    //     }

    //     return true;


    // }

    boolean getMoveStack(int[] pos) {
        stack.push(new int[]{pos[0], pos[1]});
        if (Arrays.equals(pos, wizardDoorPos)) 
        {
            stack.push(new int[]{wizardPos.get(0).get(0), wizardPos.get(0).get(1)});
            moveStack = stack;
            return true;
        }
        int[] d = {1, 0, -1, 0, 1};
        
        for (int i = 0; i < 4; ++i)
        {
            int tx = pos[0] + d[i];
            int ty = pos[1] + d[i + 1];
            //System.out.println(tx + " " + ty);
            if (tx < 0 || ty < 0 || tx >=  mapEleArray.size() || ty >= mapEleArray.get(0).size()) continue;
            if (mapEleArray.get(tx).get(ty) == 'X' && !stack.contains(new int[]{tx, ty})) {
                //System.out.println("a");
                if (getMoveStack(new int[]{tx, ty}))
                {
                    return true;
                }
            } 
        }
        stack.pop();
        return false;

    }

    void addDeadFrame() {
        this.deathFrame += 1;
    }

    int getDeadFrame() {
        return deathFrame;
    }

    // get the path for the monster
    ArrayList<ArrayList<Integer>> posPath = new ArrayList<>();
    void setMovingPath() {

        for (int[]item : stack.stack_Array) {
			int row = item[0];
            int col = item[1];
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(row);
            temp.add(col);
            posPath.add(temp);
		}
    }

    // moving the monster
    void movement() {

        if (posPath.size() == 0) {
            reachWizard = true;
            return;
        }

        float row = posPath.get(0).get(0) * CELLSIZE;
        float col = posPath.get(0).get(1) * CELLSIZE;    

        float deltaX = col - Pos_x;
        float deltaY = row - Pos_y;
        float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    
        if (distance >= speed) {
            // Move directly towards the target by adding speed
            Pos_x += speed * (deltaX / distance);
            Pos_y += speed * (deltaY / distance);
        } else {
            // If the remaining distance is less than the speed, simply set the position to the target.
            Pos_x = col;
            Pos_y = row;
            posPath.remove(0);
            //System.out.println(posPath);
        }
    }


    float[] getPos() {

        float[] pos = {Pos_x, Pos_y};

        // System.out.println(Pos_x);
        // System.out.println(Pos_y);

        return pos;
    }

    boolean deathCheck() {
        if (this.hp <= 0) {
            return true;
        }
        return false;
    }

    void madeDmage(float dmage) {

        if (this.hp > 0) {
            this.hp -= armour*dmage;
        }
    }

    void setInRange(boolean flag) {
        this.inRange = flag;
    }

    boolean getInRange() {
        return inRange;
    }
}
