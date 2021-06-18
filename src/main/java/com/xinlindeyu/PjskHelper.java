package com.xinlindeyu;

import com.xinlindeyu.controller.PjskListener;
import net.mamoe.mirai.console.extension.PluginComponentStorage;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import org.jetbrains.annotations.NotNull;

public final class PjskHelper extends JavaPlugin {
    public static final PjskHelper INSTANCE = new PjskHelper();

    private PjskHelper() {
        super(new JvmPluginDescriptionBuilder("com.xinlindeyu.pjsk", "1.0-SNAPSHOT")
                .name("Pjsk-Helper")
                .author("xinlindeyu")
                .build());
    }

    @Override
    public void onLoad(@NotNull PluginComponentStorage $this$onLoad) {
        getLogger().info("PJSK-Helper Plugin is loading...");
    }

    @Override
    public void onEnable() {
        getLogger().info("PJSK-Helper Plugin loaded!");
        PjskListener agent = new PjskListener();
        // 创建监听
        Listener listener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, agent);

    }
}