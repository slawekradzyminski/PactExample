package com.awesome.testing.state;

import com.awesome.testing.service.InformationService;

import static com.awesome.testing.data.DatabaseData.MESSI_DB_ENTRY;
import static com.awesome.testing.data.DatabaseData.RONALDO_DB_ENTRY;

public enum ContractState {
    DEFAULT("Two entries exist") {
        @Override
        public void setState(InformationService informationService) {
            informationService.resetIdAutoincrement();
            informationService.getAllInformation().forEach(informationService::delete);
            informationService.save(RONALDO_DB_ENTRY);
            informationService.save(MESSI_DB_ENTRY);
        }
    },

    EMPTY("Empty database state") {
        @Override
        public void setState(InformationService informationService) {
            informationService.resetIdAutoincrement();
            informationService.getAllInformation().forEach(informationService::delete);
        }
    };

    String state;

    ContractState(String state) {
        this.state = state;
    }

    public String description() {
        return state;
    }

    public abstract void setState(InformationService informationService);
}


