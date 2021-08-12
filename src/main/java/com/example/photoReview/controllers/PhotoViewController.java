package com.example.photoReview.controllers;

import android.util.Base64;
import com.example.photoReview.models.FirebaseRepository;
import com.example.photoReview.models.Photo;
import com.example.photoReview.models.Rating;
import com.example.photoReview.models.Tour;
import com.google.firebase.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PhotoViewController {

    @Autowired
    FirebaseRepository db;

    @GetMapping("/photos")
    public String photos(Model model) {
        model.addAttribute("controller", this);
        model.addAttribute("photos", setRatings(db.getPhotos(), db.getRatingList(), null));
        model.addAttribute("title", "Photos page");
//        model.addAttribute("ratings", db.getRatingList());
        return "photoView";
    }
    @GetMapping("/photos/{tourName}")
    public String photos(Model model, @PathVariable String tourName) {
        model.addAttribute("controller", this);
        model.addAttribute("photos", setRatings(db.getPhotos(), db.getRatingList(), tourName));
        model.addAttribute("title", "Photos page");
//        model.addAttribute("ratings", db.getRatingList());
        return "photoView";
    }

    @GetMapping("/image/imageDetails")
    String showProductDetails(@RequestParam("id") String id, Model model) {
        try {
            if (id != null) {
                List<Photo> photos = db.getPhotos();
                for (Photo photo:photos) {
                    if (photo.id.equals(id)) {
                        model.addAttribute("controller", this);
                        model.addAttribute("id", photo.id);
                        model.addAttribute("photoImage", photo.photo);
                        model.addAttribute("comment", photo.comment);
                        model.addAttribute("latitude", photo.latitude);
                        model.addAttribute("longitude", photo.longitude);
                        model.addAttribute("date", photo.currentDate);
                        model.addAttribute("emailUser", photo.EmailUser);
                        model.addAttribute("avaUserUri", photo.avaUserUri);

                        for (Tour tour:db.getTourList()) {
                            for (int i = 0; i < tour.review.size(); i++) {
                                if (tour.review.containsKey(photo.getKey())) {
                                    model.addAttribute("tour", tour.getName());
                                    i = tour.review.size();
                                }
                            }
                        }

                        return "imagedetails";
                    }
                }
                return "redirect:/photos";
            }
            return "redirect:/photos";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/photos";
        }
    }

    @GetMapping("/image/imageDetails/photoEdit")
    String editProductDetails(@RequestParam("id") String id, Model model) {
        try {
            if (id != null) {
                List<Photo> photos = db.getPhotos();
                for (Photo photo:photos) {
                    if (photo.id.equals(id)) {
                        model.addAttribute("id", photo.id);
                        model.addAttribute("comment", photo.comment);
                        model.addAttribute("latitude", photo.latitude);
                        model.addAttribute("longitude", photo.longitude);

                        return "photoEdit";
                    }
                }
                return "redirect:/photos";
            }
            return "redirect:/photos";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/photos";
        }
    }

    @PostMapping(value = "/image/imageDetails/photoEdit")
    public String submit(HttpServletRequest request) {
        for (Photo photo:db.getPhotos()) {
            if (photo.id.equals(request.getParameter("id"))) {
                DatabaseReference hopperRef = db.getRefDbPhotos().child(photo.getKey());
                Map<String, Object> hopperUpdates = new HashMap<>();
                hopperUpdates.put("comment", request.getParameter("comment"));
                hopperUpdates.put("latitude", Double.parseDouble(request.getParameter("latitude")));
                hopperUpdates.put("longitude", Double.parseDouble(request.getParameter("longitude")));

                hopperRef.updateChildrenAsync(hopperUpdates);
            }
        }
        return "redirect:/image/imageDetails/?id=" + request.getParameter("id");
    }

    public String convertTo(String str) {

        byte [] encodeByte = Base64.decode(str,Base64.URL_SAFE);
        String imageB64 = Base64.encodeToString(encodeByte, Base64.DEFAULT);
        return imageB64;

    }

    private List<Photo> setRatings(List<Photo> photos, List<Rating> ratingList, String tourName) {
        List<Photo> photoList = photos;
        int count = 0;
        Float avarange = 0f;

        for (Photo photo:photoList) {
            count = 0;
            avarange = 0f;
            for (Rating rating:ratingList) {
                if (photo.id.equals(rating.photoId)) {
                    avarange += rating.rate;
                    count++;
                }
            }
            if (count != 0) {
                photo.setRating(avarange/count);
            }
        }

        if (tourName != null) {
            List<Photo> sortPhotoList = new ArrayList<>();
                for (Tour tour:db.getTourList()) {
                    for (Photo photo:photoList) {
                    if (tour.getName().equals(tourName)) {
                        for (int i = 0; i < tour.review.size(); i++) {
                            if (tour.review.containsKey(photo.getKey())) {
                                sortPhotoList.add(photo);
                                i = tour.review.size();
                            }
                        }
                    }
                }
            }
            return sortPhotoList;
        }

        return photoList;
    }
}
