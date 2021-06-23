package com.projectsekai.service;

import net.mamoe.mirai.event.events.GroupMessageEvent;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName CardDataService
 * @description
 * @date 2021/6/23 0023 下午 3:20
 **/
public interface CardDataService {
    /**
     * @param parameters
     * @return java.util.List<com.projectsekai.entity.PjskCard>
     * @Author XinlindeYu
     * @description 查卡
     * @Date 下午 4:57 2021/6/23 0023
     **/
    void getCardDataList(GroupMessageEvent groupMessageEvent, String[] parameters);

}
