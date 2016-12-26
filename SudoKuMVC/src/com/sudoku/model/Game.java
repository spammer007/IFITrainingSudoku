package com.sudoku.model;

import java.io.Serializable;
import java.util.List;

public class Game implements Serializable{
	private int size;
	private Integer[][] tableGame;
	
	public Game(int size, Integer[][] tableGame) {
		super();
		this.size = size;
		this.tableGame = tableGame;
	}
	
	public Game() {
	}

	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public Integer[][] getTableGame() {
		return tableGame;
	}
	public void setTableGame(Integer[][] tableGame) {
		this.tableGame = tableGame;
	}
	
	
	
}
