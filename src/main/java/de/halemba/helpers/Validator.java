package de.halemba.helpers;

import de.halemba.elements.Field;

//This objects verifies if the entered Sudoku is valid
public final class Validator {

	public static int validate(Field[][] grid) {
		
		//Initialize counters
		int[][] rows = new int[9][9];
		int[][] columns = new int[9][9];
		int[][] quadrants = new int[9][9];
		
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
			rows[i][j]=0;
			columns[i][j]=0;
			quadrants[i][j]=0;
			}
		}
		
		//Check if entered numbers are valid
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(grid[i][j].getNumber() > 9 || grid[i][j].getNumber()<0) {
					return 4;
				}
			}
		}
		
		
		//Check if rows and columns are valid
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(grid[i][j].getNumber()!=0) {
					rows[i][grid[i][j].getNumber()-1] += 1;
					columns[j][grid[i][j].getNumber()-1] += 1;
				}
			}
		}
		
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(rows[i][j] > 1) {
					return 1;
				} else if(columns[i][j] > 1) {
					return 2;
				}
			}
		}
		
		//Checks if quadrants are valid
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(grid[i][j].getNumber()!=0) {
					quadrants[grid[i][j].getQuadrant()][grid[i][j].getNumber()-1] += 1;
				}
			}
		}
		
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(quadrants[i][j]>1) {
					return 3;
				}
			}
		}
		
		return 0;
	}
}
