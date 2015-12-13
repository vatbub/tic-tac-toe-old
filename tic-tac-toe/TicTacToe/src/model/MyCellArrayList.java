package model;

import java.util.ArrayList;
import java.util.Collection;

public class MyCellArrayList extends ArrayList<int[]> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2047721369005434318L;

	public MyCellArrayList() {
		super();
	}

	public MyCellArrayList(int initialCapacity) {
		super(initialCapacity);
	}

	public MyCellArrayList(Collection<int[]> c) {
		super(c);
	}

	public boolean contains(int[] anIntArray) {
		if (this.size() != 0) {
			for (int el = 0; el < this.size(); el++) {
				if (this.get(el).length == anIntArray.length) {
					// Arrays are of equal length
					boolean areEqual = true;
					for (int i = 0; i < this.get(el).length; i++) {
						if (this.get(el)[i] != anIntArray[i]) {
							areEqual = false;
						}
					}
					if (areEqual == true) {
						return true;
					}
				}
			}

			// We only arrive here if no element matches
			return false;
		} else {
			// List has no elements
			return false;
		}
	}

	public void printList() {
		for (int el = 0; el < this.size(); el++) {
			String line = "";
			for (int i = 0; i < this.get(el).length; i++) {
				line = line + this.get(el)[i] + " ";
			}
			System.out.println(line);
		}

		System.out.println("===end of list===");
	}
}
