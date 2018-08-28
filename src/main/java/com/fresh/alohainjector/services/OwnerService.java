package com.fresh.alohainjector.services;

import com.fresh.alohainjector.domain.Owner;

public interface OwnerService {
    Owner getByName(String name);

    Owner getByNumber(Integer number);

    Owner getByOwnerType(Integer ownerType);
}
