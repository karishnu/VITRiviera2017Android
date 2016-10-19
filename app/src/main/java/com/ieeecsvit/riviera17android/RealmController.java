package com.ieeecsvit.riviera17android;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;
import com.ieeecsvit.riviera17android.models.Event;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        Realm.init(application);
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }


    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {
        realm.waitForChange();
    }

    //clear all objects from Book.class
    public void clearAll() {
        realm.beginTransaction();
        realm.delete(Event.class);
        realm.commitTransaction();
    }

    //find all objects in the Book.class
    public RealmResults<Event> getEvents(String category) {
        return realm.where(Event.class).equalTo("eventCategory",category).findAll();
    }

    public Event getEvent(String eventId){
        return realm.where(Event.class).equalTo("id", eventId).findFirst();
    }

    /*    //query a single item with the given id
    public Event getEvent(String id) {

        return realm.where(Event.class).equalTo("id", id).findFirst();
    }*/

    //check if Book.class is empty
    public boolean hasEvents() {
        return !realm.where(Event.class).findAll().isEmpty();
    }

    public void setFavourite(String eventId, Boolean favourite){
        Event event = realm.where(Event.class).equalTo("id",eventId).findFirst();
        realm.beginTransaction();
        event.checked = favourite;
        realm.commitTransaction();
    }

    //query example
    /*    public RealmResults<Event> queryedBooks() {

        return realm.where(Event.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }*/
}