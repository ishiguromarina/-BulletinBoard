package jp.alhinc.ishiguro_marina.service;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.alhinc.ishiguro_marina.beans.User;
import jp.alhinc.ishiguro_marina.beans.UserBranchDepartment;
import jp.alhinc.ishiguro_marina.dao.UserBranchDepartmentDao;
import jp.alhinc.ishiguro_marina.dao.UserDao;
import jp.alhinc.ishiguro_marina.utils.CipherUtil;
import jp.alhinc.ishiguro_marina.utils.CloseableUtil;
import jp.alhinc.ishiguro_marina.utils.DBUtil;

public class UserService{

   /**
    * ユーザー情報登録
    * @param User ユーザー情報
    */
	public void insert(User user) {

		//コネクション
		Connection connection = null;
        try {

    		//コネクション取得
            connection = DBUtil.getConnection();
            //パスワード暗号化
            String encPassword = CipherUtil.encrypt(user.getPassword());
            //暗号化したパスワードをBeanへ上書き
            user.setPassword(encPassword);
            //Daoを呼び出して処理実行
            UserDao userDao = new UserDao();
            userDao.insert(connection, user);
            //コミット
            DBUtil.commit(connection);

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

   /**
    * 編集用ユーザー情報取得
    * @param int ID
    * @return User ログインユーザー情報
    */
    public User getUser(int userId) {

        Connection connection = null;
        try {
        	//コネクション取得
            connection = DBUtil.getConnection();
            //Daoのインスタンス生成
            UserDao userDao = new UserDao();
            //SQL実行
            User user = userDao.getUser(connection, userId);
            //コミット実行
            DBUtil.commit(connection);
            //編集用ユーザー情報返却
            return user;

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

   /**
    * ユーザー情報更新
    * @param User ログインユーザー情報
    */
    public void update(User user) {

        Connection connection = null;
        try {
        	//コネクション生成
            connection = DBUtil.getConnection();

            //パスワードの存在確認
            if(StringUtils.isEmpty(user.getPassword())) {
                //Daoインスタンス生成
                UserDao userDao = new UserDao();
                //SQL実行
                userDao.updateWithoutPass(connection, user);

            }else {
            //パスワードの暗号化
            String encPassword = CipherUtil.encrypt(user.getPassword());
            //Beanへ上書き
            user.setPassword(encPassword);
            //Daoインスタンス生成
            UserDao userDao = new UserDao();
            //SQL実行
            userDao.update(connection, user);

            }

            DBUtil.commit(connection);
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

   /**
    * ユーザー一覧情報取得
    * @return List<UserBranchDepartment> ユーザー一覧情報
    */
    public List<UserBranchDepartment> getUsers() {

        Connection connection = null;
        try {
        	//コネクション取得
            connection = DBUtil.getConnection();
            //Daoのインスタンス生成
            UserBranchDepartmentDao userBranchDepartmentDao = new UserBranchDepartmentDao();
            //SQL実行
            List<UserBranchDepartment> users = userBranchDepartmentDao.getUsers(connection);
            //コミット実行
            DBUtil.commit(connection);
            //編集用ユーザー情報返却
            return users;

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

   /**
    * ユーザーの停止・復活
    * @param int ID
    * @param int 停止・復活フラグ
    */
    public void updateStatus(int id, int isStopped) {

        Connection connection = null;
        try {
        	//コネクション生成
            connection = DBUtil.getConnection();
            //Daoインスタンス生成
            UserDao userDao = new UserDao();
            //SQL実行
            userDao.updateStatus(connection, id, isStopped);

            DBUtil.commit(connection);
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