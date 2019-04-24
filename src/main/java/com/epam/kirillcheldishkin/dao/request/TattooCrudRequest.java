package com.epam.kirillcheldishkin.dao.request;

import com.epam.kirillcheldishkin.entity.Tattoo;

public interface TattooCrudRequest extends CrudRequest<Tattoo, Integer> {
    String getFindByNameLikeRequest();
}
