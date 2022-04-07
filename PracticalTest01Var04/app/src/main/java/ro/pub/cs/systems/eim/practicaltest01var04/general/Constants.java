package ro.pub.cs.systems.eim.practicaltest01var04.general;

public interface Constants {
    final public static String FIRST_EDIT_TEXT = "firstEdiText";
    final public static String SECOND_EDIT_TEXT = "secondEdiText";
    final public static int SECONDARY_ACTIVITY_REQ_CODE = 1;
    final public static long SLEEP_TIME = 5000;

    final public static String TAG = "[STARTED SERVICE]";

    final public static String[] actionTypes = {
            "intention type 1",
            "intention type 2"
    };

    final public static String BROADCAST_RECEIVER_EXTRA = "message";
    final public static String BROADCAST_RECEIVER_TAG = "[MESSAGE]";

    final public static int SERVICE_STOPPED = 0;
    final public static int SERVICE_STARTED = 1;
}
