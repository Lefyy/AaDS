package ru.vsu.cs.gelem.task1;

import static java.lang.Integer.parseInt;

public class DateTime implements Comparable<DateTime> {

    private int date;
    private int time;
    private DateFormats dateFormat;
    private TimeFormats timeFormat;

    public DateTime() {
        this.date = 0;
        this.time = 0;
        this.dateFormat = DateFormats.DDMMYYYY;
        this.timeFormat = TimeFormats.hhmmss;
    }

    public DateTime(int day, int month, int year) {
        parseDate(day, month, year);
        this.time = 0;
        this.dateFormat = DateFormats.DDMMYYYY;
        this.timeFormat = TimeFormats.hhmmss;
    }

    public DateTime(int sec, int min, int hour, int day, int month, int year) {
        parseDate(day, month, year);
        parseTime(sec, min, hour);
        this.dateFormat = DateFormats.DDMMYYYY;
        this.timeFormat = TimeFormats.hhmmss;
    }

    public DateTime(String dateStr, DateFormats dateFormatStr, String timeStr, TimeFormats timeFormatStr) {
        parseDateFromString(dateStr, dateFormatStr);
        parseTimeFromString(timeStr, timeFormatStr);
        this.dateFormat = dateFormatStr;
        this.timeFormat = timeFormatStr;
    }

    private boolean checkDate(int days, int months, int years) {
        if (days < 1 || months < 1 || years < 1 || months > 12 ||
                (is30(months) && days > 30) || (is31(months) && days > 31) ||
                (!is31(months) && !is30(months) && !isLeapYear(years) && days > 28) ||
                (!is31(months) && !is30(months) && isLeapYear(years) && days > 29)) {
            throw new IllegalArgumentException("некорректная дата");
        } else return true;
    }

    private boolean checkTime(int hours, int mins, int secs) {
        if (hours > 23 || hours < 0 || mins > 59 || mins < 0 || secs > 59 || secs < 0) {
            throw new IllegalArgumentException("Некорректное время");
        } else return true;
    }

    private void parseDate(int days, int months, int years) {

        checkDate(days, months, years);

        days += countLeapYears(years) * 366 + (years - countLeapYears(years)) * 365;

        for (int i = 1; i < months; i++) {
            if (is30(i)) {
                days += 30;
            } else if (is31(i)) {
                days += 31;
            } else if (isLeapYear(years)) {
                days += 29;
            } else {
                days += 28;
            }
        }

        date = days;
    }

