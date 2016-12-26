package com.sudoku.model;

import java.io.Serializable;

public class Nodes implements Serializable {
	private int row;
	private int col;
	private int value;
	private boolean status;
	
	public Nodes() {
	}
	
	

	public Nodes(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}



	public Nodes(int row, int col, int value, boolean status) {
		super();
		this.row = row;
		this.col = col;
		this.value = value;
		this.status = status;
	}

	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	
}
