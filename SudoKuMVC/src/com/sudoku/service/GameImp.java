package com.sudoku.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.sudoku.model.Nodes;
import com.sudoku.iostream.IHandleSource;
import com.sudoku.model.Game;

public class GameImp implements IGame{
	private Game table;
	private IStack iStack;
	private IHandleSource iHandleSource;
	private final static Logger log = Logger.getLogger(GameImp.class);
	public GameImp() {
		super();
		
	}
	
	public GameImp(IStack iStack) {
		super();
		this.iStack = iStack;
	}

	public void setiStack(IStack iStack) {
		this.iStack = iStack;
	}
	
	public GameImp(IStack iStack, IHandleSource iHandleSource) {
		super();
		this.iStack = iStack;
		this.iHandleSource = iHandleSource;
	}

	public void setiHandleSource(IHandleSource iHandleSource) {
		this.iHandleSource = iHandleSource;
	}

	@Override
	public void initGame(String pathSource) {
		Scanner inp = new Scanner(System.in);
		table = new Game();
		
		System.out.println(">> Ấn phím 1 để chơi game từ đầu, phím 2 để load lại game.");
		int l = inp.nextInt();
		List<Nodes> nodeList = null;
		switch (l) {
			case 1:  
				nodeList = new ArrayList<>();
				for (Nodes node : iHandleSource.readFile(pathSource)) {
					if (!node.isStatus()) {
						nodeList.add(node);
					}
				}
				break;
			case 2: 
				nodeList = iHandleSource.readFile(pathSource);
				break;
		}
		
		System.out.print("Nhập size: ");
		int s = inp.nextInt();
		table.setSize(s);
		table.setTableGame(new Integer[s*s][s*s]);
		if ( table.getSize() >= 3) {
			for (int i=0; i < table.getSize() * table.getSize(); i++) {
				for (int j=0; j < table.getSize() * table.getSize(); j++) {
					table.getTableGame()[i][j] = 0;
				}
			}
		}
		
		for (Nodes node : nodeList) {
			table.getTableGame()[node.getRow()][node.getCol()] = node.getValue();
//			log.info(node.getRow());
		}
		displayTable();
	}
	
	@Override
	public boolean runGame(Nodes node) {
		if ( !validate(node)) {
			System.out.println("Đã trùng");
			return false;
		} 
		else {
			table.getTableGame()[node.getRow()][node.getCol()] = node.getValue();
			iStack.nodeStack().push(node); //push vào stack
			displayTable();
			return true;
		}
	}
	
	@Override
	public void displayTable() {
		for (int i = 0; i < table.getSize() * table.getSize() ; i++) {
			System.out.println();
			for (int j = 0; j < table.getSize() * table.getSize(); j++) {
				System.out.print(table.getTableGame()[i][j] + "  ");
			}
		}
	}

	@Override
	public Nodes input() {
		Scanner inp = new Scanner(System.in);
		System.out.print("Nhập row: ");
		int row = inp.nextInt();
		System.out.print("Nhập col: ");
		int col = inp.nextInt();
		System.out.print("Nhập Value: ");
		int val = inp.nextInt();
		boolean status = true;
		Nodes node = new Nodes(row, col, val, status);
		return node;
	}

	@Override
	public boolean validate(Nodes node) {
		int r = node.getRow();
		int c = node.getCol();
		int v = node.getValue();
		int size = table.getSize();
		
		//kiểm tra trong block
		Integer arr[][] = table.getTableGame();
		
		for (int i = r-r%3; i < r-r%3+2; i++) {
			for (int j = c-c%3; j < c-c%3+2; j++) {
				if (arr[i][j] == v && r!=i && c!=j){
					return false;
				}
			}
		}
		
		//kiểm tra hàng ngang
		for (int i = 0; i < size * size; i++) {
			if (c != i) {
				if (arr[r][i] == v){
					return false;
				}
			}
		}
		
		//kiểm tra hàng dọc
		for (int i = 0; i < size * size; i++) {
			if (r != i) {
				if (arr[i][c] == v){
					return false;
				}
			}
		}
		return true;
	}
	
	
	
	@Override
	public void rollback() {
		// TODO Auto-generated method stub
		if (iStack.nodeStack().size() > 0) {
			Nodes node = iStack.nodeStack().pop();
			table.getTableGame()[node.getRow()][node.getCol()] = 0;
			displayTable();
		} else {
			System.out.println("Không rollback được nữa!");
		}
	}

	@Override
	public String suggest(int row, int col) {
		Nodes node;
		
		//khởi tạo mảng có kích cỡ size bình phương
		Integer value[] = new Integer[table.getSize() * table.getSize()];
		
		//gán giá trị vào mảng từ 1 đến size bình phương
		for (int i = 0; i < value.length; i++) {
			value[i] = i+1;
		}
		
		//tìm các số có thể suggest
		String sug = "";
		for (int i = 0; i < value.length; i++) {
			node = new Nodes(row, col, value[i], true);
			if (validate(node)) {
				sug += value[i] + "  ";
			}
		}
		
		return sug;
	}
	
	@Override
	public void saveGame(String path) {
		try {
			iHandleSource.modifyFile(path, iStack.nodeStack());
		} catch (Exception e) {
			log.error("Ghi file có vấn đề", e);
		}
	}

	@Override
	public void endGame() {
//		int a =0;
//		int t = table.getSize() * (table.getSize() + 1) / 2;
//		
//		for (int i = 0; i < table.getSize() * table.getSize() ; i++) {
//			for (int j = 0; j < table.getSize() * table.getSize(); j++) {
//				a += table.getTableGame()[i][j]; 
//				if (a == t){
//					System.out.println("Success!");
//				}
//			}
//		}
	}
	
	@Override
	public String exitGame() {
		System.exit(0);
		return "Bye Bye";
	}
}
