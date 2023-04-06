package ro.pub.cs.systems.eim.practicaltest01var03;

public interface Constants {
    String RESULT = "result";
    int SECONDARY_ACTIVITY_REQUEST_CODE = 1;
    String FIRST_NUMBER = "first";
    String SECOND_NUMBER = "second";

    final public static String[] actionTypes = {
            "ro.pub.cs.systems.eim.practicaltest01.sumAction",
            "ro.pub.cs.systems.eim.practicaltest01.differenceAction"
    };

    String BROADCAST_RECEIVER_EXTRA = "ro.pub.cs.systems.eim.practicaltest01.intent.extra.BROADCAST_RECEIVER";
    String PROCESSING_THREAD_TAG = "[Processing Thread]";
    String BROADCAST_RECEIVER_TAG = "[Broadcast Receiver]";
}
