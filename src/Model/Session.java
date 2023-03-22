package Model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class Session {
    private final String id;
    private final String username;
    private final Date timestamp;
    private static final int MAX_AGE = 86_400_000;

    public Session(String id, String username, String timestamp) {
        this.id = id;
        this.username = username;
        this.timestamp = Timestamp.valueOf(timestamp);
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public boolean isValid() {
        var c = Calendar.getInstance();
        c.setTime(timestamp);
        c.add(Calendar.MILLISECOND, MAX_AGE);

        return c.getTime().after(new Date());
    }
}
