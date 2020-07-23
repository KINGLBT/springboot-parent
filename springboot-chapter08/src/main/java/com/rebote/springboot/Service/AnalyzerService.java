package com.rebote.springboot.Service;

import com.rebote.springboot.analyzer.WannaStopException;
import org.springframework.stereotype.Service;

/**
 * @ClassName: AnalyzerService
 * @description:
 * @author: luomeng
 * @time: 2020/7/17 10:32
 */
@Service
public class AnalyzerService {

  public AnalyzerService() {
    System.out.println("我是懒加载，我不执行");
    throw new WannaStopException();
  }

}
