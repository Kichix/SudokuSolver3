package de.halemba.solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import de.halemba.elements.Field;
import de.halemba.gui.SudokuGUI;
import de.halemba.helpers.SolveHelper;
import de.halemba.helpers.Validator;

public class Solver implements ActionListener {
	
	SudokuGUI gui;
	JButton start;
	Field[][] grid;
	Field[][] oldGrid;
	
	public Solver() {
		
		gui = new SudokuGUI(this);
		start = gui.getStartButton();
		grid = new Field[9][9];
		oldGrid = new Field[9][9];
		initFields();
		
		if(SolveHelper.debug) {
			debugValues();
			gui.updateFields(grid);
		}
	}
	
	//Sets some values for debugging purposes
	public void debugValues() {
		
//		int k = 1;
//		
//		for(int i = 0; i<9; i++) {
//			for(int j = 0; j<9; j++) {
//				if(grid[i][j].getQuadrant() == 0) {
//					grid[i][j].setNumber(k);
//					k += 1;
//				}
//			}
//		}
		
		//Row 0
		grid[0][0].setNumber(6);
		grid[0][6].setNumber(3);
		grid[0][7].setNumber(5);
		
		//Row 1
		grid[1][0].setNumber(7);
		grid[1][2].setNumber(4);
		grid[1][4].setNumber(9);
		grid[1][5].setNumber(3);
		
		//Row 2
		grid[2][1].setNumber(9);
		grid[2][3].setNumber(5);
		grid[2][4].setNumber(1);
		grid[2][6].setNumber(4);
		grid[2][7].setNumber(6);
		
		//Row 3
		grid[3][1].setNumber(8);
		grid[3][2].setNumber(1);
		grid[3][7].setNumber(2);
		grid[3][8].setNumber(5);
		
		//Row 4
		grid[4][0].setNumber(9);
		grid[4][1].setNumber(2);
		grid[4][6].setNumber(6);
		grid[4][7].setNumber(3);
		
		//Row 5
		grid[5][3].setNumber(4);
		grid[5][5].setNumber(9);
		grid[5][7].setNumber(7);
	
		//Row 6
		grid[6][1].setNumber(6);
		grid[6][2].setNumber(2);
		grid[6][5].setNumber(1);
		grid[6][8].setNumber(8);
		
		//Row 7
		grid[7][1].setNumber(4);
		grid[7][3].setNumber(8);
		grid[7][5].setNumber(7);
		
		//Row 8
		grid[8][2].setNumber(9);
		grid[8][5].setNumber(2);
		grid[8][6].setNumber(5);
		grid[8][8].setNumber(6);
	}

