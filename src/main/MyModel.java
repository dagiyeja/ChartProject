package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class MyModel extends AbstractTableModel{
	Vector<String> columnName;
	Vector<Vector> data=new Vector<Vector>();
	Connection con;
	
	public MyModel(Connection con) {
		this.con=con;
		columnName=new Vector<String>();
		columnName.add("score_id");
		columnName.add("학년");
		columnName.add("성별");
		columnName.add("국어");
		columnName.add("영어");
		columnName.add("수학");
		
		getList();
				
	}
	
	/*---------------------------------------------
	 * 모든 레코드 가져오기
	 * --------------------------------------------*/
	public void getList(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select * from score order by score_id asc";
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				Vector vec=new Vector(); //VO역할, 따라서 vec이 담을 데이터는 레코드 1건
				vec.add(rs.getString("score_id"));
				vec.add(rs.getString("grade"));
				vec.add(rs.getString("gender"));
				vec.add(rs.getString("kor"));
				vec.add(rs.getString("eng"));
				vec.add(rs.getString("math"));
				
				data.add(vec);
						
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	public int getColumnCount() {
		return columnName.size();
	}

	public int getRowCount() {
		return data.size();
	}
	

	public String getColumnName(int col) {
		return columnName.elementAt(col);
	}

	
	public boolean isCellEditable(int row, int col) {
		boolean flag=true;
		if(col==0){
			flag=false;
		}
		
		return flag;
	}
	
	//JTable에서 편집시 그 값이 적용되게 하기!!
	public void setValueAt(Object value, int row, int col) {
		Vector vec=data.elementAt(row);
		vec.set(col, value);
	}
	
	public Object getValueAt(int row, int col) {
		return data.elementAt(row).elementAt(col);
	}
	

}
