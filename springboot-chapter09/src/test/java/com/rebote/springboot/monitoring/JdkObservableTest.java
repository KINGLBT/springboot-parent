package com.rebote.springboot.monitoring;

import com.rebote.springboot.BaseTest;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @ClassName: JdkObservableTest
 * @description:
 * @author: luomeng
 * @time: 2020/7/20 17:31
 */
public class JdkObservableTest extends BaseTest {

  @Test
  public void jdkObserverTest(){
    HuaWeiP30 p30 = new HuaWeiP30(BigDecimal.valueOf(3000));
    p30.addObserver(new HuaWeiFan("zhangsan"));
    p30.addObserver(new HuaWeiFan("lisi"));
    p30.addObserver(new HuaWeiFan("wangwu"));
    p30.setPrice(BigDecimal.valueOf(2500));
  }

}
