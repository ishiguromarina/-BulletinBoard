package service;

import java.sql.Connection;

import beans.User;
import dao.UserDao;
import utils.CipherUtil;
import utils.CloseableUtil;
import utils.DBUtil;

public class LoginService{

	//ログイン処理
    public User login(String loginId, String password) {

        Connection connection = null;

        try {
        	//コネクション取得
            connection = DBUtil.getConnection();
            //UserDao初期化
            UserDao userDao = new UserDao();
            //password暗号化
            String encPassword = CipherUtil.encrypt(password);
            //ログイン情報取得
            User user = userDao.getUser(connection, loginId, encPassword);
            //commit実行
            DBUtil.commit(connection);
            //ログイン情報返却
            return user;

        } catch (RuntimeException e) {
        	DBUtil.rollback(connection);
            throw e;
        } catch (Error e) {
        	DBUtil.rollback(connection);
            throw e;
        } finally {
        	//コネクションクローズ
        	CloseableUtil.close(connection);
        }
    }
}