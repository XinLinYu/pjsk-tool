package com.xinlindeyu.controller;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * @ClassName 32718353
 * @description Project Sekai监听器
 * @Author XinlindeYu
 * @Date 2021/6/17 0017 下午 2:22
 * @Version 1.0
 **/
public class PjskListener implements Consumer<GroupMessageEvent> {
    public static MiraiLogger log = null;

    public static final String[] CMD = {
            "pjsk", "/pjsk"
    };

    private void stepCmd(Group api, long groupId, long qq, String message) {
        boolean isCMD = false;
        for (String s : CMD) {
            if (message.contains(s)) {
                isCMD = true;
                break;
            }
        }
        if (!isCMD) {
            return;
        }
        log.info("《世界计划 彩色舞台 feat. 初音未来》（日语：プロジェクトセカイ カラフルステージ! feat. 初音ミク；英语：Project Sekai Colorful Stage! feat. Hatsune Miku）是由SEGA、Craft Egg、Crypton Future Media共同企划，SEGA Games与Craft Egg子公司Colorful Palette共同制作的一款音乐节奏和视觉小说类的社交手机卡牌网络游戏，于2020年9月30日在日本App Store和Google Play商店正式上线。官方简称“プロセカ”，国内粉丝简称“PJSK（啤酒烧烤）”。\n" +
                "\n" +
                "阅读更多：世界计划_彩色舞台_feat._初音未来（https://zh.moegirl.org.cn/%E4%B8%96%E7%95%8C%E8%AE%A1%E5%88%92_%E5%BD%A9%E8%89%B2%E8%88%9E%E5%8F%B0_feat._%E5%88%9D%E9%9F%B3%E6%9C%AA%E6%9D%A5 ）\n" +
                "本文引自萌娘百科(https://zh.moegirl.org.cn )，文字内容默认使用《知识共享 署名-非商业性使用-相同方式共享 3.0》协议。");
    }

    @Override
    public void accept(GroupMessageEvent groupMessageEvent) {
        log = groupMessageEvent.getBot().getLogger();
        log.info("MikuMikuMi~~");
        Group group = groupMessageEvent.getGroup();
        String message = groupMessageEvent.getMessage().toString();
        log.info(message);
        long groupId = group.getId();
        long qq = groupMessageEvent.getSender().getId();
        stepCmd(group, groupId, qq, message);
    }

    @NotNull
    @Override
    public Consumer<GroupMessageEvent> andThen(@NotNull Consumer<? super GroupMessageEvent> after) {
        return null;
    }
}
