package database;

import android.provider.BaseColumns;

public final class Tables {

    private Tables(){}

    public static class User implements BaseColumns {

        public static final String TABLE_NAME="user";
        public static final String COLUMN_NAME="name";
        public static final String COLUMN_PASSWORD="password";
        public static final String COLUMN_TYPE="type";



    }
    public static class Message implements BaseColumns {

        public static final String TABLE_NAME="message";
        public static final String COLUMN_USER="user";
        public static final String COLUMN_SUBJECT="subject";
        public static final String COLUMN_MESSAGE="message";



    }
}
