package sudoku;

import java.util.LinkedList;

/**
 * This class keeps the information for EACH node of the grid.
 * Each Node contains a LinkedList of possibilities, a Boolean
 * value which indicates if the Node is permanent (is it entered
 * by the user) and a value which is the value that is in the node.
 * @author Pooya Sami & Kyle Smyth
 *
 */
public class Node {

	private LinkedList<Integer> possibilities;
	private boolean isPermanent;
	private Integer value;

	/**
	 * Constructor
	 * @param possibilities the possibilities
	 * @param isPermanent true if the node is entered by the user
	 * @param value the value of the node
	 */
	public Node(LinkedList<Integer> possibilities, boolean isPermanent,
			Integer value) {
		super();
		this.possibilities = possibilities;
		this.isPermanent = isPermanent;
		this.value = value;
	}

	/**
	 * Getter for Value
	 * @return value
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * Setter for the value
	 * @param value
	 */
	public void setValue(Integer value) {
		this.value = value;
	}

	/**
	 * Getter for the possibilities
	 * @return the linkedlist of the possibilities.
	 */
	public LinkedList<Integer> getPossibilities() {
		return possibilities;
	}

	/**
	 * Setter for possibilities
	 * @param possibilities (LinkedList)
	 */
	public void setPossibilities(LinkedList<Integer> possibilities) {
		this.possibilities = possibilities;
	}

	/**
	 * getter of isPermanenet
	 * @return isPermanent
	 */
	public boolean isPermanent() {
		return isPermanent;
	}

	/**
	 * Setter of isPermanent
	 * @param isPermanent
	 */
	public void setPermanent(boolean isPermanent) {
		this.isPermanent = isPermanent;
	}

}
