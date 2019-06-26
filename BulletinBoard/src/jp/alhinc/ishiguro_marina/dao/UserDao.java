package jp.alhinc.ishiguro_marina.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.alhinc.ishiguro_marina.beans.User;
import jp.alhinc.ishiguro_marina.exception.NoRowsUpdatedRuntimeException;
import jp.alhinc.ishiguro_marina.exception.SQLRuntimeException;
import jp.alhinc.ishiguro_marina.utils.CloseableUtil;

public class UserDao {

	   /**
	    * ユーザー情報登録
	    * @param Connection コネクション
	    * @param User 登録ユーザー情報
	    */
	   public void insert(Connection connection, User user) {

		   //SQL文字列
	        PreparedStatement ps = null;

	        try {

	        	//SQL文字列生成
	            StringBuilder sql = new StringBuilder();
	            sql.append("INSERT INTO users ( ");
	            sql.append("login_id");
	            sql.append(", password");
	            sql.append(", name");
	            sql.append(", branch_id");
	            sql.append(", department_id");
	            sql.append(", is_stopped");
	            sql.append(", created_date");
	            sql.append(", updated_date");
	            sql.append(") VALUES (");
	            sql.append("?"); // login_id
	            sql.append(", ?"); // password
	            sql.append(", ?"); // name
	            sql.append(", ?"); // brandh_id
	            sql.append(", ?"); // department_id
	            sql.append(", 1"); // is_stopped
	            sql.append(", CURRENT_TIMESTAMP"); // created_date
	            sql.append(", CURRENT_TIMESTAMP"); // updated_date
	            sql.append(")");
	            ps = connection.prepareStatement(sql.toString());

	            //パラメータ格納
	            ps.setString(1, user.getLoginId());
	            ps.setString(2, user.getPassword());
	            ps.setString(3, user.getName());
	            ps.setInt(4, user.getBranchId());
	            ps.setInt(5, user.getDepartmentId());

	            //SQL実行
	            ps.executeUpdate();

	        } catch (SQLException e) {
	            throw new SQLRuntimeException(e);
	        } finally {
	            CloseableUtil.close(ps);
	        }
	    }

	   /**
	    * ログイン情報取得
	    * @param Connection コネクション
	    * @param String ログインID
	    * @param String パスワード
	    * @return User ログインユーザー情報
	    */
	   public User getUser(Connection connection, String loginId,
		        String password) {

		   //SQL文字列
		    PreparedStatement ps = null;
	        //SQL取得用ResultSet
	        ResultSet rs = null;

		    try {
		    	//SQL文字列作成
		    	String sql = "SELECT * FROM users WHERE login_id = ? AND password = ?";

		        //パラメータ設定
		        ps = connection.prepareStatement(sql);
		        ps.setString(1, loginId);
		        ps.setString(2, password);

		        //SQL実行
		        rs = ps.executeQuery();

	        	//UserBeanのリストへ格納
	            List<User> userList = toUserList(rs);

		        //ログイン情報が取得できなかった場合
		        if (userList.isEmpty()) {
		            return null;

		        //ログイン情報が2件以上取得できた場合
		        } else if (2 <= userList.size()) {
		            throw new IllegalStateException("2 <= userList.size()");

		        //ログイン情報が正常に取得できた場合
		        } else {
		            return userList.get(0);
		        }

		    } catch (SQLException e) {
		        throw new SQLRuntimeException(e);
		    } finally {
		        CloseableUtil.close(ps);
		        CloseableUtil.close(rs);
		    }
		}

	   /**
	    * 編集用ユーザー情報取得
	    * @param Connection コネクション
	    * @param String ID
	    * @return User 編集ユーザー情報
	    */
	   public User getUser(Connection connection, int id) {

		    //SQL文字列
		    PreparedStatement ps = null;
	        //SQL取得用ResultSet
	        ResultSet rs = null;

		    try {
		    	//SQL作成
		        String sql = "SELECT * FROM users WHERE id = ?";
		        ps = connection.prepareStatement(sql);

		        //パラメータ設定
		        ps.setInt(1, id);

		        //SQL実行
		        rs = ps.executeQuery();

		        //リストへ詰め替え
		        List<User> userList = toUserList(rs);

		        if (userList.isEmpty() == true) {
		            return null;
		        } else if (2 <= userList.size()) {
		            throw new IllegalStateException("2 <= userList.size()");
		        } else {
		            return userList.get(0);
		        }
		    } catch (SQLException e) {
		        throw new SQLRuntimeException(e);
		    } finally {
		    	CloseableUtil.close(ps);
		    	CloseableUtil.close(rs);
		    }
		}

