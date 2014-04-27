package maze.logic;

/**
 * The Class Hero.
 */
public class Hero extends Entity {

	boolean alive;
	
	boolean armed;
	
	boolean onExit;

	/**
	 * Instantiates a new hero.
	 */
	public Hero() {
		alive = true;
		armed = false;
		move = true;
		onExit=false;
		token = 'H';
		
	}

	/* (non-Javadoc)
	 * @see maze.logic.Entity#die(maze.logic.Game)
	 */
	@Override
	public void die(Game gm) {
		alive = false;
		move = false;
		gm.MAZE[y][x].char1 = gm.EM;
		gm.MAZE[y][x].trshToken1 = '*';
	}

	/* (non-Javadoc)
	 * @see maze.logic.Entity#interact(maze.logic.Game)
	 */
	public void interact(Game gm) {

		Par[] acts; 
		if (alive) {

			
			if(gm.onExit(x,y)){
				gm.MAZE[y][x].trshToken1 = 'S';
				 onExit=true;
				 return;
			}
			
			
			
			if (gm.onTop(x, y, Sword.class, 3)) {
				armed = true;
				token = 'A';
			}
			
			acts = gm.near(x, y, Dragon.class);
			if (acts.length != 0) {
				for (int i = 0; i < acts.length; i++) {
					Dragon Drag = (Dragon) gm.MAZE[acts[i].y][acts[i].x].char1;
					if (!Drag.sleeping && !Drag.slayn && !armed) {
						die(gm);
						return;
					}
				}
			}

			
		}
	}

	/**
	 * Moves the hero a specific number of coordinates.
	 *
	 * @param gm the game
	 * @param dx the x-axis variation
	 * @param dy the y-axis variation
	 */
	public void move(Game gm, int dx, int dy) {
		if (move && alive) {
			if(dx>0)
				dir="right";
			if(dx<0)
				dir="left";
			if(dy<0)
				dir="up";
			if(dy>0)
				dir="down";
			gm.MAZE[y][x].char1 = gm.EM;
			if (gm.unitEmpty(x + dx, y + dy)) {
				x = x + dx;
				y = y + dy;
			}
			else if(gm.MAZE[y+dy][x+dx].char1 instanceof Exit && gm.isPathClear()){
				x = x + dx;
				y = y + dy;
				
			}
			gm.MAZE[y][x].char1 = this;
		}

	}

	
	/**
	 * Gets the alive boolean value.
	 *
	 * @return the alive
	 */
	public boolean getAlive(){
		return alive;
	}
	
	/**
	 * Gets the armed boolean value.
	 *
	 * @return the armed
	 */
	public boolean getArmed(){
		return armed;
	}
	
	/**
	 * Gets the on exit boolean value.
	 *
	 * @return the on exit
	 */
	public boolean getOnExit(){
		return onExit;
	}
	
}
