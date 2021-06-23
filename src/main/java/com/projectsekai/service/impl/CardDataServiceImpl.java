package com.projectsekai.service.impl;

import com.projectsekai.entity.PjskCard;
import com.projectsekai.mapper.PjskCardMapper;
import com.projectsekai.mapper.PjskCardMapperImpl;
import com.projectsekai.service.CardDataService;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.utils.MiraiLogger;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CardDataServiceImpl implements CardDataService {
    private static PjskCardMapper pjskCardMapper;

    public static PjskCardMapper getInstance() {
        if (pjskCardMapper == null) {
            synchronized (PjskCardMapper.class) {
                if (pjskCardMapper == null) {
                    pjskCardMapper = new PjskCardMapperImpl();
                }
            }
        }
        return pjskCardMapper;
    }

    @Override
    public void getCardDataList(GroupMessageEvent groupMessageEvent, String[] parameters) {
        String alertMessage = "查卡命令格式为\"/pjsk 查卡 <人物名称> <星级（纯数字）>，例如/pjsk 查卡 星乃一歌 4\"";
        pjskCardMapper = getInstance();
        PjskCard pjskCard = new PjskCard();
        if (!StringUtils.isEmpty(parameters[0])) {
            pjskCard.setCardCharacterName(parameters[0]);
        } else {
            groupMessageEvent.getSubject().sendMessage(alertMessage);

        }
        if (!StringUtils.isEmpty(parameters[1])) {
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            if (pattern.matcher(parameters[1]).matches()) {
                pjskCard.setRarity(Integer.parseInt(parameters[1]));
            } else {
                groupMessageEvent.getSubject().sendMessage(alertMessage);
            }
        }
        List<PjskCard> pjskCardList = pjskCardMapper.queryPjskCardList(pjskCard);
        StringBuilder resultMessage = new StringBuilder();
        for (PjskCard card : pjskCardList) {
            resultMessage.append(card.getRarity()).append("星").append("   ").append(card.getCardCharacterName());
            resultMessage.append("\\n");
        }
    }
}
