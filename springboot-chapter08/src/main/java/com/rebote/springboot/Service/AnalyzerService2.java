package com.rebote.springboot.Service;

import com.rebote.springboot.analyzer.WannaStopException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @ClassName: AnalyzerService
 * @description:
 * @author: luomeng
 * @time: 2020/7/17 10:32
 */
@Service
@Lazy(false)
public class AnalyzerService2 {

  public AnalyzerService2() {
    System.out.println("我排除设置懒加载");
  }

}