    private int countLeapYears(int years) {
        return (years / 4 - years / 100 + years / 400);
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private boolean is31(int month) {
        return (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12);
    }

    private boolean is30(int month) {
        return (month == 4 || month == 6 || month == 9 || month == 11);
    }

    private void parseTime(int secs, int mins, int hours) {
        checkTime(hours, mins, secs);
        time = secs + mins * 60 + hours * 3600;
    }

    public String getDate() {
        int[] dates = countDate();
        StringBuilder strDate = new StringBuilder();
        if (this.dateFormat == DateFormats.DDMMYYYY) {
            strDate.append(dates[0]);
            strDate.append(".");
            strDate.append(dates[1]);
        } else {
            strDate.append(dates[1]);
            strDate.append(".");
            strDate.append(dates[0]);
        }
        strDate.append(".");
        strDate.append(dates[2]);
        return strDate.toString();
    }

    public String getTime() {
        int[] times = countTime();
        StringBuilder strTime = new StringBuilder();
        if (this.timeFormat == TimeFormats.hhmmss) {
            strTime.append(times[0]);
            strTime.append(":");
            strTime.append(times[1]);
            strTime.append(":");
            strTime.append(times[2]);
            return strTime.toString();
        } else if (this.timeFormat == TimeFormats.hhmm) {
            strTime.append(times[0]);
            strTime.append(":");
            strTime.append(times[1]);
            return strTime.toString();
        } else {
            strTime.append(times[1]);
            strTime.append(":");
            strTime.append(times[2]);
            return strTime.toString();
        }
    }

    public String getDateAndTime() {
        return getDate() + " " + getTime();
    }

    private void checkTimeChange() {
        if (time >= 86400) {
            date += 1 + time / 86400;
            time = time % 86400;
        }
        if (time < 0) {
            date -= 1 + time / 86400;
            time = 86400 - time % 86400;
        }
    }

    private int[] countDate() {
        int source = date;
        int year = date / 365;
        int leapYears = countLeapYears(year);
        date -= (leapYears * 366 + ((year - leapYears) * 365));
        while (date < 1) {
            date += isLeapYear(year) ? 366 : 365;
            year -= 1;
        }
        int months = 0;
        int days = 0;
        for (int i = 1; i <= 12; i++) {
            if (is30(i) && date <= 30 ||
                    is31(i) && date <= 31 ||
                    isLeapYear(year) && i == 2 && date <= 29 ||
                    !isLeapYear(year) && i == 2 && date <= 28) {
                days = date;
                months++;
                break;
            } else {
                if (is30(i)) {
                    months++;
                    date -= 30;
                } else if (is31(i)) {
                    months++;
                    date -= 31;
                } else if (isLeapYear(year) && i == 2) {
                    months++;
                    date -= 29;
                } else {
                    months++;
                    date -= 28;
                }
            }
        }
        if (days == 0) {
            days = 1;
        } else if (months == 0) {
            months = 1;
        }
        date = source;
        return new int[]{days, months, year};
    }

    private int[] countTime() {
        int hours = time / 3600;
        int mins = time % 3600 / 60;
        int secs = time % 3600 % 60;
        return new int[]{hours, mins, secs};
    }

    public void plusSecs(int n) {
        time += n;
        checkTimeChange();
    }

    public void plusMins(int n) {
        time += 60 * n;
        checkTimeChange();
    }

    public void plusHours(int n) {
        time += 3600 * n;
        checkTimeChange();
    }

    public void plusDays(int n) {
        date += n;
    }

    public void plusMonths(int n) {
        int[] dateArr = countDate();
        dateArr[1] += n;
        if (dateArr[1] > 12) {
            dateArr[2] += dateArr[1] / 12;
            dateArr[1] = dateArr[1] % 12;
        }
        if (is30(dateArr[1]) && dateArr[0] > 30) {
            dateArr[1] += 1;
            dateArr[0] = dateArr[0] - 30;
        } else if (dateArr[1] == 2 && isLeapYear(dateArr[2]) && dateArr[0] > 29) {
            dateArr[1] += 1;
            dateArr[0] = dateArr[0] - 29;
        } else if (dateArr[1] == 2 && !isLeapYear(dateArr[2]) && dateArr[0] > 28) {
            dateArr[1] += 1;
            dateArr[0] = dateArr[0] - 28;
        }

        parseDate(dateArr[0], dateArr[1], dateArr[2]);
    }

    public void plusYears(int n) {
        int[] dateArr = countDate();
        dateArr[2] += n;
        parseDate(dateArr[0], dateArr[1], dateArr[2]);
    }

    public void setDateFormat(DateFormats format) {
        this.dateFormat = format;
    }

    public void setTimeFormat(TimeFormats format) {
        this.timeFormat = format;
    }

    public void parseDateFromString(String dateStr, DateFormats format) {
        String[] dateStrs = dateStr.split("\\.");
        int[] dateInts = new int[3];
        if (dateStrs.length != 3) {
            throw new IllegalArgumentException("неверный формат даты");
        }
        for (int i = 0; i < dateInts.length; i++) {
            dateInts[i] = parseInt(dateStrs[i]);
        }
        switch (format) {
            case MMDDYYYY:
                if (checkDate(dateInts[1], dateInts[0], dateInts[2])) {
                    parseDate(dateInts[1], dateInts[0], dateInts[2]);
                }
                break;
            case DDMMYYYY:
                if (checkDate(dateInts[0], dateInts[1], dateInts[2])) {
                    parseDate(dateInts[0], dateInts[1], dateInts[2]);
                }
                break;
        }

    }

    public void parseTimeFromString(String timeStr, TimeFormats format) {
        String[] timeStrs = timeStr.split(":");
        int[] timeInts = new int[3];
        for (int i = 0; i < timeStrs.length; i++) {
            timeInts[i] = parseInt(timeStrs[i]);
        }
        switch (format) {
            case hhmmss:
                if (checkTime(timeInts[0], timeInts[1], timeInts[2])) {
                    parseTime(timeInts[2], timeInts[1], timeInts[0]);
                }
                break;
            case hhmm:
                if (checkTime(timeInts[0], timeInts[1], 0)) {
                    parseTime(0, timeInts[1], timeInts[0]);
                }
                break;
            case mmss:
                if (checkTime(0, timeInts[0], timeInts[1])) {
                    parseTime(timeInts[1], timeInts[0], 0);
                }
                break;
        }
    }

    @Override
    public int compareTo(DateTime o) {
        int result = Integer.compare(this.date, o.date);
        if (result == 0) {
            return Integer.compare(this.time, o.time);
        } else {
            return result;
        }
    }
}
