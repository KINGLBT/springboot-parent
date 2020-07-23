package com.rebote.springboot.eventmodel;

import java.util.EventObject;

/**
 * @ClassName: ClickEvent
 * @description:
 * @author: luomeng
 * @time: 2020/7/21 10:20
 */
public class ClickEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ClickEvent(Object source) {
        super(source);
    }
}
