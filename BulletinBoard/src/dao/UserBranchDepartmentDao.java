package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.UserBranchDepartment;
import exception.SQLRuntimeException;
import utils.CloseableUtil;

public class UserBranchDepartmentDao {

	    private List<UserBranchDepartment> toUserList(ResultSet rs) throws SQLException{

	    	//リスト作成
	    	List<UserBranchDepartment> userList = new ArrayList<UserBranchDepartment>();
	        //取得できた分繰り返し
	        while (rs.next()) {

	        	//UserBeanのインスタンス生成
	            UserBranchDepartment user = new UserBranchDepartment();

	            //UserBeanに格納
	            user.setId(rs.getInt("id"));
	            user.setLoginId(rs.getString("login_id"));
	            user.setName(rs.getString("name"));
	            user.setBranchId(rs.getInt("branch_id"));
	            user.setDepartmentId(rs.getInt("department_id"));
	            user.setPassword(rs.getString("password"));
	            user.setIsStopped(rs.getInt("is_stopped"));
	            user.setCreatedDate(rs.getTimestamp("created_date"));
	            user.setBranchName(rs.getString("branchName"));
	            user.setDepartmentName(rs.getString("departmentName"));

	            //UserBeanに格納したログイン情報をリストへ追加
	            userList.add(user);
	        }
	        return userList;
	    }

	   public List<UserBranchDepartment> getUsers(Connection connection) {

		   //SQL文字列
		   PreparedStatement ps = null;
		     //SQL取得用ResultSet
		     ResultSet rs = null;

		   try {
		     //SQL作成
	            StringBuilder sql = new StringBuilder();

	            //SQL文字列
	            sql.append("SELECT ");
	            sql.append("users.id,  ");
	            sql.append("users.login_id, ");
	            sql.append("users.password, ");
	            sql.append("users.name, ");
	            sql.append("users.is_stopped, ");
	            sql.append("users.branch_id, ");
	            sql.append("users.department_id, ");
	            sql.append("branches.name as branchName, ");
	            sql.append("departments.name as departmentName, ");
	            sql.append("users.created_date ");
	            sql.append("FROM users ");
	            sql.append("INNER JOIN branches ");
	            sql.append("ON users.branch_id = branches.id ");
	            sql.append("INNER JOIN departments ");
	            sql.append("ON users.department_id = departments.id ");
	            sql.append("ORDER BY created_date DESC");

	            //SQL実行
	            ps = connection.prepareStatement(sql.toString());
	            //SQL実行
	            rs = ps.executeQuery();
	            //リストへ詰め替え
		       List<UserBranchDepartment> userList = toUserList(rs);

		       return userList;

		   } catch (SQLException e) {
		       throw new SQLRuntimeException(e);
		   } finally {
		     CloseableUtil.close(ps);
		     CloseableUtil.close(rs);
		   }
	   }
  }