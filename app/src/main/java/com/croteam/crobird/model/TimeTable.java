package com.croteam.crobird.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TimeTable extends RealmObject {

    @PrimaryKey
    private String id;
    private String userId, dayOfWeek;
    int fromTime, toTime;

}
