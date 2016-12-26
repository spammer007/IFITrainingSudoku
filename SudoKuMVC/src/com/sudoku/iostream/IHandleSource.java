package com.sudoku.iostream;

import java.util.List;
import java.util.Stack;

import com.sudoku.exception.CustomException;
import com.sudoku.model.Nodes;

public interface IHandleSource {
	List<Nodes> readFile(String path);
	void modifyFile(String path, Stack<Nodes> nodesStack);
	boolean validate(List<Nodes> nodeList) throws CustomException;
}
