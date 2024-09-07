package ru.vsu.cs.gelem.task1;

public class Main {
    public static void main(String[] args) {
        DateTime data = new DateTime(34, 18, 22, 28, 2, 2007);
        data.plusDays(5);
        data.plusYears(2);
        DateTime data1 = new DateTime("28.02.2005", DateFormats.DDMMYYYY, "14:30", TimeFormats.hhmm);
        data.plusDays(1);
        System.out.println(data.getDateAndTime() + " " + data1.getDateAndTime() + " " + data.compareTo(data1));
    }
}