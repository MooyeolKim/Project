import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAO {
	public EmployeeVO getEmployeeregiste(EmployeeVO evo) throws Exception{
		String dml = "insert into employee"
				+ "(name, jobGrade, department, email)"
				+ "values"
				+ "(?, ?, ?, ?)";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		EmployeeVO retval = null;
		
		try{
			con = DBUtil.getConnection();
			
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, evo.getName());
			pstmt.setString(2, evo.getJobGrade());
			pstmt.setInt(3, evo.getDepartment());
			pstmt.setString(4, evo.getEmail());
			
			int i = pstmt.executeUpdate();
			retval = new EmployeeVO();
			retval.setStatus(i + " ");
		} catch(SQLException e){
			System.out.println("e = [" + e + "]");
		} catch(Exception e){
			System.out.println("e = [" + e + "]");
		} finally{
			try{
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(SQLException e){
				
			}
		}
		return retval;
	}
	
	public EmployeeVO getEmployeeCheck(int no, String name) throws Exception{
		String dml = "select * from employee where no = ? and name = ?";
		
		Connection 		  con    = null;
		PreparedStatement pstmt  = null;
		ResultSet 		  rs     = null;
		EmployeeVO 		  retval = null;
		
		try{
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, no);
			pstmt.setString(2, name);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				retval = new EmployeeVO(rs.getInt(1), 
										rs.getString(2), 
										rs.getString(3), 
										rs.getInt(4), 
										rs.getString(5));
			}
		} catch(SQLException se){
			System.out.println(se);
		} catch(Exception e){
			System.out.println(e);
		} finally{
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(SQLException se){
				
			}
		}
		
		return retval;
	}
	
	public EmployeeVO getEmployeeNo(int no) throws Exception{
		String dml = "select * from employee where no = ?";
		
		Connection 		  con    = null;
		PreparedStatement pstmt  = null;
		ResultSet 		  rs     = null;
		EmployeeVO 		  retval = null;
		
		try{
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if(rs.next()){
				retval = new EmployeeVO(rs.getInt(1), 
						rs.getString(2), 
						rs.getString(3), 
						rs.getInt(4), 
						rs.getString(5));
			}
		} catch(SQLException se){
			System.out.println(se);
		} catch(Exception e){
			System.out.println(e);
		} finally{
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(SQLException se){
			}
		}
		return retval;
	}
	
	public EmployeeVO getEmployeeName(String name) throws Exception{
		String dml = "select * from employee where name = ?";
		
		Connection 		  con    = null;
		PreparedStatement pstmt  = null;
		ResultSet 		  rs     = null;
		EmployeeVO 		  retval = null;
		
		try{
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if(rs.next()){
				retval = new EmployeeVO(rs.getInt(1), 
						rs.getString(2), 
						rs.getString(3), 
						rs.getInt(4), 
						rs.getString(5));
			}
		} catch(SQLException se){
			System.out.println(se);
		} catch(Exception e){
			System.out.println(e);
		} finally{
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(SQLException se){
			}
		}
		return retval;
	}
	
	public ArrayList<EmployeeVO> getEmployeetotal(){
		ArrayList<EmployeeVO> list = new ArrayList<EmployeeVO>();
		
		String tml = "select * from employee";
		Connection 		  con    = null;
		PreparedStatement pstmt  = null;
		ResultSet 		  rs     = null;
		EmployeeVO 		  emVo   = null;
		
		try{
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();
			while(rs.next()){
				emVo = new EmployeeVO(rs.getInt(1), 
									  rs.getString(2), 
									  rs.getString(3), 
									  rs.getInt(4), 
									  rs.getString(5));
				list.add(emVo);
			}
		} catch(SQLException se){
			System.out.println(se);
		} catch(Exception e){
			System.out.println(e);
		} finally{
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(SQLException se){
			}
		}
		return list;
	}
	
	public ArrayList<String> getColumnName(){
		ArrayList<String> columnName = new ArrayList<String>();
		
		String sql = "select * from employee";
		Connection 		  con    = null;
		PreparedStatement pstmt  = null;
		ResultSet 		  rs     = null;
		ResultSetMetaData rsmd   = null;
		
		try{
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			
			int cols = rsmd.getColumnCount();
			
			for(int i=1 ; i<=cols ; i++){
				columnName.add(rsmd.getColumnClassName(i));
			}
		} catch(SQLException se){
			System.out.println(se);
		} catch(Exception e){
			System.out.println(e);
		} finally{
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(SQLException se){
			}
		}
		return columnName;
	}
}
