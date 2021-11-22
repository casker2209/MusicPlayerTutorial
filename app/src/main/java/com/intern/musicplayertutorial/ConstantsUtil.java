package com.intern.musicplayertutorial;

public class ConstantsUtil {
    public interface ACTION {
        public static String FOREGROUND_START = "start_foreground";
        public static String FOREGROUND_PAUSE = "pause foreground";
        public static String PLAY_PAUSE = "play pause action";
        public static String PLAY = "play action";
        public static String PAUSE = "pause action";
        public static String NEXT = "next action";
        public static String PREV = "previous action";
        public static String CLOSE = "close action";
        public static final String SEEK = "seekbar action";
        public static final String START_ACTIVITY = "start activity";
        public static final String SEEK_VOLUME = "seekbar volume action";

        public static final String VOLUME_CHANGE = "volume change";
        public static final String UPDATE_STATUS = "update";
    }
    public interface NOTIFICATION_ID{
        public static int ID = 101;
    }
}