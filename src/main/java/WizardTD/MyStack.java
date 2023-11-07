package WizardTD;

import java.util.Arrays;

public class MyStack {
    int n;
	int[][] stack_Array = new int[][]{};

	public boolean isEmpty() {

		boolean isEmpty = true;

		for (int[] item: stack_Array) {
			if (item != null){
				isEmpty = false;
				break;
			}
		}

		return isEmpty;

	}

	public void clear() {
		stack_Array = new int[][]{};
	}

    public void pop() {

        if (stack_Array.length > 0) {
			
			n = stack_Array.length;
			int[][] temp_arr = new int[n-1][];

			for (int i = 0; i < n-1; i++){
				temp_arr[i] = stack_Array[i];
			}
			stack_Array = temp_arr;
			return;
		}
		else{
			return;
		}
        
    }

    public void push(int[] elem) {

        if (stack_Array != null && elem != null) {
            
			n = stack_Array.length;
			int[][] temp_arr = new int[n+1][];

			for (int i = 0; i < n; i++){
				temp_arr[i] = stack_Array[i];
			}

			temp_arr[n] = elem;

			stack_Array = temp_arr;
        }
    }

    public int size() {
        return stack_Array.length;
    } 

    public boolean contains(int[] elem) {
		
		for (int[] item : stack_Array) {
			if (Arrays.equals(item, elem)) {
				return true;
			}
		}
		return false;
	}

	public void printStack() {

		for (int[]item : stack_Array) {
			System.out.println(item[0] + " " + item[1]);
		}
	}

}
