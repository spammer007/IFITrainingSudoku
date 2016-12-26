package com.sudoku.service;

import java.util.Stack;

import com.sudoku.model.Nodes;

public class StackImp implements IStack {
	private Stack<Nodes> stack = new Stack<>();
	@Override
	public Stack<Nodes> nodeStack() {
		return stack;
	}
	
	@Override
	public synchronized Nodes delete( int position) {
        int size = nodeStack().size();
        if (position < size && position >=0) {
	        nodeStack().removeElementAt(position);
	        return nodeStack().elementAt(position);
        }
        return null;
    }
	
	@Override
	public synchronized Nodes getNode( int position) {
        int size = nodeStack().size();
        if (position < size && position >=0) {
	        return nodeStack().elementAt(position);
        }
        return null;
    }

}
