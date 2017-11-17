import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;


public class EmployModel	extends AbstractTableModel {
	Object [][] data;
	Object [] columnName;
	
	EmployeeDAO emDao = new EmployeeDAO();
	EmployeeVO emVo;
	ArrayList <String> title;
	ArrayList <EmployeeVO> list;
	
	public EmployModel(){
		title = emDao.getColumnName();
		columnName = title.toArray();
		int columnCount = title.size();
		list = emDao.getEmployeetotal();
		int rowCount = list.size();
		data = new Object[rowCount][columnCount];
		for(int index=0 ; index<rowCount ; index++){
			emVo = list.get(index);
			data[index][0] = emVo.getNo();
			data[index][1] = emVo.getName();
			data[index][2] = emVo.getJobGrade();
			data[index][3] = emVo.getDepartment();
			data[index][4] = emVo.getEmail();
		}
	}
	
	public int getColumnCount(){
		if(columnName == null)
			return 0;
		else
			return columnName.length;
	}
	
	public int getRowCount(){
		if(data == null)
			return 0;
		else
			return data.length;
	}
	
	public Object getValueAt(int rowIndex, int columnIndex){
		return data[rowIndex][columnIndex];
	}
	
	public String getColumnName(int column){
		return (String)columnName[column];
	}
}
