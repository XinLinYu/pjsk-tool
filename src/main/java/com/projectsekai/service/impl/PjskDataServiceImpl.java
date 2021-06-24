package com.projectsekai.service.impl;

import com.projectsekai.dao.PjskCardDao;
import com.projectsekai.entity.PjskCard;
import com.projectsekai.service.PjskDataService;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName CardDataServiceImpl
 * @description
 * @date 2021/6/23 0023 下午 3:21
 **/
@Service
public class PjskDataServiceImpl implements PjskDataService {
    private static PjskCardDao pjskCardDao = null;

    public static PjskCardDao getCardDaoInstance() {
        if (pjskCardDao == null) {
            synchronized (PjskCardDao.class) {
                if (pjskCardDao == null) {
                    pjskCardDao = new PjskCardDao();
                }
            }
        }
        return pjskCardDao;
    }

    @Override
    public void getCardDataList(GroupMessageEvent groupMessageEvent, String[] parameters) {
        pjskCardDao = getCardDaoInstance();
        String alertMessage = "查卡命令格式为\"/pjsk 查卡 <人物名称> <星级(纯数字)>\"，例如/pjsk 查卡 星乃一歌 4";
        PjskCard pjskCard = new PjskCard();
        // 命令中的“人物”不为空
        if (!StringUtils.isEmpty(parameters[0])) {
            pjskCard.setCardCharacterName(parameters[0]);
            // 命令中的“星级”不为空
            if (!StringUtils.isEmpty(parameters[1])) {
                Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
                if (pattern.matcher(parameters[1]).matches()) {
                    pjskCard.setRarity(Integer.parseInt(parameters[1]));
                    List<PjskCard> pjskCardList = pjskCardDao.getPjskCardList(pjskCard);
                    if (null != pjskCardList && pjskCardList.size() != 0) {
                        StringBuilder resultMessage = new StringBuilder();
                        for (PjskCard card : pjskCardList) {
                            resultMessage.append("卡名：").append(card.getPrefix()).append("            ");
                            resultMessage.append("技能：").append(card.getCardSkillName()).append("\n");
                        }
                        MessageChain chain = new MessageChainBuilder()
                                .append(new QuoteReply(groupMessageEvent.getMessage()))
                                .append(resultMessage.toString())
                                .build();
                        groupMessageEvent.getSubject().sendMessage(chain);
                    } else {
                        MessageChain chain = new MessageChainBuilder()
                                .append(new QuoteReply(groupMessageEvent.getMessage()))
                                .append("目前没有查到这样的卡牌哦~")
                                .build();
                        groupMessageEvent.getSubject().sendMessage(chain);
                    }
                } else {
                    MessageChain chain = new MessageChainBuilder()
                            .append(new QuoteReply(groupMessageEvent.getMessage()))
                            .append(alertMessage)
                            .build();
                    groupMessageEvent.getSubject().sendMessage(chain);
                }
            }
        } else {
            MessageChain chain = new MessageChainBuilder()
                    .append(new QuoteReply(groupMessageEvent.getMessage()))
                    .append(alertMessage)
                    .build();
            groupMessageEvent.getSubject().sendMessage(chain);
        }
    }

    @Override
    public void characterData(GroupMessageEvent groupMessageEvent, String[] parameters) {

    }
}
