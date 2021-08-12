package com.example.photoReview.models;

import com.example.photoReview.FirebaseInitializer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FirebaseRepository {
    @Autowired
    FirebaseInitializer db;
    List<Photo> photos;
    List<Rating> ratingList;
    List<Tour> tourList;
    DatabaseReference collectionPhotos;
    DatabaseReference ratingTableRef;
    DatabaseReference toursRef;

    public List<Photo> getPhotos() {
        return photos;
    }
    public DatabaseReference getRefDbPhotos() { return collectionPhotos; }
    public List<Tour> getTourList() {
        return tourList;
    }

    public List<Rating> getRatingList() {
        return ratingList;
    }

    @PostConstruct
    private void initRep() {
        photos = new ArrayList<Photo>();
        ratingList = new ArrayList<Rating>();
        tourList = new ArrayList<Tour>();
        ratingTableRef = db.getDatabase().getInstance().getReference("ratings");
        collectionPhotos = db.getDatabase().getInstance().getReference("photoItems");
        toursRef = db.getDatabase().getInstance().getReference("tours");

        ratingTableRef.addValueEventListener(ratingListener);
        collectionPhotos.addValueEventListener(postListener);
        toursRef.addValueEventListener(toursListener);
    }

    ValueEventListener ratingListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot ds:dataSnapshot.getChildren()) {
                for (DataSnapshot data:ds.getChildren()) {
                    Rating rating = new Rating(ds.getKey(), data.getKey(), data.getValue(Float.class));
                    ratingList.add(rating);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
        }
    };

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            photos.clear();
            for (DataSnapshot ds:dataSnapshot.getChildren()) {

                Photo photo = ds.getValue(Photo.class);
                photo.setKey(ds.getKey());

                photos.add(photo);
            }

        }

        @Override
        public void onCancelled(@NotNull DatabaseError error) {
            // Getting Post failed, log a message
        }
    };
    ValueEventListener toursListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot ds:dataSnapshot.getChildren()) {
                Tour tour = ds.getValue(Tour.class);
                tour.setName(ds.getKey());

                tourList.add(tour);
            }
        }

        @Override
        public void onCancelled(@NotNull DatabaseError error) {
            // Getting Post failed, log a message
        }
    };
}
