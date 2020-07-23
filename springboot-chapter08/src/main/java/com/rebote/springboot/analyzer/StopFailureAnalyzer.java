package com.rebote.springboot.analyzer;

import com.rebote.springboot.Service.AnalyzerService;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * @ClassName: StopFailureAnalyzer
 * @description:
 * @author: luomeng
 * @time: 2020/7/17 10:34
 */
public class StopFailureAnalyzer extends AbstractFailureAnalyzer<WannaStopException> {
  @Override
  protected FailureAnalysis analyze(Throwable rootFailure, WannaStopException cause) {
    for (StackTraceElement stackTraceElement : cause.getStackTrace()) {
      if (stackTraceElement.getClassName().equals(AnalyzerService.class.getName())) {
        return new FailureAnalysis("A想停止", "别要A了", cause);
      }
    }
    return null;
  }
}
