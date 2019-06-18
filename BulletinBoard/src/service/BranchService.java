package service;

import java.sql.Connection;
import java.util.List;

import beans.Branch;
import dao.BranchDao;
import utils.CloseableUtil;
import utils.DBUtil;

public class BranchService {
	//private static final int LIMIT_NUM = 1000;

	public List<Branch> select() {

		//コネクション
		Connection connection = null;

		try {
			//コネクション取得
			connection = DBUtil.getConnection();
			//Dao呼び出して処理実行
			BranchDao branchDao = new BranchDao();
			List<Branch> branches = branchDao.select(connection);
			//コミット
			DBUtil.commit(connection);

			return branches;

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
