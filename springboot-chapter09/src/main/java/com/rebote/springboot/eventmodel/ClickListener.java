package com.rebote.springboot.eventmodel;

import java.util.EventListener;

public interface ClickListener extends EventListener {
    void handleEvent(ClickEvent clickEvent);
}
