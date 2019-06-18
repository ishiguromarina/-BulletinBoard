package service;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import beans.Comment;
import beans.UserComment;
import dao.CommentDao;
import dao.UserCommentDao;

public class CommentService {

	private static final int LIMIT_NUM = 1000;

	//メッセージ登録
    public void register(Comment comment) {

        Connection connection = null;

        try {
        	//コネクション取得
            connection = getConnection();
            //メッセージDaoインスタンス生成
            CommentDao commentDao = new CommentDao();
            //SQL実行
            commentDao.insert(connection, comment);
            //コミット実行
            commit(connection);

        } catch (RuntimeException e) {
            rollback(connection);
            throw e;
        } catch (Error e) {
            rollback(connection);
            throw e;
        } finally {
        	//コネクションクローズ
            close(connection);
        }
    }

    public ArrayList<UserComment> getUserComments() {

        Connection connection = null;
        try {
        	//コネクション取得
            connection = getConnection();
            //メッセージDaoインスタンス生成
            UserCommentDao userCommentDao = new UserCommentDao();
            //SQL実行
            ArrayList<UserComment> ret = userCommentDao.getUserComments(connection, LIMIT_NUM);
            //コミット実行
            commit(connection);
            //リスト返却
            return ret;

        } catch (RuntimeException e) {
            rollback(connection);
            throw e;
        } catch (Error e) {
            rollback(connection);
            throw e;
        } finally {
            close(connection);
        }
    }
}