package com.croteam.crobird.database;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.croteam.crobird.model.User;

import io.realm.Realm;
import io.realm.RealmResults;

public class UserHelper extends RealmController {

    private static UserHelper instance;
    private final Realm realm;
    private static Application context;

    public UserHelper(Application application) {
        super(application);
        realm = super.getRealm();
    }

    public static UserHelper with(Fragment fragment) {

        if (instance == null) {
            context = fragment.getActivity().getApplication();
            instance = new UserHelper(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static UserHelper with(Activity activity) {

        if (instance == null) {
            context = activity.getApplication();
            instance = new UserHelper(activity.getApplication());
        }
        return instance;
    }

    public static UserHelper with(Application application) {

        if (instance == null) {
            context = application;
            instance = new UserHelper(application);
        }
        return instance;
    }

    public UserHelper getInstance() {

        return instance;
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
        realm.delete(User.class);
        realm.commitTransaction();
    }

    //find all objects in the Book.class
    public RealmResults<User> getUsers() {

        return realm.where(User.class).findAll();
    }

    //query a single item with the given id
//    public User getUserById(String id) {
//
//        return realm.where(User.class).equalTo(User.ID, id).findFirst();
//    }

    public User getUserById(String id) {

        return realm.where(User.class).equalTo(User.ID, id).findFirst();
    }

    public User getUserByPhone(String phone) {

        return realm.where(User.class).equalTo(User.PHONE, phone).findFirst();
    }

    //check if Book.class is empty
    public boolean hasUsers() {

        return realm.where(User.class).findAll().size()>0;
    }

    //query example
    public RealmResults<User> queryedUsers() {

        return realm.where(User.class)
                .findAll();

    }

    public void addUser(User user){
        if(!realm.isInTransaction()) realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }

    public RealmResults<User> searchUser(String value) {

        return realm.where(User.class)
                .contains(User.SEARCH_KEY, value)
                .findAll();

    }

    public RealmResults<User> searchUserByCategory(String category, String value) {

        return realm.where(User.class)
                .equalTo(User.JOB, category)
                .contains(User.SEARCH_KEY, value)
                .findAll();

    }
}
