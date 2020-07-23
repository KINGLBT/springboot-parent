package com.rebote.springboot.monitoring;

import java.math.BigDecimal;
import java.util.Observable;

/**
 * @ClassName: HuaWeiP30
 * @description: 定义华为手机P30：被观察对象
 * @author: luomeng
 * @time: 2020/7/20 15:54
 */
public class HuaWeiP30 extends Observable {

    /**
     * 手机价格
     */
    private BigDecimal price;

    public HuaWeiP30(BigDecimal price) {
        this.price = price;
    }

    public void setPrice(BigDecimal price) {
        if (price.compareTo(this.price) != Integer.valueOf(0)) {
            setChanged();
        }
        if (price.compareTo(this.price) == Integer.valueOf(-1)) {
            this.notifyObservers(price);
        }
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
