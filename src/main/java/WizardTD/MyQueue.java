package WizardTD;
import java.util.*;

public class MyQueue {

    int n;
	Monsters[] queue_Array = new Monsters[]{};

	public boolean isEmpty() {

		boolean isEmpty = true;

		for (Monsters item: queue_Array) {
			if (item != null){
				isEmpty = false;
				break;
			}
		}

		return isEmpty;

	}

	public void clear() {
		queue_Array = new Monsters[]{};
	}

	public Monsters random() {

        Random rand = new Random();
        int upperBound = queue_Array.length;
        int randomIndex = rand.nextInt(upperBound);

		if (queue_Array.length > 0) {
			return queue_Array[randomIndex];
		}
		else{
			return null;
		}
	}

    public void dequeue() {

        if (queue_Array.length > 0) {
			
			n = queue_Array.length;
			Monsters[] temp_arr = new Monsters[n-1];

			for (int i = 0; i < n-1; i++){
				temp_arr[i] = queue_Array[i+1];
			}
			queue_Array = temp_arr;
		}
    }

    public void enqueue(Monsters elem) {

        if (queue_Array != null && elem != null) {
            
			n = queue_Array.length;
			Monsters[] temp_arr = new Monsters[n+1];

			for (int i = 0; i < n; i++){
				temp_arr[i] = queue_Array[i];
			}

			temp_arr[n] = elem;

			queue_Array = temp_arr;
        }
    }

    public int size() {
        return queue_Array.length;
    } 

    public boolean contains(Monsters elem) {
		
		boolean flag = false;
		for (int i = 0; i < queue_Array.length; i++) {
				if (queue_Array[i].equals(elem)) {
					flag = true;
                }
            }
		return flag;
    }
}
