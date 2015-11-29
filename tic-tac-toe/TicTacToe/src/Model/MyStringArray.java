package Model;

public class MyStringArray {

	private String[] values;

	public MyStringArray(String[] values) {
		this.values = values;
	}

	public MyStringArray() {

	}

	public String[] getArrayByReference() {
		return values;
	}

	public boolean arrayContains(String valueToLookFor) {
		boolean result = false;
		for (int i = 0; i < values.length; i++) {
			if (values[i].equals(valueToLookFor) == true) {
				result = true;
				break;
			}
		}

		return result;
	}
}
