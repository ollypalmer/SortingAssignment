import java.util.ArrayList;
import java.util.Random;

/**
 * The bubbleSort class is is used to implement a Bubble Sorting process on an Array of random integers that are 
 * automatically generated by the algorithm, the class can be used to either animate the bubble sort process or
 * record the time taken to fully sort a single array. Runs as a single thread.
 * @author Oliver Palmer, stuNumber 12089466
 *
 */
public class bubbleSort implements Runnable {

	static Random r = new Random();
	static final int largestValue = 100;
	private int Array[];
	drawPanel draw;
	boolean running = false, animate = true;
	ArrayList<arrayStore> list;

	/**
	 * Constructor for bubbleSort
	 * @param i int, determine the size of the array to be sorted
	 * @param d drawPanel, the JPanel that the animation is draw on
	 * @param animate boolean, to determine whether the class is being accessed for animation or analysis
	 * @param l ArrayList, used to store the sort time and array size when performing analysis
	 */
	bubbleSort(int i, drawPanel d, boolean animate, ArrayList<arrayStore> l){
		Array = new int[i];
		draw = d;
		this.animate = animate;
		this.list = l;
	}

	/**
	 * Sort method, used to begin the sorting progress, records the time in nano seconds then divides by 1000 to convert
	 * the value to microseconds. If the class is initialised to analyse the data then this method records the timetaken and
	 * array size in the array list provided by the constructor
	 */
	public synchronized void sort(){

		running = true;
		fillArray(Array);

		long startTime = System.nanoTime();
		BubbleSort();
		long endTime = System.nanoTime();

		long timeTaken = (endTime - startTime) / 1000;

		if (animate == false) {
			this.list.add(new arrayStore(Array.length, timeTaken));
		}
		running = false;
	}

	/**
	 * BubbleSort method, steps through every item in the array and compares each adjacent item and swaps the values if the two items
	 * are in descending order, after one cycle of the first for loop the largest integer in the array will be at the end position, the
	 * process is then repeated over looking the items that have already been moved to the end of the array as they are considered sorted
	 */
	private void BubbleSort(){

		for (int i = 0; i < Array.length; i++){
			if (running == true){
				for(int j = 1; j < (Array.length - i); j++){
					if (Array[j-1] > Array[j]){
						swap(Array, j-1, j);

						// if required to animate, sends current state of array to drawPanel
						if (animate == true) {
							draw.draw(Array);
							// sleep statement required to make the animation run at a visible speed
							try{ Thread.sleep(1); } catch (InterruptedException e) { }
						}
					}
				}
			}
		}


	}

	/**
	 * Swap method used to swap two values in the array
	 * @param a the array containing the values to be swapped
	 * @param x the index of one of the values to be swapped
	 * @param y the index of the other value to be swapped
	 */
	private void swap(int[]a, int x, int y){
		int temp = a[x];
		a[x] = a[y];
		a[y] = temp;
	}

	/**
	 * fillArray method fills the classes array with random numbers ranging from 0 to 100
	 * @param a the array to fill
	 */
	private void fillArray (int[] a){
		for (int i = 0; i < a.length; i++){
			a[i] = r.nextInt(largestValue);
		}
	}

	/**
	 * run method, used for running the class as a thread
	 */
	@Override
	public void run(){
		sort();
	}

	/**
	 * Stop method, changes the boolean value running to false so the code will halt when the value is next checked
	 */
	public void stop(){
		running = false;
	}

}

