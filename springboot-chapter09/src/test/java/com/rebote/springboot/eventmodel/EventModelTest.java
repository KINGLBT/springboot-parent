package com.rebote.springboot.eventmodel;

import com.rebote.springboot.BaseTest;
import org.junit.Test;

/**
 * @ClassName: EventModelTest
 * @description:
 * @author: luomeng
 * @time: 2020/7/21 13:59
 */
public class EventModelTest extends BaseTest {

    @Test
    public void buttonClickTest(){
        Button button = new Button();
        button.addListener(new ClickListener() {
            @Override
            public void handleEvent(ClickEvent clickEvent) {
                System.out.println("弹出对话框");
            }
        });
        button.click();
    }

}
