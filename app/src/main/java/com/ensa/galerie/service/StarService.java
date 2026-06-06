package com.ensa.galerie.service;

import com.ensa.galerie.model.dao.IDao;
import com.ensa.galerie.model.entity.Star;

import java.util.ArrayList;
import java.util.List;

public class StarService implements IDao<Star> {
    private List<Star> stars;
    private static StarService instance;

    private StarService() {
        stars = new ArrayList<>();
        seed();
    }

    private void seed() {
        stars.add(new Star(
                "Tom Cruise",
                "https://th.bing.com/th/id/R.19a7003010c330ddbf280d962567f9ef?rik=GPQpXYxYAWB9bA&pid=ImgRaw&r=0",
                4.2f
        ));

        stars.add(new Star(
                "Mark Ruffalo",
                "https://tse3.mm.bing.net/th/id/OIP.pcI2mkJgM4vZwjQ7-c5vIAHaLG?rs=1&pid=ImgDetMain&o=7&rm=3",
                3.2f
        ));
        stars.add(new Star(
                "Robert Downey Jr.",
                "https://th.bing.com/th/id/OIP.JSlQ0Cxz0ks1FpuOYjQyTAHaJ3?w=113&h=180&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3",
                4.8f
        ));

        stars.add(new Star(
                "Brad Pitt",
                "https://th.bing.com/th/id/OIP.io7ADEUGW9tsYLf6vPIOeQHaLH?w=204&h=306&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3",
                4.7f
        ));
    }

    public static StarService getInstance() {
        if (instance == null) {
            instance = new StarService();
        }
        return instance;
    }

    @Override
    public List<Star> findAll() {
        return stars;
    }

    @Override
    public Star findById(int id) {
        for (Star s : stars) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    @Override
    public void add(Star o) {
        stars.add(o);
    }

    @Override
    public void del(Star o) {
        stars.remove(o);
    }

    @Override
    public void update(Star o) {
        for (int i = 0; i < stars.size(); i++) {
            if (stars.get(i).getId() == o.getId()) {
                stars.set(i, o);
                break;
            }
        }
    }
}
