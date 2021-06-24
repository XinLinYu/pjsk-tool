package com.projectsekai.dao;

import com.projectsekai.config.DBConfig;
import com.projectsekai.entity.PjskCard;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName PjskCardMapper
 * @description
 * @date 2021/6/23 0023 下午 4:00
 **/
@Mapper
public class PjskCardDao {
    private static Connection connection = null;

    public static Connection getConnectionInstance() {
        if (connection == null) {
            synchronized (Connection.class) {
                if (connection == null) {
                    connection = DBConfig.getConnection();
                }
            }
        }
        return connection;
    }

    /**
     * @param pjskCard
     * @return java.util.List<com.projectsekai.entity.PjskCard>
     * @Author XinlindeYu
     * @description 查卡
     * @Date 下午 4:52 2021/6/23 0023
     **/
    public List<PjskCard> getPjskCardList(PjskCard pjskCard) {
        List<PjskCard> cardList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            //2、创建连接
            connection = getConnectionInstance();
            //3.写sql语句
            String sql = "SELECT CARD_CHARACTER_NAME cardCharacterName,\n" +
                    "               CARD_RARITY rarity,CARD_PREFIX prefix,CARD_SKILL_NAME cardSkillName\n" +
                    "        FROM PJSK_CARD\n" +
                    "        WHERE CARD_CHARACTER_NAME LIKE ?\n" +
                    "          AND CARD_RARITY = ?";
            //4.获得statement对象
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + pjskCard.getCardCharacterName() + "%");
            statement.setInt(2, pjskCard.getRarity());
            //5.执行sql 得到结果集
            resultSet = statement.executeQuery();
            //6.处理结果集
            while (resultSet.next()) {
                PjskCard card = new PjskCard();
                card.setCardCharacterName(resultSet.getString(1));
                card.setRarity(resultSet.getInt(2));
                card.setPrefix(resultSet.getString(3));
                card.setCardSkillName(resultSet.getString(4));
                cardList.add(card);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //7.关闭资源
            /*try {
                DBConfig.closeAll(resultSet, statement, connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }*/
        }
        return cardList;
    }
}
