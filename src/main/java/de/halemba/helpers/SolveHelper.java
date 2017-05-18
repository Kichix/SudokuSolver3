package de.halemba.helpers;

import de.halemba.elements.Field;

public final class SolveHelper {
	
	public static final boolean debug = false;

	//Delivers the quadrant in which the field is in 
	public static int getQuadrant(int i, int j) {
		//Decide to which quadrant the field belongs to
		if(i < 3 && j < 3) {
			return 0;
		} else if(i < 3 && j>= 3 && j<6) {
			return 1;
		} else if(i < 3 && j>=6){
			return 2;
		} else if(i >= 3 && i <6 && j <3) {
			return 3;
		} else if(i >= 3 && i <6 && j >= 3 && j<6) {
			return 4;
		} else if(i >= 3 && i <6 && j>=6) {
			return 5;
		} else if(i >= 6 && j < 3) {
			return 6;
		} else if(i >= 6 && j >= 3 & j < 6) {
			return 7;
		} else if(i >= 6 && j >= 6) {
			return 8;
		} else return -1;
	}
	
	//Checks if a nunmber is still free in the row
	public static boolean checkRow(int i, int j, int k, Field[][] grid) {
		for(int l=0; l<9; l++) {
			if(grid[i][l].getNumber() == k && l!=j)
				return false;
		}
		return true;
	}
	
	//Checks if a number is still free in the column
	public static boolean checkCol(int i, int j, int k, Field[][] grid) {
		for(int l=0; l<9; l++) {
			if(grid[l][j].getNumber() == k && l!=i)
				return false;
		}
		return true;
	}
	
	//Checks if a number is still free in the quadrant
	public static boolean checkQuad(int i, int j, int k, Field[][] grid) {
		for(int l=0; l<9; l++) {
			for(int m=0; m<9; m++) { 
				if(l!=i && m!=j) {
					if(grid[l][m].getQuadrant() == grid[i][j].getQuadrant() && grid[l][m].getNumber() == k) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	//Checks if there is only one possible Field for a number(i) in a row(j)
	public static int checkSinglePossiblityRow(int i, int j, Field[][] grid) {
		
		int count = 0;
		int field = -1;
		int[] possibles;
		
		//Row
		for(int k=0; k<9; k++) {
			if(!grid[j][k].getFix()) {
				possibles = grid[j][k].getPossbileNumbers();
				
				if(possibles[i-1] == i) {
					count += 1;
					field = k;
				}
			}
		}
		
		if(count > 1) {
			field = -1;
		}
		
		return field;
	}
	
	//Checks if a number is still missing in a row
	public static boolean checkMissingInRow(int i, int j, Field[][] grid) {
			
		for(int k=0; k<9; k++) {
			if(grid[j][k].getNumber() == i) {
					return false;
				} 
		}
		return true;
	}
	
	//Checks if a given numbers is missing in a giving column
	public static boolean checkMissingInColumn(int i, int j, Field[][] grid) {
		
		for(int k=0; k<9; k++) {
			if(grid[k][j].getNumber() == i) {
					return false;
				} 
		}
		return true;
	}
	
	//Checks if there is only one possible Field for a number(i) in a column(j)
	public static int checkSinglePossiblityColumn(int i, int j, Field[][] grid) {
			
			int count = 0;
			int field = -1;
			int[] possibles;
			
			//Column
			for(int k=0; k<9; k++) {
				if(!grid[k][j].getFix()) {
					possibles = grid[k][j].getPossbileNumbers();
					
					if(possibles[i-1] == i) {
						count += 1;
						field = k;
					}
				}
			}
			
			if(count > 1) {
				field = -1;
			}
			
			return field;
		}
	
	//Checks if a given numbers is missing in a given quadrant
	public static boolean checkMissingInQuadrant(int i, int j, Field[][] grid) {
		
		for(int k=0; k<9; k++) {
			for(int l=0; l<9; l++) {
				if(grid[k][l].getQuadrant()==j && grid[k][l].getNumber()==i) {
					return false;
				} 
			}
		}
		return true;
	}
	
	//Checks if there is only one possiblity for a given number in a given field
	public static int[] checkSinglePossiblityQuadrant(int i, int j, Field[][] grid) {
		
		int count = 0;
		int[] field = new int[2];
		int[] possibles;
		
		field[0] = -1;
		field[1] = -1;
		
		//Column
		for(int k=0; k<9; k++) {
			for(int l =0; l<9; l++) {
				if(!grid[k][l].getFix() && grid[k][l].getQuadrant()==j) {
					possibles = grid[k][l].getPossbileNumbers();
					
					if(possibles[i-1] == i) {
						count += 1;
						field[0] = k;
						field[1] = l;
					}
				}
			}
		}
		
		if(count > 1) {
			field[0] = -1;
			field[1] = -1;
		}
		
		return field;
	}
	
	//Returns if the Sudoku is solved
	public static boolean solved(Field[][] grid) {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(grid[i][j].getNumber()==0) {
					return false;
				}
			}
		}
		
		return true;
	}
	
}