	//Updates the numbers in the old grid
	public void updateOldgrid() {
		
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				oldGrid[i][j].setNumber(grid[i][j].getNumber());
			}
		}
	}
	
	//Checks if there has been a change
	public boolean checkChange() {
		
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(oldGrid[i][j].getNumber()!=grid[i][j].getNumber()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	//Initializes the grid
	private void initFields() {
		
		for(int i = 0; i<9; i++) {
			for(int j = 0; j<9; j++) {
				grid[i][j] = new Field(SolveHelper.getQuadrant(i, j));
				oldGrid[i][j] = new Field(SolveHelper.getQuadrant(i, j));
			}
		}
	}
	
	//Updates the grid from the GUI
	private void updateFields() {
		
		for(int i = 0; i<9; i++) {
			for(int j = 0; j<9; j++) {
				if(gui.getFieldValue(i, j) > 0){
					System.out.println("Fixing field: "+i+"/"+j);
					grid[i][j].setNumber(gui.getFieldValue(i, j));
					grid[i][j].setFix(true);
				}
			}
		}	
	}
	
	//Updates the possible numbers for each field
	private void updatePossibles() {
		
		for(int i = 0; i<9; i++) {
			for(int j = 0; j<9; j++) {
				if(!grid[i][j].getFix()) {
					for(int k=1; k<=9; k++) {
						if(SolveHelper.checkRow(i,j,k,grid)) {
							if(SolveHelper.checkCol(i,j,k,grid)) {
								if(SolveHelper.checkQuad(i,j,k,grid)) {
									
								} else {
									grid[i][j].delPossibleNumber(k);
								}
							} else {
								grid[i][j].delPossibleNumber(k);
							}
						} else {
							grid[i][j].delPossibleNumber(k);
						}
					}
				}
			}
		}
	}
	
	//Sets a number if it is needed in a row and there is only one possible field
	public void checkNeededInRow() {
		
		int n;
		
		//Rows
		for(int j=0; j<9; j++) {
			//Numbers
			for(int i=1; i<10; i++) {
				if(SolveHelper.checkMissingInRow(i, j, grid)) {
					n = SolveHelper.checkSinglePossiblityRow(i, j, grid);
					if(n>=0) {
						grid[j][n].setNumber(i);
						break;
					}
				}
			}
		}
	}
	
	//Sets a number if it is needed in a column and there is only one possible field
	public void checkNeededInColumn() {

		int n;
		
		//Columns
		for(int j=0; j<9; j++) {
			//Numbers
			for(int i=1; i<10; i++) {
				if(SolveHelper.checkMissingInColumn(i, j, grid)) {
					n = SolveHelper.checkSinglePossiblityColumn(i, j, grid);
					if(n>=0) {
						grid[n][j].setNumber(i);
						break;
					}
				}
			}
		}
	}
	
	//Sets a number if it is needed in a quadrant and there is only one possible field
	public void checkNeededInQuadrant() {
		
		int[] n;
		
		//Quadrants
		for(int j=0; j<9; j++) {
			//Numbers
			for(int i=1; i<10; i++) {
				if(SolveHelper.checkMissingInQuadrant(i, j, grid)) {
					n = SolveHelper.checkSinglePossiblityQuadrant(i, j, grid);
					if(n[0]>=0) {
						grid[n[0]][n[1]].setNumber(i);
						break;
					}
				}
			}
		}
	}
	
	//Executes all the "needed"-methods from above in a single call
	public void checkNeeded() {
		checkNeededInRow();
		checkNeededInColumn();
		checkNeededInQuadrant();
	}
	
	//Resets the Sudoku
	public void resetFields() {
		for(int i = 0; i<9; i++) {
			for(int j = 0; j<9; j++) {
			grid[i][j].reset();
			}
		}
		
		gui.updateFields(grid);
	}
	
	//Solves the grid by brute force
	public void bruteForce() {
		BruteForcer forcer = new BruteForcer(grid);
		grid = forcer.forceSolve();
		gui.updateFields(grid);
	}
	
	//Solves the Sudoku
	public void solve() {
		
		String error = "";
		String error2 = "";
		Boolean progress = true;
		int totalIterations = 0;
		
		updateFields();
		
		if(Validator.validate(grid)==0) {
			while(!SolveHelper.solved(grid) && progress) {
				

				updateOldgrid();
				updatePossibles();
				checkNeeded();
				progress = checkChange();
			}
			
			gui.updateFields(grid);
			
			if(!SolveHelper.solved(grid) && !progress) {
				System.out.println("BRUTEFORCEEEEEEEEEE");
				bruteForce();
			}
			
			for(int i=0; i<9; i++) {
				for (int j=0; j<9; j++) {
					totalIterations += grid[i][j].getIterations();
				}
			}
			gui.setState("Sudoku gelöst! Iterationen gesamt: "+totalIterations);
			
		} else {
			switch(Validator.validate(grid)) {
			case 1: error = "Zeile";
					error2 = "doppelte";
					break;
			case 2: error = "Spalte";
					error2 = "doppelte";
					break;
			case 3: error = "Kästchen";
					error2 = "doppelte";
					break;
			case 4: error = "Feld";
					error2 = "zu große oder zu kleine";
					break;
			}
			gui.setState(String.format("Eingaben sind nicht valide, ein(e) %s enthält eine %s Zahl", error, error2));
		}
	}
	
	//Actionlistener
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() ==  gui.getStartButton()) {
			solve();
		} else if (e.getSource() == gui.getPosButton()) {
//			updateFields();
//			updatePossibles();
//			gui.updateFields(grid);
			BruteForcer forcer = new BruteForcer(grid);
			grid = forcer.forceSolve();
			gui.updateFields(grid);
		} else if (e.getSource() == gui.getResetButton()) {
			resetFields();
		}
	}
}
