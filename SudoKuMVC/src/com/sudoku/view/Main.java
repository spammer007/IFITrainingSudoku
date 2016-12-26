package com.sudoku.view;

import java.util.Scanner;

import com.sudoku.controller.GameController;
import com.sudoku.iostream.HandleExcelData;
import com.sudoku.iostream.HandleXMLData;
import com.sudoku.iostream.IHandleSource;
import com.sudoku.service.IStack;
import com.sudoku.service.IGame;
import com.sudoku.service.StackImp;
import com.sudoku.util.Commons;
import com.sudoku.service.GameImp;

public class Main {
	public static void doTutorial(){
		System.out.println();
		System.out.println("* Nhập phím 0 để chơi lại.");
		System.out.println("* Nhập phím 1 để rollback.");
		System.out.println("* Nhập phím 2 để chơi tiếp.");
		System.out.println("* Nhập phím 3 để trợ giúp.");
		System.out.println("* Nhập phím 4 để lưu game. ");
		System.out.println("* Nhập phím 5 để thoát. ");
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		IStack iStack = new StackImp();
		IHandleSource iHandleSource = null ;
		IGame iGame = null;
		GameController gameController = null;
		Scanner inp = new Scanner(System.in);
		int n;
		System.out.println("********* PLAY GAME **********");
		System.out.println("* Nhập phím 0 để vào game: ");
		
		while(true) {
			n = inp.nextInt();
			switch(n){
				case 0:
					System.out.println("* Nhập phím 1 để load game từ file xml: ");
					System.out.println("* Nhập phím 2 để load game từ file excel: ");
					int s;
					s = inp.nextInt(); 
					switch (s) {
						case 1 : 
							iHandleSource = new HandleXMLData();
							iGame = new GameImp(iStack, iHandleSource);
							gameController = new GameController(iGame, Commons.pathRootXML);
							gameController.startGame();
							break;
						case 2 : 
							iHandleSource = new HandleExcelData();
							iGame = new GameImp(iStack, iHandleSource);
							gameController = new GameController(iGame, Commons.pathRootExcel);
							gameController.startGame();
							break;
					}
					System.out.println();
					System.out.println("---- Start -----");
					System.out.println("* Nhập phím 2 để chơi: ");
					break;
				case 1: 
					gameController.rollback();
					doTutorial();
					break;
					
				case 2: 
					gameController.playGame();
					gameController.endGame();
					System.out.println();
					doTutorial();
					break;
					
				case 3: 
					System.out.print("Nhập row suggest: ");
					int row = inp.nextInt();
					System.out.print("Nhập col suggest: ");
					int col = inp.nextInt();
					System.out.println("Danh sách suggest: "+gameController.suggestGame(row, col));
					doTutorial();
					break;
				
				case 4: 
					gameController.saveGame();
					
				case 5: 
					System.out.println(gameController.exitGame());
					break;
				
			}
		}
	}
}
