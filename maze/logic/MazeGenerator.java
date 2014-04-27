package maze.logic;

import java.io.Serializable;
import java.util.Random;
import java.util.Stack;

/**
 * The Class MazeGenerator.
 */
public class MazeGenerator implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	boolean saida, dragao, espada, heroi;
	
	Unvisited un;
	
	final int closedFactor = 40, turnCloseFactor =40,arranjeFactor = 40;

	/**
	 * Instantiates a new maze generator.
	 */
	public MazeGenerator() {
		saida = false;
		dragao = false;
		espada = false;
		heroi = false;
		un = new Unvisited();
	}

	/**
	 * Creates a Random Maze without characters with the dimensions saved in gm and saves it in gm.MAZE
	 *
	 * @param gm the game in which the created maze will be saved
	 */
	public void createMap(Game gm) {

		Random rd = new Random();

		int si = 0, sj = 0;

		for (int j = 0; j < gm.Height; j++) {
			for (int i = 0; i < gm.Width; i++) {
				if (i == 0 || j == 0 || i == gm.Width - 1 || j == gm.Height - 1) {
					gm.MAZE[j][i] = new Unit(gm.WL, gm);

				} else
					gm.MAZE[j][i] = new Unit(un, gm);
			}
		}
		do {
			switch (rd.nextInt(4)) {

			case 0:
				si = 0;
				sj = rd.nextInt(gm.Height);
				break;
			case 1:
				si = gm.Width - 1;
				sj = rd.nextInt(gm.Height);
				break;
			case 2:
				sj = 0;
				si = rd.nextInt(gm.Width);
				break;
			case 3:
				sj = gm.Height - 1;
				si = rd.nextInt(gm.Width);
				break;
			default:
				break;
			}
			if (camAberto(si, sj, gm)) {

				saida = true;

			}

		} while (!saida);
		// create path
		gm.MAZE[sj][si].char1 = gm.EX;
		gm.EX.x = si;
		gm.EX.y = sj;
		createPath(sj, si, gm);

	}

	private boolean camAberto(int tx, int ty, Game gm) {

		if (cpLim(ty - 1, tx, '.', gm)) {
			return true;
		}

		if (cpLim(ty + 1, tx, '.', gm)) {
			return true;
		}
		if (cpLim(ty, tx + 1, '.', gm)) {
			return true;
		}
		if (cpLim(ty, tx - 1, '.', gm)) {
			return true;
		}

		return false;
	}


	private boolean cpLim(int tj, int ti, char eq, Game gm) {

		if (tj >= 0 && tj < gm.Height && ti >= 0 && ti < gm.Width)
			if (gm.MAZE[tj][ti].char1.token == eq) {
				return true;
			}

		return false;

	}

	
	private void createPath(int sj, int si, Game gm) {

		Stack<Par> caminho = new Stack<Par>();

		int j = sj, i = si;
		Random rd = new Random();
		boolean moved = false;

		do {

			if (hasPath(j, i, gm)) {

				do {

					switch (rd.nextInt(4)) {

					case 0:

						if (cpLim(j - 1, i, '.', gm)) {

							if (cpLim(j + 1, i, '.', gm)
									&& !cpLim(j + 1, i - 1, '.', gm)
									&& !cpLim(j + 1, i + 1, '.', gm)
									&& !cpLim(j + 2, i, '.', gm)) {
								if (rd.nextInt(turnCloseFactor) != 0)
									gm.MAZE[j + 1][i].char1 = gm.WL;

							}

							if (cpLim(j, i - 1, '.', gm)
									&& !cpLim(j - 1, i - 1, '.', gm)
									&& !cpLim(j + 1, i - 1, '.', gm)
									&& !cpLim(j, i - 2, '.', gm)) {
								if (rd.nextInt(turnCloseFactor) != 0)
									gm.MAZE[j][i - 1].char1 = gm.WL;

							}
							if (cpLim(j, i + 1, '.', gm)
									&& !cpLim(j - 1, i + 1, '.', gm)
									&& !cpLim(j + 1, i + 1, '.', gm)
									&& !cpLim(j, i + 2, '.', gm)) {
								if (rd.nextInt(turnCloseFactor) != 0)
									gm.MAZE[j][i + 1].char1 = gm.WL;

							}

							gm.MAZE[j - 1][i].char1 = gm.EM;
							gm.MAZE[j - 1][i - 1].char1 = gm.WL;
							gm.MAZE[j - 1][i + 1].char1 = gm.WL;
							j = j - 1;

							caminho.push(new Par(i, j));
							moved = true;

							if (cpLim(j - 1, i, '.', gm)) {
								gm.MAZE[j - 1][i].char1 = gm.EM;
								if (cpLim(j - 1, i + 2, ' ', gm))
									if (rd.nextInt(closedFactor) != 0)
										gm.MAZE[j - 1][i + 1].char1 = gm.WL;
								if (cpLim(j - 1, i - 2, ' ', gm))
									if (rd.nextInt(closedFactor) != 0)
										gm.MAZE[j - 1][i - 1].char1 = gm.WL;

								j = j - 1;
								caminho.pop();
								caminho.push(new Par(i, j));

							}

						}
						break;
					case 1:
						if (cpLim(j + 1, i, '.', gm)) {

							if (cpLim(j - 1, i, '.', gm)
									&& !cpLim(j - 1, i - 1, '.', gm)
									&& !cpLim(j - 1, i + 1, '.', gm)
									&& !cpLim(j - 2, i, '.', gm)) {
								if (rd.nextInt(turnCloseFactor) != 0)
									gm.MAZE[j - 1][i].char1 = gm.WL;

							}

							if (cpLim(j, i - 1, '.', gm)
									&& !cpLim(j - 1, i - 1, '.', gm)
									&& !cpLim(j + 1, i - 1, '.', gm)
									&& !cpLim(j, i - 2, '.', gm)) {
								if (rd.nextInt(turnCloseFactor) != 0)
									gm.MAZE[j][i - 1].char1 = gm.WL;

							}
							if (cpLim(j, i + 1, '.', gm)
									&& !cpLim(j - 1, i + 1, '.', gm)
									&& !cpLim(j + 1, i + 1, '.', gm)
									&& !cpLim(j, i + 2, '.', gm)) {
								if (rd.nextInt(turnCloseFactor) != 0)
									gm.MAZE[j][i + 1].char1 = gm.WL;

							}

							gm.MAZE[j + 1][i].char1 = gm.EM;
							gm.MAZE[j + 1][i - 1].char1 = gm.WL;
							gm.MAZE[j + 1][i + 1].char1 = gm.WL;
							j = j + 1;

							caminho.push(new Par(i, j));
							moved = true;

							if (cpLim(j + 1, i, '.', gm)) {

								gm.MAZE[j + 1][i].char1 = gm.EM;
								if (cpLim(j + 1, i + 2, ' ', gm))
									if (rd.nextInt(closedFactor) != 0)
										gm.MAZE[j + 1][i + 1].char1 = gm.WL;
								if (cpLim(j + 1, i - 2, ' ', gm))
									if (rd.nextInt(closedFactor) != 0)
										gm.MAZE[j + 1][i - 1].char1 = gm.WL;

								j = j + 1;
								caminho.pop();
								caminho.push(new Par(i, j));

							}

						}
						break;

					case 2:
						if (cpLim(j, i + 1, '.', gm)) {

							if (cpLim(j, i - 1, '.', gm)
									&& !cpLim(j - 1, i - 1, '.', gm)
									&& !cpLim(j + 1, i - 1, '.', gm)
									&& !cpLim(j, i - 2, '.', gm)) {
								if (rd.nextInt(turnCloseFactor) != 0)
									gm.MAZE[j][i - 1].char1 = gm.WL;

							}

							if (cpLim(j - 1, i, '.', gm)
									&& !cpLim(j - 1, i - 1, '.', gm)
									&& !cpLim(j - 1, i + 1, '.', gm)
									&& !cpLim(j - 2, i, '.', gm)) {
								if (rd.nextInt(turnCloseFactor) != 0)
									gm.MAZE[j - 1][i].char1 = gm.WL;

							}
							if (cpLim(j + 1, i, '.', gm)
									&& !cpLim(j + 1, i - 1, '.', gm)
									&& !cpLim(j + 1, i + 1, '.', gm)
									&& !cpLim(j + 2, i, '.', gm)) {
								if (rd.nextInt(turnCloseFactor) != 0)
									gm.MAZE[j + 1][i].char1 = gm.WL;

							}

							gm.MAZE[j][i + 1].char1 = gm.EM;
							gm.MAZE[j - 1][i + 1].char1 = gm.WL;
							gm.MAZE[j + 1][i + 1].char1 = gm.WL;

							i = i + 1;
							caminho.push(new Par(i, j));
							moved = true;

							if (cpLim(j, i + 1, '.', gm)) {

								gm.MAZE[j][i + 1].char1 = gm.EM;
								if (cpLim(j - 2, i + 1, ' ', gm))
									if (rd.nextInt(closedFactor) != 0)
										gm.MAZE[j - 1][i + 1].char1 = gm.WL;
								if (cpLim(j + 2, i + 1, ' ', gm))
									if (rd.nextInt(closedFactor) != 0)
										gm.MAZE[j + 1][i + 1].char1 = gm.WL;

								i = i + 1;
								caminho.pop();
								caminho.push(new Par(i, j));

							}

						}
						break;
					case 3:
						if (cpLim(j, i - 1, '.', gm)) {

							if (cpLim(j, i + 1, '.', gm)
									&& !cpLim(j - 1, i + 1, '.', gm)
									&& !cpLim(j + 1, i + 1, '.', gm)
									&& !cpLim(j, i + 2, '.', gm)) {
								if (rd.nextInt(turnCloseFactor) != 0)
									gm.MAZE[j][i + 1].char1 = gm.WL;

							}

							if (cpLim(j - 1, i, '.', gm)
									&& !cpLim(j - 1, i - 1, '.', gm)
									&& !cpLim(j - 1, i + 1, '.', gm)
									&& !cpLim(j - 2, i, '.', gm)) {
								if (rd.nextInt(turnCloseFactor) != 0)
									gm.MAZE[j - 1][i].char1 = gm.WL;

							}
							if (cpLim(j + 1, i, '.', gm)
									&& !cpLim(j + 1, i - 1, '.', gm)
									&& !cpLim(j + 1, i + 1, '.', gm)
									&& !cpLim(j + 2, i, '.', gm)) {
								if (rd.nextInt(turnCloseFactor) != 0)
									gm.MAZE[j + 1][i].char1 = gm.WL;

							}

							gm.MAZE[j][i - 1].char1 = gm.EM;
							gm.MAZE[j - 1][i - 1].char1 = gm.WL;
							gm.MAZE[j + 1][i - 1].char1 = gm.WL;

							i = i - 1;
							caminho.push(new Par(i, j));
							moved = true;

							if (cpLim(j, i - 1, '.', gm)) {

								gm.MAZE[j][i - 1].char1 = gm.EM;
								if (cpLim(j - 2, i - 1, ' ', gm))
									if (rd.nextInt(closedFactor) != 0)
										gm.MAZE[j - 1][i - 1].char1 = gm.WL;
								if (cpLim(j + 2, i - 1, ' ', gm))
									if (rd.nextInt(closedFactor) != 0)
										gm.MAZE[j + 1][i - 1].char1 = gm.WL;

								i = i - 1;
								caminho.pop();
								caminho.push(new Par(i, j));

							}

						}
						break;

					}
				} while (!moved);

			} else {

				caminho.pop();
				if (!caminho.empty()) {
					i = caminho.peek().getx();
					j = caminho.peek().gety();

				}

			}
			moved = false;
		} while (!caminho.empty());

		boolean side = true;
		char check;

		if (gm.MAZE[1][1].char1 instanceof Empty)
			check = 'X';
		else
			check = ' ';

		for (int a = 2; a < gm.Width - 1; a++) {
			if (gm.MAZE[1][a].char1.token == check) {
				if (check == ' ')
					check = 'X';
				else
					check = ' ';
			}

			else
				side = false;

		}
		if (side == true) {

			for (int a = 1; a < gm.Width - 1; a++) {

				if (gm.MAZE[1][a - 1].char1 instanceof Empty
						&& gm.MAZE[1][a + 1].char1 instanceof Empty
						&& gm.MAZE[2][a].char1 instanceof Empty
						&& rd.nextInt(arranjeFactor) != 1) {
					gm.MAZE[1][a].char1 = gm.EM;
					gm.MAZE[2][a].char1 = gm.WL;

				}

			}

		}

		side = true;

		if (gm.MAZE[gm.Height - 2][1].char1 instanceof Empty)
			check = 'X';
		else
			check = ' ';

		for (int a = 2; a < gm.Width - 2; a++) {
			if (gm.MAZE[gm.Height - 2][a].char1.token == check) {
				if (check == ' ')
					check = 'X';
				else
					check = ' ';
			}

			else
				side = false;

		}
		if (side == true) {

			for (int a = 1; a < gm.Width - 2; a++) {

				if (gm.MAZE[gm.Height - 2][a - 1].char1 instanceof Empty
						&& gm.MAZE[gm.Height - 2][a + 1].char1 instanceof Empty
						&& gm.MAZE[gm.Height - 3][a].char1 instanceof Empty
						&& rd.nextInt(arranjeFactor) != 1) {
					gm.MAZE[gm.Height - 2][a].char1 = gm.EM;
					gm.MAZE[gm.Height - 3][a].char1 = gm.WL;

				}

			}

		}
		side = true;

		if (gm.MAZE[1][1].char1 instanceof Empty)
			check = 'X';
		else
			check = ' ';

		for (int a = 2; a < gm.Height - 1; a++) {
			if (gm.MAZE[a][1].char1.token == check) {
				if (check == ' ')
					check = 'X';
				else
					check = ' ';
			}

			else
				side = false;

		}
		if (side == true) {

			for (int a = 1; a < gm.Height - 1; a++) {

				if (gm.MAZE[a - 1][1].char1 instanceof Empty
						&& gm.MAZE[a + 1][1].char1 instanceof Empty
						&& gm.MAZE[a][2].char1 instanceof Empty
						&& rd.nextInt(arranjeFactor) != 1) {
					gm.MAZE[a][1].char1 = gm.EM;
					gm.MAZE[a][2].char1 = gm.WL;

				}

			}

		}
		side = true;

		if (gm.MAZE[1][gm.Width - 2].char1 instanceof Empty)
			check = 'X';
		else
			check = ' ';

		for (int a = 2; a < gm.Height - 1; a++) {
			if (gm.MAZE[a][gm.Width - 2].char1.token == check) {
				if (check == ' ')
					check = 'X';
				else
					check = ' ';
			}

			else
				side = false;

		}
		if (side == true) {

			for (int a = 1; a < gm.Height - 1; a++) {

				if (gm.MAZE[a - 1][gm.Width - 2].char1 instanceof Empty
						&& gm.MAZE[a + 1][gm.Width - 2].char1 instanceof Empty
						&& gm.MAZE[a][gm.Width - 3].char1 instanceof Empty
						&& rd.nextInt(arranjeFactor) != 1) {
					gm.MAZE[a][gm.Width - 2].char1 = gm.EM;
					gm.MAZE[a][gm.Width - 3].char1 = gm.WL;

				}

			}

		}

	}

	private boolean hasPath(int j, int i, Game gm) {

		if (cpLim(j - 1, i, '.', gm))
			return true;

		if (cpLim(j + 1, i, '.', gm))
			return true;

		if (cpLim(j, i + 1, '.', gm))
			return true;

		if (cpLim(j, i - 1, '.', gm))
			return true;

		return false;

	}

	

}
