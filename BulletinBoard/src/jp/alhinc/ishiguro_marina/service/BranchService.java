package jp.alhinc.ishiguro_marina.service;

import java.sql.Connection;
import java.util.List;

import jp.alhinc.ishiguro_marina.beans.Branch;
import jp.alhinc.ishiguro_marina.dao.BranchDao;
import jp.alhinc.ishiguro_marina.utils.CloseableUtil;
import jp.alhinc.ishiguro_marina.utils.DBUtil;

public class BranchService {

   /**
    * 支店情報取得
    * @return List<Branch> 支店情報
    */
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
