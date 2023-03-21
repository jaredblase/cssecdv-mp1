package Model;

import java.util.HashMap;
import java.util.Map;

public enum Role {
    ADMINISTRATOR(5),
    MANAGER(4),
    STAFF(3),
    CLIENT(2),
    DISABLED(1);

    private final int code;

    Role(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    private static final Map<Integer, Role> BY_VALUE = new HashMap<>();

    static {
        for (Role r: values()) {
            BY_VALUE.put(r.code, r);
        }
    }

    public static Role valueOf(int code) {
        return BY_VALUE.get(code);
    }
}
