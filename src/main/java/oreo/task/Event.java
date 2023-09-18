package oreo.task;

import oreo.datetime.TimeParser;
import oreo.exception.IllegalDateTimeException;

public class Event extends Task {
    private String fromDate;
    private String fromTime;
    private String toDate;
    private String toTime;
    public Event(String d, String fromDate, String fromTime,
                 String toDate, String toTime) throws IllegalDateTimeException {
        super(d);
        TimeParser.checkValidEventDate(fromDate, toDate);
        if (fromDate.equals(toDate) && fromTime != null
        && toTime != null) {
            TimeParser.checkValidEventTime(fromTime, toTime);
        }
        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.toTime = toTime;
    }

    public Event(String d, String fromDate, String fromTime,
                 String toDate, String toTime , boolean completed) {
        super(d);
        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.toTime = toTime;
        this.isComplete = completed;
    }

    @Override
    public String toString() {
        String marker = "☐";
        if (this.isComplete) {
            marker = "☑";
        }
        String fromTimeString;
        String toTimeString;
        fromTimeString = (this.fromTime == null) ? "" : (" " + this.fromTime);
        toTimeString = (this.toTime == null) ? "" : (" " + this.toTime);
        return  "EVENT:" + " " + this.description
                + " (from: " + this.fromDate + fromTimeString
                + " to: " + this.toDate + toTimeString + ") "
                + marker + "\n";
    }

    @Override
    public String writeToFile() {
        int mark = isComplete ? 1 : 0;
        String data = 3 + " " + mark + description + "/" + TimeParser.parseDateForFile(this.fromDate)
                + TimeParser.parseTimeForFile(this.fromTime) + "/" + TimeParser.parseDateForFile(this.toDate)
                + TimeParser.parseTimeForFile(this.toTime) + System.lineSeparator();
        return data;
    }

    @Override
    public String getTaskInEditFormat() {
        return "event" + description + " /from " + TimeParser.parseDateForFile(this.fromDate)
                + TimeParser.parseTimeForFile(this.fromTime)
                + "/to " + TimeParser.parseDateForFile(this.toDate) + TimeParser.parseTimeForFile(this.toTime);
    }
}
