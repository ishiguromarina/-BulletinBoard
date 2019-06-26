package jp.alhinc.ishiguro_marina.service;

import static jp.alhinc.ishiguro_marina.utils.CloseableUtil.*;
import static jp.alhinc.ishiguro_marina.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import jp.alhinc.ishiguro_marina.beans.Comment;
import jp.alhinc.ishiguro_marina.beans.UserComment;
import jp.alhinc.ishiguro_marina.dao.CommentDao;
import jp.alhinc.ishiguro_marina.dao.UserCommentDao;

public class CommentService {

   /**
    * コメント情報登録
    * @param Comment コメント
    */
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

   /**
    * コメント情報取得
    * @return List<UserComment> コメント情報
    */
    public List<UserComment> getUserComments() {

        Connection connection = null;
        try {
        	//コネクション取得
            connection = getConnection();
            //メッセージDaoインスタンス生成
            UserCommentDao userCommentDao = new UserCommentDao();
            //SQL実行
            List<UserComment> ret = userCommentDao.getUserComments(connection);
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

    /**
     * コメント情報削除
     * @param id コメントID
     */
	public void delete(int id) {

		Connection connection = null;

		try {
			//削除実行
			connection = getConnection();
			new CommentDao().delete(connection, id);
			//コミット
			commit(connection);

		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch(Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}
}