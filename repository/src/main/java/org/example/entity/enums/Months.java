package org.example.entity.enums;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
public enum Months {
    January("Январь", 1),
    February("Февраль", 2),
    March("Март", 3),
    April("Апрель", 4),
    May("Май", 5),
    June("Июнь", 6),
    July("Июль", 7),
    August("Август", 8),
    September("Сентябрь", 9),
    October("Октябрь", 10),
    November("Ноябрь", 11),
    December("Декабрь", 12);

    private String rus;
    private int number;

    Months(String rus, int number) {
        this.rus = rus;
        this.number = number;
    }

    public static void main(String[] args) {
        Months months = Months.valueOf(String.valueOf(December));
        System.out.println(months);
        BigDecimal x = BigDecimal.valueOf(12.78);
        BigDecimal y = BigDecimal.valueOf(12.6215422555555);
        System.out.println(x.add(y).setScale(2, RoundingMode.CEILING));
    }
}
