package service;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import beans.Message;
import beans.UserMessage;
import dao.MessageDao;
import dao.UserMessageDao;

public class MessageService {

	//メッセージ登録
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

    public ArrayList<UserMessage> getMessage(String startDate, String endDate, String category) {

    	String startDateTime = "2019-01-01 00:00:00";
        Date date = new Date();
        String endDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);

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
            ArrayList<UserMessage> ret = userMessageDao.getUserMessages(connection, startDateTime, endDateTime, category);
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