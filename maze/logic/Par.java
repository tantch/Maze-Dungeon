package maze.logic;

import java.io.Serializable;

/**
 * The Class Par.
 */
public class Par implements Serializable{

	private static final long serialVersionUID = 1L;
	
	int x = 0, y = 0;

	/**
	 * Instantiates a new par.
	 *
	 * @param a the a value
	 * @param b the b value
	 */
	public Par(int a, int b) {

		x = a;
		y = b;

	}

	/**
	 * Gets the x value.
	 *
	 * @return the x
	 */
	public int getx() {

		return x;
	}

	/**
	 * Gets the y value.
	 *
	 * @return the y
	 */
	public int gety() {

		return y;
	}

}
