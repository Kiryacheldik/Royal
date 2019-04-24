package com.epam.kirillcheldishkin.dao.request;

import com.epam.kirillcheldishkin.entity.TattooImage;

public interface TattooImageRequest extends CrudRequest<TattooImage, Integer> {
    String getFindImagesForCertainTattooRequest();
}