	   /**
	    * ユーザー情報更新
	    * @param Connection コネクション
	    * @param User 更新用ユーザー情報
	    */
	    public void update(Connection connection, User user) {

	        PreparedStatement ps = null;
	        try {
	        	//SQL文字列の作成
	            StringBuilder sql = new StringBuilder();
	            sql.append("UPDATE users SET");
	            sql.append("  login_id = ?");
	            sql.append(", name = ?");
	            sql.append(", password = ?");
	            sql.append(", branch_id = ?");
	            sql.append(", department_id = ?");
	            sql.append(", updated_date = CURRENT_TIMESTAMP");
	            sql.append(" WHERE");
	            sql.append(" id = ?");

	            //SQL文字列をPSに格納
	            ps = connection.prepareStatement(sql.toString());

	            //パラメータ格納
	            ps.setString(1, user.getLoginId());
	            ps.setString(2, user.getName());
	            ps.setString(3, user.getPassword());
	            ps.setInt(4, user.getBranchId());
	            ps.setInt(5, user.getDepartmentId());
	            ps.setInt(6, user.getId());

	            //SQL実行
	            ps.executeUpdate();

	        } catch (SQLException e) {
	            throw new SQLRuntimeException(e);
	        } finally {
	        	CloseableUtil.close(ps);
	        }
	    }

		   /**
		    * ユーザー情報更新（パスワードなし）
		    * @param Connection コネクション
		    * @param User 更新用ユーザー情報
		    */
		    public void updateWithoutPass(Connection connection, User user) {

		        PreparedStatement ps = null;
		        try {
		        	//SQL文字列の作成
		            StringBuilder sql = new StringBuilder();
		            sql.append("UPDATE users SET");
		            sql.append("  login_id = ?");
		            sql.append(", name = ?");
		            sql.append(", branch_id = ?");
		            sql.append(", department_id = ?");
		            sql.append(", updated_date = CURRENT_TIMESTAMP");
		            sql.append(" WHERE");
		            sql.append(" id = ?");

		            //SQL文字列をPSに格納
		            ps = connection.prepareStatement(sql.toString());

		            //パラメータ格納
		            ps.setString(1, user.getLoginId());
		            ps.setString(2, user.getName());
		            ps.setInt(3, user.getBranchId());
		            ps.setInt(4, user.getDepartmentId());
		            ps.setInt(5, user.getId());

		            //SQL実行
		            ps.executeUpdate();

		        } catch (SQLException e) {
		            throw new SQLRuntimeException(e);
		        } finally {
		        	CloseableUtil.close(ps);
		        }
		    }

		/**
	    * ユーザーのステータス更新
	    * @param Connection コネクション
	    * @param int ID
	    * @param int 停止復活フラグ
	    */
	    public void updateStatus(Connection connection, int id, int isStopped) {

	        PreparedStatement ps = null;
	        try {
	        	//SQL文字列の作成
	            StringBuilder sql = new StringBuilder();
	            sql.append("UPDATE users SET");
	            sql.append("  is_stopped = ?");
	            sql.append(" WHERE");
	            sql.append(" id = ?");

	            //SQL文字列をPSに格納
	            ps = connection.prepareStatement(sql.toString());

	            //パラメータ格納
	            ps.setInt(1, isStopped);
	            ps.setInt(2, id);

	            //SQL実行
	            int count = ps.executeUpdate();

	            //update対象が存在しなかった場合
	            if (count == 0) {
	                throw new NoRowsUpdatedRuntimeException();
	            }

	        } catch (SQLException e) {
	            throw new SQLRuntimeException(e);
	        } finally {
	        	CloseableUtil.close(ps);
	        }
	    }

	   /**
	    * ユーザー情報をリストへ格納
	    * @param ResultSet rs
	    * @return List<User> ログインユーザー情報
	    */
	    private List<User> toUserList(ResultSet rs) throws SQLException{

	    	//リスト作成
	    	List<User> userList = new ArrayList<User>();
	        //取得できた分繰り返し
	        while (rs.next()) {

	        	//UserBeanのインスタンス生成
	            User user = new User();

	            //UserBeanに格納
	            user.setId(rs.getInt("id"));
	            user.setLoginId(rs.getString("login_id"));
	            user.setName(rs.getString("name"));
	            user.setBranchId(rs.getInt("branch_id"));
	            user.setDepartmentId(rs.getInt("department_id"));
	            user.setPassword(rs.getString("password"));
	            user.setIsStopped(rs.getInt("is_stopped"));
	            user.setCreatedDate(rs.getTimestamp("created_date"));
	            user.setUpdatedDate(rs.getTimestamp("updated_date"));

	            //UserBeanに格納したログイン情報をリストへ追加
	            userList.add(user);
	        }
	        return userList;
	    }
  }