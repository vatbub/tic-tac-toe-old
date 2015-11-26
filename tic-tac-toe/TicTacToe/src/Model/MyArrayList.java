package Model;

import java.util.ArrayList;
import java.util.Collection;

public class MyArrayList {
	
	private ArrayList<ArrayList<Model.Player>> cellUsedByPlayerList;
	int rowCount;
	int columnCount;
	
	public MyArrayList() {
		cellUsedByPlayerList=new ArrayList<ArrayList<Model.Player>>();
	}

	public MyArrayList(int rowCount, int columnCount) {
		this.rowCount=rowCount;
		this.columnCount=columnCount;
		
		cellUsedByPlayerList=new ArrayList<ArrayList<Model.Player>>(rowCount);
		System.out.println(cellUsedByPlayerList.size());
		for (int i=0;i<rowCount;i++){
			cellUsedByPlayerList.set(i, new ArrayList<Model.Player>(columnCount));
		}
	}
	
	public void addColumn(){
		for (int i=0;i<rowCount;i++){
			cellUsedByPlayerList.get(i).add(null);
		}
	}
	
	public void addRow(){
		cellUsedByPlayerList.add(new ArrayList<Model.Player>(columnCount));
	}
	
	public Model.Player getPlayerAt(int row, int col){
		return cellUsedByPlayerList.get(row).get(col);
	}
	
	public void setPlayerAt(Player value, int row, int col){
		cellUsedByPlayerList.get(row).set(col, value);
	}
}
