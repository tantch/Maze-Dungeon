package maze.logic;

import java.util.Stack;
/**
 * The Class Eagle.
 */
public class Eagle extends Entity {

	boolean flying, alive, onShoulder, withSword, onJorney, able;
	
	int retX, retY;
	
	Stack<Par> caminho = new Stack<Par>(), caminhoVolta = new Stack<Par>();

	/**
	 * Instantiates a new eagle, which will be with the Hero
	 */
	public Eagle() {
		flying = false;
		alive = true;
		onShoulder = true;
		onJorney = false;
		able = true;
		token = 'v';
	}

	/**
	 * Kills the eagle.
	 *
	 * @param gm the game which is being played
	 */
	public void die(Game gm) {
		alive = false;
		move = false;
		onJorney = false;
		onShoulder = false;
		if (withSword) {
			gm.SW.invisible = false;
		}
		gm.MAZE[y][x].char2 = gm.EM;
		gm.MAZE[y][x].trshToken2 = '!';

	}
	/**
	 * @see maze.logic.Entity#interact(Game gm)
	 */

	
	public void interact(Game gm) {

		Par[] acts;
		if (!alive || flying || onShoulder) {
			return;
		}

		acts = gm.near(x, y, Dragon.class);
		if (acts.length != 0) {
			for (int i = 0; i < acts.length; i++) {
				Dragon drag = (Dragon) gm.MAZE[acts[i].y][acts[i].x].char1;
				if (!drag.sleeping && !drag.slayn) {
					die(gm);
					return;
				}
			}
		}
		
		if(gm.onTop(x,y,Dragon.class,1)){
			Dragon drag = (Dragon) gm.MAZE[y][x].char1;
			if (!drag.sleeping && !drag.slayn) {
				die(gm);
				return;
			}
			
		}

		if (gm.onTop(x, y, Sword.class, 3)) {
			withSword = true;
			token = 'V';
		}
		if (gm.onTop(x, y, Hero.class, 1)) {
			onShoulder = true;
			flying = false;
			onJorney = false;
			withSword = false;

		}

	}

	/**
	 * Creates the path the eagle will make saving it in the Stacks
	 *
	 * @param ex the x coordenate
	 * @param ey the y coordenate
	 */
	public void createPath(int ex, int ey) {

		int tx = x, ty = y;
		retX = x;
		retY = y;
		int dx = Math.abs(x - ex);
		int dy = Math.abs(y - ey);

		int up, right, spare, sprused = 0;
		boolean hor;
		int extra, normal, bigger;

		caminhoVolta.push(new Par(x, y));
		if (dx == 0 || dy == 0) {
			System.out.println("simples");
			if (dx >= dy) {
				bigger = dx;
				hor = true;
			} else {
				bigger = dy;
				hor = false;
			}

			if (x < ex)
				right = 1;
			else
				right = -1;

			if (y > ey)
				up = -1;
			else
				up = 1;

			for (int i = 0; i < bigger; i++) {
				if (hor)
					tx = tx + right;
				else
					ty = ty + up;
				caminhoVolta.push(new Par(tx, ty));
			}

		}

		if (dx != 0 && dy != 0) {
			System.out.println("diagonal");
			if (x < ex)
				right = 1;
			else
				right = -1;

			if (y > ey)
				up = -1;
			else
				up = 1;

			if (dx >= dy) {
				normal = dy;
				spare = dx % dy;
				extra = (dx / dy) - 1;
				bigger = dx;
				hor = true;
			} else {
				normal = dx;
				spare = dy % dx;
				extra = (dy / dx) - 1;
				bigger = dy;

				hor = false;
			}

			for (int i = 0; i < normal; i++) {

				tx = tx + right;
				ty = ty + up;
				caminhoVolta.push(new Par(tx, ty));
				for (int j = 0; j < extra; j++) {
					if (hor)
						tx = tx + right;
					else
						ty = ty + up;

					caminhoVolta.push(new Par(tx, ty));
				}

				if (spare != 0)

					if ((i + 1) % (normal / spare) == 0 && (spare > sprused)) {

						if (hor)
							tx = tx + right;
						else
							ty = ty + up;

						sprused++;
						caminhoVolta.push(new Par(tx, ty));

					}

			}

		}

		Stack<Par> tep = (Stack) caminhoVolta.clone();

		while (!tep.empty())
			caminho.push(tep.pop());

		caminhoVolta.pop();

		caminho.pop();

	}

	/**
	 * Moves the eagle in its quest for the sword.
	 *
	 * @param gm the game which is currently being played
	 * @param send true if eagle is being sent
	 */
	public void move(Game gm, boolean send) {

		if (able && send) {
			createPath(gm.SW.x, gm.SW.y);
			System.out.println("path created");
			flying = true;
			onShoulder = false;
			onJorney = true;
			able = false;
			return;
		}
		if (onJorney) {
			System.out.println("a voar");
			Par tp;
			gm.MAZE[y][x].char2 = gm.EM;
			if (!caminho.empty()) {
				tp = caminho.pop();
				if(tp.getx() - x >0)
					dir="right";
				else if(tp.getx() - x <0)
					dir="left";
				else if(tp.gety() - y >0)
					dir="down";
				else if(tp.gety() - y <0)
					dir="up";
				x = tp.getx();
				y = tp.gety();
				if (x == gm.SW.x && y == gm.SW.y) {
					flying = false;

				}
				gm.MAZE[y][x].char2 = this;
				return;
			}
			if (!caminhoVolta.empty()) {
				tp = caminhoVolta.pop();
				if(tp.getx() - x >0)
					dir="right";
				else if(tp.getx() - x <0)
					dir="left";
				else if(tp.gety() - y >0)
					dir="down";
				else if(tp.gety() - y <0)
					dir="up";
				x = tp.getx();
				y = tp.gety();
				flying = true;
				if (x == retX && y == retY) {
					flying = false;
					onJorney = false;
				}
				gm.MAZE[y][x].char2 = this;
				return;

			}

		}
		if (onShoulder) {
			gm.MAZE[y][x].char2 = gm.EM;
			x = gm.HR.x;
			y = gm.HR.y;
			gm.MAZE[y][x].char2 = this;
		}
	}

	/**
	 * Gets the flying value, which is true if the eagle is flying an false if else.
	 *
	 * @return flying True if eagle is in flight false if else
	 */
	public boolean getFlying(){
		return flying;
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
	 * Gets the onShoulder value, which verifies if the eagle is on the hero's shoulder.
	 *
	 * @return the OnShoulder
	 */
	public boolean getOnShoulder(){
		return onShoulder;
	}
	
	/**
	 * Gets the withSword, which indicates if the eagle is carrying the sword.
	 *
	 * @return the WithSword
	 */
	public boolean getWithSword(){
		return withSword;
	}
	
	/**
	 * Gets the onJourney, which indicates if the eagle is moving without the hero, to either pick up the sword or takint it to the hero.
	 *
	 * @return the onJourney
	 */
	public boolean getOnJourney(){
		return onJorney;
	}
	
	/**
	 * Gets the able boolean value, which checks if the eagle has not been disabled by the dragon.
	 *
	 * @return the able
	 */
	public boolean getAble(){
		return able;
	}
	
	
	
}
