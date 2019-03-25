package com.croteam.crobird.database;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.support.v4.app.Fragment;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmController {

    private static RealmController instance;
    private final Realm realm;
    private static Application context;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            context = fragment.getActivity().getApplication();
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            context = activity.getApplication();
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Service service) {

        if (instance == null) {
            context = service.getApplication();
            instance = new RealmController(service.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            context = application;
            instance = new RealmController(application);
        }
        return instance;
    }

    public RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    public Application getContext() {
        return context;
    }

    //clear all objects from Book.class
    public void clearAll() {

        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public void clearAllTable(Class classname){
        realm.beginTransaction();
        realm.delete(classname);
        realm.commitTransaction();
    }

    public boolean deleteItems(Class classname, String field, String value){
        if(! realm.isInTransaction())realm.beginTransaction();
        RealmResults<Object> realmResults = queryObjects(classname, field, value);
        boolean result =  realmResults.deleteAllFromRealm();
        realm.commitTransaction();
        return result;
    }

    //find all objects in the Book.class
    public RealmResults<RealmObject> getAllList(Class classname) {

        return realm.where(classname).findAll();
    }

    public RealmResults<RealmObject> getLimit(Class classname, int start, int end) {
        return realm.where(classname).between("index", start, end).findAll();
    }

    //query a single item with the given id
    public Object getItemByField(String field, String value, Class classname) {

        return realm.where(classname).equalTo(field, value).findFirst();
    }

    public Object getItemByField(String field, Boolean value, Class classname) {

        return realm.where(classname).equalTo(field, value).findFirst();
    }

    //check if Book.class is empty
    public boolean hasItem(Class classname) {

        return !realm.where(classname).findAll().isEmpty();
    }

    //query example
    public RealmResults<Object> queryObjects(Class classname, String field, String value) {

        return realm.where(classname)
                .equalTo(field, value)
                .findAll();

    }

    public RealmResults<Object> queryObjects(Class classname, String field, Boolean value) {

        return realm.where(classname)
                .equalTo(field, value)
                .findAll();

    }
}
