package com.rebote.springboot.eventmodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName: Button
 * @description:
 * @author: luomeng
 * @time: 2020/7/21 13:45
 */
public class Button {

    /**
     * 监听事件集合，后续触发点击，做通知处理
     */
    private List<ClickListener> eventListeners = new ArrayList<>();

    /**
     * 添加监听器
     *
     * @param eventListener
     */
    public void addListener(ClickListener eventListener) {
        eventListeners.add(eventListener);
    }

    /**
     * 通知所有的监听器，进行处理
     *
     * @param clickEvent
     */
    private void notifyListener(ClickEvent clickEvent) {
        Iterator<ClickListener> iterator = eventListeners.iterator();
        if (iterator.hasNext()) {
            ClickListener clickListener = iterator.next();
            clickListener.handleEvent(clickEvent);
        }
    }

    /**
     * 点击操作触发
     */
    public void click() {
        ClickEvent clickEvent = new ClickEvent(this);
        System.out.println("按钮被点击");
        notifyListener(clickEvent);
    }

}
