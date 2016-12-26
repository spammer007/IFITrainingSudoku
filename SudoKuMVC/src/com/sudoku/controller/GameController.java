package com.sudoku.controller;


import com.sudoku.service.IGame;

public class GameController {
	private IGame iTable;
	private String path;
	public GameController(IGame iTable) {
		super();
		this.iTable = iTable;
	}

	public GameController(IGame iTable, String path) {
		super();
		this.iTable = iTable;
		this.path = path;
	}

	public void setiTable(IGame iTable) {
		this.iTable = iTable;
	}
	
	public void startGame(){
		iTable.initGame( path);
	}
	
	public void playGame() {
		iTable.runGame(iTable.input());
	}
	
	public String suggestGame(int row, int col){
		return iTable.suggest(row, col);
	}
	
	public void rollback(){
		iTable.rollback();
	}
	
	public void saveGame() {
		iTable.saveGame(path);
	}
	
	public void endGame(){
		iTable.endGame();
	}
	
	public String exitGame(){
		return iTable.exitGame();
	}
}
