package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Branch;
import exception.SQLRuntimeException;
import utils.CloseableUtil;

public class BranchDao {

	public List<Branch> select (Connection connection) {

		List<Branch> branches = new ArrayList<Branch>();
		PreparedStatement ps = null;

		try {
			//SQL組み立て
			String sql = "SELECT * FROM branches";
			ps = connection.prepareStatement(sql);

			//SQL実行
			ResultSet rs = ps.executeQuery();

			//取得できた件数分ループ開始
			while(rs.next()) {

				//Beanへ格納
	            Branch branch = new Branch();
	            branch.setId(rs.getInt("id"));
	            branch.setName(rs.getString("name"));

	            //返却用リストへ格納
				branches.add(branch);
			}

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			CloseableUtil.close(ps);
		}
		return branches;

	}
}