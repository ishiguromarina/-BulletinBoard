package jp.alhinc.ishiguro_marina.service;

import static jp.alhinc.ishiguro_marina.utils.CloseableUtil.*;
import static jp.alhinc.ishiguro_marina.utils.DBUtil.*;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jp.alhinc.ishiguro_marina.beans.Message;
import jp.alhinc.ishiguro_marina.beans.UserMessage;
import jp.alhinc.ishiguro_marina.dao.MessageDao;
import jp.alhinc.ishiguro_marina.dao.UserMessageDao;

public class MessageService {

   /**
    * 投稿情報登録
    * @param Message 投稿情報
    */
    public void register(Message message) {

        Connection connection = null;

        try {
        	//コネクション取得
            connection = getConnection();
            //メッセージDaoインスタンス生成
            MessageDao messageDao = new MessageDao();
            //SQL実行
            messageDao.insert(connection, message);
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
    * 投稿情報取得
    * @param String 開始日時
    * @param String 終了日時
    * @param String カテゴリー
    * @return List<UserMessage> 投稿情報取得
    */
    public List<UserMessage> getMessage(String startDate, String endDate, String category) {

    	String startDateTime = "2019-01-01 00:00:00";
        Date date = new Date();
        String endDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        Connection connection = null;
        try {
        	//コネクション取得
            connection = getConnection();
            //メッセージDaoインスタンス生成
            UserMessageDao userMessageDao = new UserMessageDao();

            //startDate存在チェック
            if(!StringUtils.isEmpty(startDate)) {
            	startDateTime = startDate + " 00:00:00";
            }
            //endDate存在チェック
            if(!StringUtils.isEmpty(endDate)) {
            	endDateTime = endDate + " 23:59:59";
            }

            //SQL実行
            List<UserMessage> ret = userMessageDao.getUserMessages(connection, startDateTime, endDateTime, category);
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
     * 投稿情報削除
     * @param id 投稿ID
     */
	public void delete(int id) {

		Connection connection = null;

		try {
			//削除実行
			connection = getConnection();
			new MessageDao().delete(connection, id);
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