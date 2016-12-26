package com.sudoku.iostream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sudoku.exception.CustomException;
import com.sudoku.model.Nodes;
import com.sudoku.util.Commons;

public class HandleExcelData implements IHandleSource{
	private final static Logger log = Logger.getLogger(HandleExcelData.class);
	@Override
	public List<Nodes>  readFile(String path) {
		InputStream ExcelFileToRead = null;
		XSSFWorkbook wb = null;
		List<Nodes> liseNode = null;
		try {
			ExcelFileToRead = new FileInputStream(path);
			wb = new XSSFWorkbook(ExcelFileToRead);
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row; 
			XSSFCell cell;
			Nodes node ;
			liseNode = new ArrayList<>();
			Iterator<Row> rows = sheet.rowIterator();

			while (rows.hasNext()) {
				row=(XSSFRow) rows.next();
				Iterator<Cell> cells = row.cellIterator();
				
				while (cells.hasNext()) {
					cell=(XSSFCell) cells.next();
					if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
						node = new Nodes(cell.getRowIndex(), cell.getColumnIndex(), (int) cell.getNumericCellValue(), false);
						liseNode.add(node);
					}
				}
			}
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException : " , e);
		} catch (IOException e) {
			log.error("IOException : ", e);
		}
		
		try {
			validate(liseNode);
		} catch (CustomException e) {
			log.error(e);
		}
		return liseNode;
	}

	@Override
	public void modifyFile(String path, Stack<Nodes> nodesStack) {
		FileOutputStream fileOut = null;
		FileInputStream fileInt= null;
		try {
			fileInt = new FileInputStream(new File(path));
			XSSFWorkbook wb = new XSSFWorkbook(fileInt);
			XSSFSheet sheet = wb.getSheetAt(0);
			Cell cell = null;
			for (Nodes node : nodesStack) {
				cell = sheet.getRow(node.getRow()).getCell(node.getCol());
				cell.setCellValue(node.getValue());
			}
			fileOut = new FileOutputStream(new File(path));
			wb.write(fileOut);
			fileOut.flush();
		}catch (FileNotFoundException e) {
			log.error("FileNotFoundException : ", e);
		}catch (IOException e) {
			log.error("IOException : ", e);
		}finally {
			try {
				fileInt.close();
				fileOut.close();
			} catch (IOException e) {
				log.error("IOException : ", e);
			}
		}
		
	}

	@Override
	public boolean validate(List<Nodes> nodeList) throws CustomException{
		if (nodeList == null ) {
			return false;
		}
		return true;
	}
//	public static void main(String[] args) {
//		HandleExcelData han = new HandleExcelData();
//		Nodes node = new Nodes(8, 8, 1, true);
//		List<Nodes> nodes= new ArrayList<>();
//		nodes.add(node);
//		node = new Nodes(7,7,3,true);
//		nodes.add(node);
//		NodeListDTO nodeListDTO = new NodeListDTO();
//		nodeListDTO.setNodeList(nodes);
//		han.modifyFile(Commons.pathRootExcel, nodeListDTO);
//		try {
//		System.out.println(han.readFile(Commons.pathRootExcel).getNodeList().size());
//		} catch (NullPointerException e) {
//			log.error("NullPointerException", e);
//		}
//		
//	}

}
