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
    private static SessionListener unauthorizedListener;

    public static String login(SQLite db, String username) throws SQLException {
        var sessionId = UUID.randomUUID().toString();
        db.addSession(sessionId, username);
        prefs.put(SESSION_ID_KEY, sessionId);

        if (loginListener != null) {
            loginListener.onAction();
        }

        return sessionId;
    }

    public static Session getSession(SQLite db) throws SQLException {
        var sessId = prefs.get(SESSION_ID_KEY, null);
        if (sessId == null) return null;

        var session = db.getSessionById(sessId);

        if (session == null || !session.isValid()) {
            logout(db);
            return null;
        }

        return session;
    }

    public static User getUser(SQLite db) throws SQLException {
        return getUser(db, getSession(db));
    }

    public static User getUser(SQLite db, Session session) throws SQLException {
        if (session == null) return null;
        return db.getUserByUsername(session.getUsername());
    }

    public static User getAuthorizedUser(SQLite db, Role... roles) throws SQLException {
        User user = getUser(db);

        if (user == null) {
            logout(db);
            return null;
        }

        for (Role role : roles) {
            if (user.getRole() == role) {
                return user;
            }
        }

        onUnauthorized();
        return null;
    }

    public static void logout(SQLite db) {
        var sessId = prefs.get(SESSION_ID_KEY, null);

        if (sessId != null) {
            try {
                String username = getUser(db).getUsername();
                prefs.remove(SESSION_ID_KEY);
                db.deleteSessionById(sessId);
                db.addLogs("NOTICE", username, "User logout successful", null);
            } catch (SQLException e) {
                if (SQLite.DEBUG_MODE) e.printStackTrace();
            }
        }

        if (logoutListener != null) {
            logoutListener.onAction();
        }
    }

    public static void onUnauthorized() {
        if (unauthorizedListener != null) {
            unauthorizedListener.onAction();
        }
    }

    public static void setLoginListener(SessionListener loginListener) {
        SessionManager.loginListener = loginListener;
    }

    public static void setLogoutListener(SessionListener logoutListener) {
        SessionManager.logoutListener = logoutListener;
    }

    public static void setUnauthorizedListener(SessionListener unauthorizedListener) {
        SessionManager.unauthorizedListener = unauthorizedListener;
    }

    public interface SessionListener {
        void onAction();
    }
}
