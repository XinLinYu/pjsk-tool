package com.projectsekai.service.impl;

import com.projectsekai.service.FrequencyControlService;
import com.projectsekai.thread.PowerRemindThread;
import com.projectsekai.utils.ConstantUtil;
import com.projectsekai.utils.PropertiesUtil;
import com.projectsekai.utils.RedisUtil;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.utils.MiraiLogger;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName FrequencyControlServiceImpl
 * @description
 * @date 2021/6/21 0021 上午 10:48
 **/
@Service
public class FrequencyControlServiceImpl implements FrequencyControlService {
    private static RedisUtil redisUtil = null;

    public static MiraiLogger log = null;

    public static RedisUtil getRedisUtilInstance() {
        if (redisUtil == null) {
            synchronized (RedisUtil.class) {
                if (redisUtil == null) {
                    redisUtil = new RedisUtil();
                }
            }
        }
        return redisUtil;
    }

    @Override
    public void instructionFrequencyControl(String instruction, int expire, GroupMessageEvent groupMessageEvent, String message) {
        redisUtil = getRedisUtilInstance();
        log = groupMessageEvent.getBot().getLogger();
        log.info("GroupId：" + groupMessageEvent.getGroup().getId());
        log.info("instruction：" + instruction);
        Boolean instructionExistsStatus = redisUtil.exists("pjsk_" + instruction + ":" + groupMessageEvent.getGroup().getId());
        log.info("instructionExistsStatus：" + instructionExistsStatus);
        if (null == instructionExistsStatus) {
            log.error("检测命令超时时间错误！");
        }
        if (instructionExistsStatus) {
            groupMessageEvent.getSubject().sendMessage(PropertiesUtil.getInstance().read("欧尼酱~先让人家休息会嘛~马上再给欧尼酱回复哦~"));
        } else {
            groupMessageEvent.getSubject().sendMessage(message);
            setInstructionExpire("pjsk_" + instruction + ":" + groupMessageEvent.getGroup().getId(), message, 0, expire);
        }
    }

    @Override
    public void powerRemind(long id, GroupMessageEvent groupMessageEvent) {
        redisUtil = getRedisUtilInstance();
        Boolean exists = redisUtil.exists("pjsk_powerremind:" + id);
        if (exists) {
            Boolean powerRemind = redisUtil.exists("pjsk_powerremind:isstart");
            if (powerRemind) {
                groupMessageEvent.getSubject().sendMessage("体力小助手提醒已经开启了哦~");
            } else {
                redisUtil.set("pjsk_powerremind:isstart", String.valueOf(groupMessageEvent.getGroup().getId()), 0);
                groupMessageEvent.getSubject().sendMessage("已开启清体力小助手提醒，Miku酱将每隔一小时提醒大家打开烧烤清空体力哦~");
                CountDownLatch cdn = new CountDownLatch(1);
                ConstantUtil.executor.execute((new PowerRemindThread(cdn, groupMessageEvent, this)));
            }
        } else {
            groupMessageEvent.getSubject().sendMessage("哼！你不是我的主人，我不会听你的命令的！");
        }
    }

    @Override
    public void powerRemindStop(long id, GroupMessageEvent groupMessageEvent) {
        redisUtil = getRedisUtilInstance();
        Boolean exists = redisUtil.exists("pjsk_powerremind:" + id);
        if (exists) {
            Boolean powerRemind = redisUtil.exists("pjsk_powerremind:isstart");
            if (powerRemind) {
                redisUtil.del(0, "pjsk_powerremind:isstart");
                groupMessageEvent.getSubject().sendMessage("已关闭清体力小助手提醒，大家不再需要Miku酱提醒了吗o(TヘTo)");
            } else {
                groupMessageEvent.getSubject().sendMessage("体力小助手提醒已经关闭了哦~");
            }
        } else {
            groupMessageEvent.getSubject().sendMessage("哼！你不是我的主人，我不会听你的命令的！");
        }
    }

    private void setInstructionExpire(String key, String value, int indexdb, int expire) {
        redisUtil.set(key, value, indexdb);
        redisUtil.expire(key, expire, indexdb);
    }

    public void powerRemindTask(GroupMessageEvent groupMessageEvent) {
        while (true) {
            groupMessageEvent.getSubject().sendMessage("大家好，我是本群的提醒烧烤请体力小助手，希望此刻看到消息的人可以和我一起打开PROJECT SEKAI COLORFUL STAGE! feat.初音ミク 清空体力，一小时后，我将继续提醒大家打开烧烤清空体力，和我一起成为一天清八次体力的排名背刺人吧！");
            try {
                Thread.sleep(3600000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            redisUtil = getRedisUtilInstance();
            Boolean powerRemind = redisUtil.exists("pjsk_powerremind:isstart");
            if (!powerRemind) {
                break;
            }
        }
    }
}
