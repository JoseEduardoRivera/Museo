package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CollecPermaMapperTest {

    private CollecPermaMapper collecPermaMapper;

    @BeforeEach
    public void setUp() {
        collecPermaMapper = new CollecPermaMapperImpl();
    }
}
