package WizardTD;
import java.util.*;
import java.io.*;

public class Initial_map {
    
    String path;
    ArrayList<ArrayList<Integer>> shrub_pos_array = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> path_pos_array = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> path0_pos_array = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> path1_pos_array = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> path2_pos_array = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> path3_pos_array = new ArrayList<ArrayList<Integer>>();
    // 90 degree of path0
    ArrayList<ArrayList<Integer>> degree_90_path0 = new ArrayList<ArrayList<Integer>>();
    // 90 degree of path1
    ArrayList<ArrayList<Integer>> degree_90_path1 = new ArrayList<ArrayList<Integer>>();
    // 180 degree of path1
    ArrayList<ArrayList<Integer>> degree_270_path1 = new ArrayList<ArrayList<Integer>>();
    // 90 degree of path2
    ArrayList<ArrayList<Integer>> degree_90_path2 = new ArrayList<ArrayList<Integer>>();
    // 180 degree of path2
    ArrayList<ArrayList<Integer>> degree_180_path2 = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> wizard_pos_array = new ArrayList<ArrayList<Integer>>();
    MyHashSet myHashSet = new MyHashSet();

    Initial_map(String path) {
        
        this.path = path;
    }

    // get the pos of all element in map
    List<List<Character>> mapList = new ArrayList<>();

    List<List<Character>> GetMapPos() {
        File file = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                List<Character> charList = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    charList.add(c);
                }
                mapList.add(charList);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapList;
    }

    // for shrub and wizard house
    void readFile() {

        Integer count = 0;
        Integer line = 0;

        try {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));
            
            try {
                char element;
                while (br.ready()) {

                    // read the file
                    element = (char)br.read();
                    
                    // skip the newline and carriage return characters
                    if (element == '\n' || element == '\r') {
                        continue;
                    }

                    // create an array to store the position of the shrub
                    ArrayList<Integer> pos_array = new ArrayList<Integer>();
                    if (element == 'S') {
                        pos_array.add(line);
                        pos_array.add(count);
                        shrub_pos_array.add(pos_array);
                    }else if (element == 'X') {
                        pos_array.add(line);
                        pos_array.add(count);
                        path_pos_array.add(pos_array);
                        myHashSet.add(pos_array);
                    } else if (element == 'W') {
                        pos_array.add(line);
                        pos_array.add(count);
                        wizard_pos_array.add(pos_array);
                    }
                    count++;
                    if (count%20 == 0) {
                        count = 0;
                        line++;
                    }
                }
                br.close();
            }
            catch (IOException e) {
                System.out.println("Error reading file.");
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("No such file exists.");
        }
    }

    // for path
    void select_path() {

        for (int i = 0; i < path_pos_array.size(); i++) {

            // get the x and y
            int x = path_pos_array.get(i).get(1);
            int y = path_pos_array.get(i).get(0);
            boolean up = false;
            boolean down = false;
            boolean left = false;
            boolean right = false;
            ArrayList<Integer> myArray = new ArrayList<Integer>();

            // judge upward
            myArray.add(y-1);
            myArray.add(x);
            if (myHashSet.contains(myArray)) {
                up = true;
            }
            myArray.clear();

            // judge downward
            myArray.add(y+1);
            myArray.add(x);
            if (myHashSet.contains(myArray)) {
                down = true;
            }
            myArray.clear();

            // judge leftward
            myArray.add(y);
            myArray.add(x-1);
            if (myHashSet.contains(myArray)) {
                left = true;
            }
            myArray.clear();

            // judge rightward
            myArray.add(y);
            myArray.add(x+1);
            if (myHashSet.contains(myArray)) {
                right = true;
            }
            myArray.clear();

            // for path0, path1, path2, path3
            if(up == false && down == false) {
                path0_pos_array.add(path_pos_array.get(i));
            } else if (up == false && right == false) {
                path1_pos_array.add(path_pos_array.get(i));
            } else if (up == false && (down && left && right)) {
                path2_pos_array.add(path_pos_array.get(i));
            } else if (up && down && left && right) {
                path3_pos_array.add(path_pos_array.get(i));
            } else if (left == false && right == false) {
                degree_90_path0.add(path_pos_array.get(i));
            } else if (down == false && right == false) {
                degree_90_path1.add(path_pos_array.get(i));
            } else if (up == false && left == false) {
                degree_270_path1.add(path_pos_array.get(i));
            } else if (right == false && (up && down && left)) {
                degree_90_path2.add(path_pos_array.get(i));
            } else if (down == false && (up && left && right)) {
                degree_180_path2.add(path_pos_array.get(i));
            }
        }

    }

    ArrayList<ArrayList<Integer>> getShrubPosArray() {
        return shrub_pos_array;
    }

    ArrayList<ArrayList<Integer>> getPathPosArray() {
        return path_pos_array;
    }

    ArrayList<ArrayList<Integer>> getPath0PosArray() {
        return path0_pos_array;
    }

    ArrayList<ArrayList<Integer>> getPath1PosArray() {
        return path1_pos_array;
    }

    ArrayList<ArrayList<Integer>> getPath2PosArray() {
        return path2_pos_array;
    }

    ArrayList<ArrayList<Integer>> getPath3PosArray() {
        return path3_pos_array;
    }

    ArrayList<ArrayList<Integer>> getDegree90Path0() {
        return degree_90_path0;
    }

    ArrayList<ArrayList<Integer>> getDegree90Path1() {
        return degree_90_path1;
    }

    ArrayList<ArrayList<Integer>> getDegree270Path1() {
        return degree_270_path1;
    }

    ArrayList<ArrayList<Integer>> getDegree90Path2() {
        return degree_90_path2;
    }

    ArrayList<ArrayList<Integer>> getDegree180Path2() {
        return degree_180_path2;
    }

    ArrayList<ArrayList<Integer>> getWizardPosArray() {
        return wizard_pos_array;
    }
}
