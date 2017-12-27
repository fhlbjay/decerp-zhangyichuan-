package cn.wolfcode.crm.util;

import java.math.BigDecimal;

public class JudgeNumber {

    public static BigDecimal judgeNumber(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return new BigDecimal("0");
        } else {
            return bigDecimal;
        }
    }
}
