package service;

import java.sql.Connection;
import java.util.List;

import beans.Department;
import dao.DepartmentDao;
import utils.CloseableUtil;
import utils.DBUtil;

public class DepartmentService {
	//private static final int LIMIT_NUM = 1000;

	public List<Department> select() {

		//コネクション
		Connection connection = null;
		try {
			//コネクション取得
			connection = DBUtil.getConnection();
			//Daoを呼び出して処理実行
			DepartmentDao departmentDao = new DepartmentDao();
			List<Department> divisions = departmentDao.select(connection);
			//コミット
			DBUtil.commit(connection);

			return divisions;

		} catch (RuntimeException e) {
			DBUtil.rollback(connection);
			throw e;
		} catch (Error e) {
			DBUtil.rollback(connection);
			throw e;
		} finally {
			CloseableUtil.close(connection);
		}
	}
}
