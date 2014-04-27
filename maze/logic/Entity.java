package maze.logic;

import java.io.Serializable;

/**
 * The Class Entity.
 */
public abstract class Entity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	int x;
	
	int y;
	
	String dir="right";
	
	boolean move;
	
	char token;
	
	boolean invisible=false;
	
	/**
	 * Interacts the entity with the game and the other entities.
	 *
	 * @param gm the game which is being currently played
	 */
	public void interact(Game gm){
		
	}
	

	/**
	 * Tries to kill and entity, which is impossible.
	 *
	 * @param gm the game
	 */
	public void die(Game gm){
		System.out.println("tentativa de matar algo qe n tem vida");
	}
	
	/**
	 * Sets the move value.
	 *
	 * @param br the new move value
	 */
	public void setMove(boolean br){
		move=br;
	}
	
	/**
	 * Gets the x position of the entity.
	 *
	 * @return the x
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * Gets the y position of the entity.
	 *
	 * @return the y
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * Gets the move value.
	 *
	 * @return the move
	 */
	public boolean getMove(){
		return move;
	}
	
	/**
	 * Gets the invisible value, which indicates if the entity is either visible or not.
	 *
	 * @return the invisible
	 */
	public boolean getInvisible(){
		return invisible;
	}
	
	/**
	 * Gets the direction in which the entity moves.
	 *
	 * @return the dir
	 */
	public String getDir(){
		return dir;
	}
	
	/**
	 * Sets the x coordinate of the entity.
	 *
	 * @param a the new x
	 */
	public void setX(int a){
		x=a;
	}

	/**
	 * Sets the y coordinate of the entity.
	 *
	 * @param a the new y
	 */
	public void setY(int a){
		y=a;
	}
	
	/**
	 * Gets the token of the entity, it's representation in the map.
	 *
	 * @return the token
	 */
	public char getToken(){
		return token;
	}
	
	/**
	 * Sets the invisible.
	 *
	 * @param b the new invisible
	 */
	public void setInvisible(boolean b){
		invisible=b;
	}
}
