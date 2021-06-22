package com.projectsekai.controller;

import com.projectsekai.annotation.InstructionTarget;
import com.projectsekai.annotation.InstructionVal;
import com.projectsekai.service.FrequencyControlService;
import com.projectsekai.service.impl.FrequencyControlServiceImpl;
import com.projectsekai.utils.PropertiesUtil;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
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

    public static FrequencyControlService frequencyControlService = null;


    public static MiraiLogger getInstance(GroupMessageEvent groupMessageEvent) {
        if (log == null) {
            synchronized (MiraiLogger.class) {
                if (log == null) {
                    log = groupMessageEvent.getBot().getLogger();
                }
            }
        }
        return log;
    }

    public static FrequencyControlService getServiceInstance() {
        if (frequencyControlService == null) {
            synchronized (FrequencyControlService.class) {
                if (frequencyControlService == null) {
                    frequencyControlService = new FrequencyControlServiceImpl();
                }
            }
        }
        return frequencyControlService;
    }

    /**
     * @param groupMessageEvent
     * @return void
     * @Author XinlindeYu
     * @description PJSK的简介
     * @Date 下午 4:35 2021/6/18 0018
     **/
    @InstructionVal(value = "/pjsk")
    public void intro(GroupMessageEvent groupMessageEvent) {
        log = getInstance(groupMessageEvent);
        frequencyControlService = getServiceInstance();
        frequencyControlService.instructionFrequencyControl("intro", 30, groupMessageEvent, PropertiesUtil.getInstance().read("《世界计划 彩色舞台 feat. 初音未来》（日语：プロジェクトセカイ カラフルステージ! feat. 初音ミク；英语：Project Sekai Colorful Stage! feat. Hatsune Miku）是由SEGA、Craft Egg、Crypton Future Media共同企划，SEGA Games与Craft Egg子公司Colorful Palette共同制作的一款音乐节奏和视觉小说类的社交手机卡牌网络游戏，于2020年9月30日在日本App Store和Google Play商店正式上线。官方简称“プロセカ”，国内粉丝简称“PJSK（啤酒烧烤）”。\\n阅读更多：世界计划_彩色舞台_feat._初音未来（https://zh.moegirl.org.cn/%E4%B8%96%E7%95%8C%E8%AE%A1%E5%88%92_%E5%BD%A9%E8%89%B2%E8%88%9E%E5%8F%B0_feat._%E5%88%9D%E9%9F%B3%E6%9C%AA%E6%9D%A5 ）\\n本文引自萌娘百科(https://zh.moegirl.org.cn )，文字内容默认使用《知识共享 署名-非商业性使用-相同方式共享 3.0》协议。"));
    }

    /**
     * @param groupMessageEvent
     * @return void
     * @Author XinlindeYu
     * @description 开启体力小助手提醒
     * @Date 下午 3:27 2021/6/21 0021
     **/
    @InstructionVal(value = "/pjsk power-remind start")
    public void powerRemindStart(GroupMessageEvent groupMessageEvent) {
        log = getInstance(groupMessageEvent);
        frequencyControlService = getServiceInstance();
        Group group = groupMessageEvent.getGroup();
        Member sender = groupMessageEvent.getSender();
        long id = sender.getId();
        log.info("开启体力小助手的成员ID：" + id);
        frequencyControlService.powerRemind(id, groupMessageEvent);
    }

    /**
     * @param groupMessageEvent
     * @return void
     * @Author XinlindeYu
     * @description 关闭体力小助手提醒
     * @Date 下午 3:27 2021/6/21 0021
     **/
    @InstructionVal(value = "/pjsk power-remind stop")
    public void powerRemindStop(GroupMessageEvent groupMessageEvent) {
        log = getInstance(groupMessageEvent);
        frequencyControlService = getServiceInstance();
        Group group = groupMessageEvent.getGroup();
        Member sender = groupMessageEvent.getSender();
        long id = sender.getId();
        log.info("关闭体力小助手的成员ID：" + id);
        frequencyControlService.powerRemindStop(id, groupMessageEvent);
    }

    /*@InstructionVal(value = "/pjsk authority power-remind {}")
    public void authorityPowerRemind(GroupMessageEvent groupMessageEvent) {
        log = getInstance(groupMessageEvent);

    }*/
}
