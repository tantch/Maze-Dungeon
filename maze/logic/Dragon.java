package maze.logic;

import java.util.Random;

/**
	 * The Class Dragon.
	 */
public class Dragon extends Entity {
	
	boolean slayn, sleeping, overSword, sleepable;
	
	int sleepFactor=30,wakeFactor=10;
	
	/**
	 * Instantiates a new dragon.
	 */
	public Dragon() {

		slayn = false;
		sleeping = false;
		overSword = false;
		token = 'D';
		move = true;
		sleepable = true;
	}

	
	public void die(Game gm) {
		slayn = true;
		move = false;
		gm.MAZE[y][x].char1 = gm.EM;
		gm.MAZE[y][x].trshToken1 = '%';
	}

	/**
	 * Moves the dragon 1 place randomly in the maze.
	 *
	 * @param gm the game
	 */
	public void move(Game gm) {
		Random rd = new Random();

		int dire = 0;
		int dx = 0, dy = 0;
		boolean moved = false;

		if (rd.nextInt(wakeFactor) == 0 && sleeping) {
			
				sleeping = false;
		}
		else if (rd.nextInt(sleepFactor)==0 && !sleeping){
				sleeping = true;
		}

		if (sleeping)
			return;

		Par[] acts = gm.near(x, y, Empty.class);

		if (move && acts.length != 0) {
			gm.MAZE[y][x].char1 = gm.EM;
			do {
				dire = rd.nextInt(4);
				switch (dire) {
				case 0:
					dx = 0;
					dy = 1;
					break;
				case 1:
					dx = 0;
					dy = -1;
					break;
				case 2:
					dx = 1;
					dy = 0;
					break;
				case 3:
					dx = -1;
					dy = 0;
					break;
				}

				moved = moveTo(x + dx, y + dy, gm);

			} while (!moved);
			if (dx < 0)
				dir = "left";
			if (dx > 0)
				dir = "right";
			if (dy < 0)
				dir = "up";
			if (dy > 0)
				dir = "down";
			gm.MAZE[y][x].char1 = this;
		}

	}

	/**
	 * Move the dragon a specified number of coordinates in the maze.
	 *
	 * @param tx the x-axis variation.
	 * @param ty the y-axis variation.
	 * @param gm the game.
	 * @return true, if successful
	 */
	public boolean moveTo(int tx, int ty, Game gm) {
	
		
		
		if (gm.unitEmpty(tx, ty)) {
			
			System.out.println(dir);
			x = tx;
			y = ty;
			return true;
		}
		return false;

	}

	/**
	 * Interacts the dragon with the rest of the maze.
	 *
	 * @param gm the game.
	 */
	
	public void interact(Game gm) {

		Par[] acts;

		if (slayn) {
			return;
		}

		if (gm.onTop(x, y, Sword.class, 3)) {
			overSword = true;
			token = 'F';
		}
		if (gm.onTop(x, y, Empty.class, 3)) {
			overSword = false;
			token = 'D';
		}

		acts = gm.near(x, y, Hero.class);
		if (acts.length != 0) {
			for (int i = 0; i < acts.length; i++) {
				Hero her = (Hero) gm.MAZE[acts[i].y][acts[i].x].char1;
				if (her.armed) {
					die(gm);
					return;
				}
			}
		}

	}

	/**
	 * Gets the slayn, which indicates if the dragon is either dead or not.
	 *
	 * @return true if slayned, false if not.
	 */
	public boolean getSlayn() {
		return slayn;
	}

	/**
	 * Gets the sleeping value.
	 *
	 * @return true if sleeping, false if not.
	 */
	public boolean getSleeping() {
		return sleeping;
	}

	/**
	 * Gets the overSword, which indicates if the dragon is over the Sword or not.
	 *
	 * @return true if dragon is in the same place as the sword, false if else.
	 */
	public boolean getoverSword() {
		return overSword;
	}

	/**
	 * Sets the sleeping.
	 *
	 * @param b the new sleeping value
	 */
	public void setSleeping(boolean b) {
		sleeping=b;
		
	}

}
