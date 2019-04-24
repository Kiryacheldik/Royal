package com.epam.kirillcheldishkin.dao.request;

import com.epam.kirillcheldishkin.entity.TattooRating;

public interface TattooRatingCrudRequest extends CrudRequest<TattooRating, Integer> {
    String getFindRatingsForCertainTattooRequest();
    String getFindRatingByUserAndTattooIdRequest();
}
