package service;

import java.sql.Connection;
import java.util.List;

import beans.User;
import beans.UserBranchDepartment;
import dao.UserBranchDepartmentDao;
import dao.UserDao;
import utils.CipherUtil;
import utils.CloseableUtil;
import utils.DBUtil;

public class UserService{

	//ユーザー情報登録
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

    //編集用ユーザー情報取得
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

    //ユーザー情報更新
    public void update(User user) {

        Connection connection = null;
        try {
        	//コネクション生成
            connection = DBUtil.getConnection();
            //パスワードの暗号化
            String encPassword = CipherUtil.encrypt(user.getPassword());
            //Beanへ上書き
            user.setPassword(encPassword);
            //Daoインスタンス生成
            UserDao userDao = new UserDao();
            //SQL実行
            userDao.update(connection, user);

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

    //全ユーザー情報取得
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

    //ユーザー情報更新
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