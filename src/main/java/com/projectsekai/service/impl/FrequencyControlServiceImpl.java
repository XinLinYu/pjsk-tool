package com.projectsekai.service.impl;

import com.projectsekai.service.FrequencyControlService;
import com.projectsekai.thread.PowerRemindThread;
import com.projectsekai.utils.ConstantUtil;
import com.projectsekai.utils.PropertiesUtil;
import com.projectsekai.utils.RedisUtil;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.MiraiLogger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName FrequencyControlServiceImpl
 * @description
 * @date 2021/6/21 0021 上午 10:48
 **/
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

    @Override
    public void instructionFrequencyControl(String instruction, int expire, GroupMessageEvent groupMessageEvent, String message) {
        redisUtil = getRedisUtilInstance();
        log = getInstance(groupMessageEvent);
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
        log = getInstance(groupMessageEvent);
        redisUtil = getRedisUtilInstance();
        String key = "pjsk_powerremind_time:" + groupMessageEvent.getGroup().getId();
        while (true) {
            Boolean exists = redisUtil.exists(key);
            if (!exists) {
                // 请体力小助手图片+文字
                groupMessageEvent.getSubject().sendMessage(Image.fromId("{067981C8-0D09-BE10-9C25-626D97653999}.jpg").plus(new PlainText("各位清火啦~~~（提醒从早上9点开始到晚上9点结束，活动期间Miku酱每隔一小时提醒一次哦~）")));
                redisUtil.set(key, String.valueOf(groupMessageEvent.getGroup().getId()), 0);
                // 1小时提醒一次
                redisUtil.expire(key, 3600, 0);
            }
            try {
                // 每10秒钟检查一次
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 判断当前是否还需要再次开启，不在开启则跳出循环
            Boolean powerRemind = redisUtil.exists("pjsk_powerremind:isstart");
            if (!powerRemind) {
                break;
            }
            // 判断是否在休眠期内，在休眠期则不再执行
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            Date now = null;
            Date beginTime = null;
            Date endTime = null;
            try {
                now = df.parse(df.format(new Date()));
                beginTime = df.parse("21:00");
                endTime = df.parse("9:00");
            } catch (Exception e) {
                e.printStackTrace();
            }
            boolean flag = belongCalendar(now, beginTime, endTime);
            if (flag) {
                log.info("在休眠期内……");
                groupMessageEvent.getSubject().sendMessage("Miku酱要睡觉觉啦！明天再继续给大家提醒吧~╰(￣ω￣ｏ)！晚安~");
                try {
                    // 休眠期12小时
                    Thread.sleep(43200000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    private static boolean belongCalendar(Date nowTime, Date beginTime,
                                          Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        end.add(Calendar.DATE, 1);
        return date.after(begin) && date.before(end);
    }
}
