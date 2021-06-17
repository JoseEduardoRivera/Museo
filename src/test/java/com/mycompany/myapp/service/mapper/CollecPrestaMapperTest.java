package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CollecPrestaMapperTest {

    private CollecPrestaMapper collecPrestaMapper;

    @BeforeEach
    public void setUp() {
        collecPrestaMapper = new CollecPrestaMapperImpl();
    }
}
