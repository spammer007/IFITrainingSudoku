package com.sudoku.service;

import java.util.Stack;

import com.sudoku.model.Nodes;

public interface IStack {
	Stack<Nodes> nodeStack();
	Nodes getNode( int position);
	Nodes delete( int position);
}
