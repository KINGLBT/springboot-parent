package com.rebote.springboot.banner;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

/**
 * @ClassName: SpringBootBanner
 * @description:
 * @author: luomeng
 * @time: 2020/7/17 14:55
 */
public class SpringBootCustomerBanner implements Banner {

  private static final String[] BANNER = { "", " ZZZZZ Y   Y TTTTTT EEEE  CCC H  H \n"
      + "   Z   Y Y    TT   E    C    H  H \n" + "  Z     Y     TT   EEE  C    HHHH \n"
      + " Z      Y     TT   E    C    H  H \n" + "ZZZZZ   Y     TT   EEEE  CCC H  H \n" };

  private static final String SPRING_BOOT = " :: Spring Boot :: ";

  private static final int STRAP_LINE_SIZE = 42;

  @Override
  public void printBanner(Environment environment, Class<?> sourceClass, PrintStream printStream) {
    for (String line : BANNER) {
      printStream.println(line);
    }
    String version = SpringBootVersion.getVersion();
    version = (version != null) ? " (v" + version + ")" : "";
    StringBuilder padding = new StringBuilder();
    while (padding.length() < STRAP_LINE_SIZE - (version.length() + SPRING_BOOT.length())) {
      padding.append(" ");
    }

    printStream.println(AnsiOutput.toString(AnsiColor.GREEN, SPRING_BOOT, AnsiColor.DEFAULT, padding.toString(),
        AnsiStyle.FAINT, version));
    printStream.println();
  }
}
