package Model;

import Controller.SQLite;

import java.sql.SQLException;
import java.util.UUID;
import java.util.prefs.Preferences;

public class SessionManager {
    private static final Preferences prefs = Preferences.userNodeForPackage(SessionManager.class);
    private static final String SESSION_ID_KEY = "session_id";
    private static SessionListener loginListener;
    private static SessionListener logoutListener;

    public static String login(SQLite db, String username) throws SQLException {
        var sessionId = UUID.randomUUID().toString();
        db.addSession(sessionId, username);
        prefs.put(SESSION_ID_KEY, sessionId);

        if (loginListener != null) {
            loginListener.onAction();
        }

        return sessionId;
    }

    public static User getUser(SQLite db) {
        var sessId = prefs.get(SESSION_ID_KEY, null);
        if (sessId == null) return null;

        try {
            return db.getUserBySessionId(sessId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void logout() {
        prefs.remove(SESSION_ID_KEY);
        if (logoutListener != null) {
            logoutListener.onAction();
        }
    }

    public static void setLoginListener(SessionListener loginListener) {
        SessionManager.loginListener = loginListener;
    }

    public static void setLogoutListener(SessionListener logoutListener) {
        SessionManager.logoutListener = logoutListener;
    }

    public interface SessionListener {
        void onAction();
    }
}
