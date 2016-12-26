package com.sudoku.service;


import com.sudoku.model.Nodes;

public interface IGame {
	void initGame(String pathSource);
	boolean runGame(Nodes node);
	Nodes input();
	void displayTable();
	boolean validate(Nodes node);
	String suggest(int row, int col);
	void rollback();
	void saveGame(String path);
	void endGame();
	String exitGame();
}
