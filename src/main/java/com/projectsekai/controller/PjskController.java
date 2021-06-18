package com.projectsekai.controller;

import com.projectsekai.annotation.InstructionTarget;
import com.projectsekai.annotation.InstructionVal;
import com.projectsekai.utils.PropertiesUtil;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.utils.MiraiLogger;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName PjskController
 * @description 基础控制层
 * @date 2021/6/18 0018 下午 3:20
 **/
@InstructionTarget
public class PjskController {
    public static MiraiLogger log = null;

    /**
     * @param groupMessageEvent
     * @return void
     * @Author XinlindeYu
     * @description PJSK的简介
     * @Date 下午 4:35 2021/6/18 0018
     **/
    @InstructionVal(value = "/pjsk")
    public void intro(GroupMessageEvent groupMessageEvent) {
        log = groupMessageEvent.getBot().getLogger();
        String pjskIntroduction = PropertiesUtil.getInstance().read("pjsk_introduction");
        log.info(pjskIntroduction);
        groupMessageEvent.getSubject().sendMessage(pjskIntroduction);
    }


}
