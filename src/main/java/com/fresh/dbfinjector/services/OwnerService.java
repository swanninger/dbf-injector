package com.fresh.dbfinjector.services;

import com.fresh.dbfinjector.dataAloha.domain.Owner;

public interface OwnerService {
    Owner getByName(String name);

    Owner getByNumber(Integer number);

    Owner getByOwnerType(Integer ownerType);
}
