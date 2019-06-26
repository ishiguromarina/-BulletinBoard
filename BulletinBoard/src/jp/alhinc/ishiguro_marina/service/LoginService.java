package jp.alhinc.ishiguro_marina.service;

import java.sql.Connection;

import jp.alhinc.ishiguro_marina.beans.User;
import jp.alhinc.ishiguro_marina.dao.UserDao;
import jp.alhinc.ishiguro_marina.utils.CipherUtil;
import jp.alhinc.ishiguro_marina.utils.CloseableUtil;
import jp.alhinc.ishiguro_marina.utils.DBUtil;

public class LoginService{

   /**
    * ログイン情報取得
    * @param String ログインID
    * @param String パスワード
    * @return User ログインユーザー情報
    */
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