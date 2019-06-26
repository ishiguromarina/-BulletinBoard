package jp.alhinc.ishiguro_marina.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.alhinc.ishiguro_marina.beans.Department;
import jp.alhinc.ishiguro_marina.exception.SQLRuntimeException;
import jp.alhinc.ishiguro_marina.utils.CloseableUtil;

public class DepartmentDao {

   /**
    * 部署・役職情報取得
    * @param Connection コネクション
    * @return List<Department> 部署・役職情報
    */
	public List<Department> select (Connection connection) {

		List<Department> departmentList = new ArrayList<Department>();
		PreparedStatement ps = null;

		try {
			//SQL作成
			String sql = "SELECT * FROM departments";
			ps = connection.prepareStatement(sql);

			//SQL実行
			ResultSet rs = ps.executeQuery();

			//取得できた件数分ループ開始
			while(rs.next()) {

				//Beanへ格納
	            Department department = new Department();
	            department.setId(rs.getInt("id"));
	            department.setName(rs.getString("name"));

	            //返却用リストへ格納
	            departmentList.add(department);
			}

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			CloseableUtil.close(ps);
		}
		return departmentList;

	}
}