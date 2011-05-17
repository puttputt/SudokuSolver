package sudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.HeadlessException;
import javax.swing.*;

/**
 * This class extends JApplet and is the GUI class of the Sudoku game which gets
 * and inserts the values to the Grid class and gets the answer from the Grid
 * class and shows it in the TABLE. It also checks the entered values to prevent
 * entering meaningless values into the game and alerts the user. It also has
 * some example games which the user can choose and insert the games in the grid
 * and get the solved.
 * 
 * @author Pooya Sami & Kyle Smyth
 * 
 */
public class Sudoku extends JApplet {

	private static final long serialVersionUID = 1L;
	private JTable grid = new JTable(9, 9);
	private Grid game = new Grid();
	private boolean hasInvalidVals = false;

	/**
	 * It initialises the game and the grid (table).
	 */
	public void init() {
		// Execute a job on the event-dispatching thread; creating this applet's
		// GUI.
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					// we needed to new all the entries otherwise we could
					// get the nullexceptionerror...
					reset();

					setSize(340, 340);

					grid.setRowHeight(25);
					grid.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
							Color.BLACK));
					grid.setGridColor(Color.BLACK);

					JButton submit = new JButton("Solve!");
					submit.setSize(80, 40);
					submit.setLocation(90, 230);
					submit.addActionListener(solveAction);

					JButton reset = new JButton("Reset");
					reset.setSize(80, 40);
					reset.setLocation(170, 230);
					reset.addActionListener(resetAction);

					JButton loadExample1 = new JButton("Example 1");
					loadExample1.setSize(100, 40);
					loadExample1.setLocation(20, 270);
					loadExample1.addActionListener(load1);

					JButton loadExample2 = new JButton("Example 2");
					loadExample2.setSize(100, 40);
					loadExample2.setLocation(120, 270);
					loadExample2.addActionListener(load2);

					JButton loadExample3 = new JButton("Example 3");
					loadExample3.setSize(100, 40);
					loadExample3.setLocation(220, 270);
					loadExample3.addActionListener(load3);

					add(loadExample1);
					add(loadExample2);
					add(loadExample3);
					add(reset);
					add(submit);
					add(grid);
				}

			});
		} catch (Exception e) {
			System.err.println("createGUI didn't complete successfully");
		}
	}

	/**
	 * It gets the values from the table and inserts them into the "game"
	 * variable. It's being called when the user presses the solve button.
	 * 
	 */
	public void getValuesFromTableToGame() {
		try {
			int k = 0;
			String x;
			Integer x1;
			int emptyCount = 0;
			hasInvalidVals = false;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					x = grid.getValueAt(i, j).toString();
					if (x.isEmpty()) {
						game.getGrid().get(k).setValue(0);
						emptyCount++;
						if (emptyCount == 81) {
							hasInvalidVals = true;
						}
					} else {

						x1 = Integer.parseInt(x);

						if (!game.checkCol(k, x1) && !game.checkRow(k, x1)
								&& !game.CheckGrid(k, x1) && !(x1 < 1)
								&& !(x1 > 9)) {
							game.getGrid().get(k).setValue(x1);
							game.getGrid().get(k).setPermanent(true);
							game.getGrid().get(k).getPossibilities().clear();
							game.Update(k);
							game.setCount(game.getCount() + 1);
						} else {

							if ((x1 < 1) || (x1 > 9)) {
								JOptionPane
										.showMessageDialog(
												grid,
												"Invalid Entry! The values entered should be between 1 to 9. Check the node at Row "
														+ (i + 1)
														+ ", Column "
														+ (j + 1) + ".");
								hasInvalidVals = true;
							}
							if (game.getGrid().get(k).getValue() == x1) {
								System.out
										.println("The value exists in the grid.");

							} else if (game.checkCol(k, x1)
									|| game.checkRow(k, x1)
									|| game.CheckGrid(k, x1)
									&& game.getGrid().get(k).getValue() != x1) {
								JOptionPane
										.showMessageDialog(
												grid,
												"The values are not possible. You cannot add the same value"
														+ "that you entered before in the same row, column or grid. Check the "
														+ "node at Row "
														+ (i + 1) + ", Column "
														+ (j + 1) + ".");
								hasInvalidVals = true;
							}
						}
					}
					k++;
				}

			}
		} catch (NumberFormatException e) {
			JOptionPane
					.showMessageDialog(
							grid,
							"Invalid Entry! The values entered should be between 1 to 9 and they should not be characters."
									+ "Please remove the characters...");
			hasInvalidVals = true;
		} catch (HeadlessException e) {
			hasInvalidVals = true;
		}

	}

	/**
	 * 
	 * It gets the values from the table and inserts them into the "game"
	 * variable.
	 * 
	 */
	public void fillValues() {
		int k = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (game.getGrid().get(k).getValue() != 0) {
					grid.getModel().setValueAt(
							game.getGrid().get(k).getValue(), i, j);
				}
				++k;
			}
		}
	}

	/**
	 * Creates a new instance of the game variable and also initiates the values
	 * on the table.
	 */
	public void reset() {
		game = new Grid();
		hasInvalidVals = false;
		int k = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				grid.setValueAt(new String(), i, j);
				++k;
			}
		}
	}

	/**
	 * Imports the predefined games into the "game" variable. There are three
	 * options for the games which are defined as strings inside this method.
	 * 
	 * @param example
	 *            the example number which is from 1 to 3
	 */
	public void Import(int example) {
		reset();

		String inputString = "";
		switch (example) {
		case 1:
			inputString = "000304000200010006690000071805602907100000008903801604310000025700020009000105000";
			break;
		case 2:
			inputString = "720190560014000009000720081030002100240000053008600020390017000400000910082045036";
			break;
		case 3:
			// inputString =
			// "000204395456790280030051000104000000900000002000000708000980030098045126643102000";
			inputString = "300000004061000090004039070000450060080070030070061000010620700050000910200000006";
			break;
		}

		int value;
		int k = 0;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				value = Integer.parseInt(inputString.substring(k, k + 1));
				if (value != 0) {
					grid.getModel().setValueAt(value, i, j);
				}
				++k;
			}
		}
	}

	/**
	 * Reset button ActionListener
	 */
	ActionListener resetAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			reset();

		}
	};

	/**
	 * This is the ActionListener for when the user presses the Example 1 button
	 */
	ActionListener load1 = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			Import(1);
		}
	};
	/**
	 * This is the ActionListener for when the user presses the Example 1 button
	 */
	ActionListener load2 = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			Import(2);
		}
	};
	/**
	 * This is the ActionListener for when the user presses the Example 1 button
	 */
	ActionListener load3 = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			Import(3);
		}
	};

	/**
	 * Solve button ActionListener
	 */
	ActionListener solveAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			getValuesFromTableToGame();
			if (!hasInvalidVals) {
				game.printGrid();
				game.SimpleFillValuesRecursive();
				game.printGrid();
				fillValues();
				if (!game.YouWin()) {
					game.bruteSolve();
					fillValues();
					System.out.println("done");
					JOptionPane
							.showMessageDialog(grid, "The Puzzle is Solved!");
				} else {
					JOptionPane
							.showMessageDialog(grid, "The Puzzle is Solved!");
				}
			}
		}
	};
}
