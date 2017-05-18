package de.halemba.solver;

import de.halemba.elements.Field;
import de.halemba.helpers.SolveHelper;

public class BruteForcer {

	Field[][] grid;
	int[] currentField;
	
	public BruteForcer(Field[][] grid) {
		
		this.grid = new Field[9][9];
		this.grid = grid;
		currentField = new int[2];
		getNextField(0,0);
	}
	
	public void getNextField(int x, int y) {
		
		//x = Zeile, y = Spalte, 0 = zeile, 1 = Spalte
		int[] nextField = new int[2];
		
		if(!(x==8 && y==8)) {
			if(x==8) {
				nextField[0] = 0;
				nextField[1] = y+1;
			} else {
				nextField[0] = x+1;
				nextField[1] = y;
			}
			
			if(grid[nextField[0]][nextField[1]].getFix()) {
				getNextField(nextField[0], nextField[1]);
			} else {
				currentField = nextField;
			}
		}
		
	}
	
	public void getLastField(int x, int y) {
		
		int[] lastField = new int[2];
		
		if(x==0) {
			lastField[0] = 8;
			lastField[1] = y-1;
		} else {
			lastField[0] = x-1;
			lastField[1] = y;
		}
		
		if(grid[lastField[0]][lastField[1]].getFix()) {
			getLastField(lastField[0], lastField[1]);
		} else {
			currentField = lastField;
		}
	}

	public Field[][] forceSolve() {
		
		while(!SolveHelper.solved(grid)) {
			force(currentField);
		}
		
		return this.grid;
	}
	
	//forcesolves a field
	public void force(int[] field) {
		
		System.out.println("Forcing Field: "+field[0]+"/"+field[1]+"("+grid[field[0]][field[1]].getNumber()+")");
		int[] possibles = grid[field[0]][field[1]].getPossbileNumbers();
		int start = grid[field[0]][field[1]].getNumber();
		int number = -1;
		
//		for(int i=start; i<possibles.length; i++) {
//			if(possibles[i] != 0 && 
//			   SolveHelper.checkQuad(field[0], field[1], possibles[i], grid) &&
//			   SolveHelper.checkRow(field[0], field[1], possibles[i], grid) &&
//			   SolveHelper.checkCol(field[0], field[1], possibles[i], grid)) {
//				
//				grid[field[0]][field[1]].setNumber(possibles[i]);
//				
//				getNextField(field[0], field[1]);
//				number = possibles[i];
//				break;
//			}
//		}
		
		for(int i=start+1; i<10; i++) {
			if(SolveHelper.checkQuad(field[0], field[1], i, grid) &&
			   SolveHelper.checkRow(field[0], field[1], i, grid) &&
			   SolveHelper.checkCol(field[0], field[1], i, grid)) {
				
				grid[field[0]][field[1]].setNumber(i);
				
				getNextField(field[0], field[1]);
				number = i;
				break;
			}
		}
		
		
		if(number < 0) {
			
			grid[field[0]][field[1]].setNumber(0);
			getLastField(field[0], field[1]);
		}
		
	}
}
