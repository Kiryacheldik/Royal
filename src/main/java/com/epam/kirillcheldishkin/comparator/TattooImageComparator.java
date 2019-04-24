package com.epam.kirillcheldishkin.comparator;

import com.epam.kirillcheldishkin.entity.TattooImage;
import com.epam.kirillcheldishkin.entity.imageType.ImageType;

import java.util.Comparator;

public class TattooImageComparator implements Comparator<TattooImage> {
    @Override
    public int compare(TattooImage o1, TattooImage o2) {
        int priority1 = 0;
        int priority2 = 0;
        priority1 = setPriority(o1.getType());
        priority2 = setPriority(o2.getType());
        return Integer.compare(priority1, priority2);
    }

    public int setPriority(ImageType type) {
        switch (type) {
            case TITLE:
                return 3;
            case ORDINARY:
                return 2;
            case SKETCH:
                return 3;
            default:
                return -1;
        }
    }
}
