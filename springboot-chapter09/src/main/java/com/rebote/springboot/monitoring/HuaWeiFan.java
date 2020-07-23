package com.rebote.springboot.monitoring;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Observable;
import java.util.Observer;

/**
 * @ClassName: HuaWeiFan
 * @description:
 * @author: luomeng
 * @time: 2020/7/20 16:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HuaWeiFan implements Observer {

    /**
     * 花粉名称
     */
    private String name;

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof HuaWeiP30) {
            System.out.println("粉丝名为" + name + "发现华为p30降价了，新的价格为：" + arg + "元");
        }
    }
}
