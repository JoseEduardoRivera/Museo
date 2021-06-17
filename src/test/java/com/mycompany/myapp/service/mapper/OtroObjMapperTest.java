package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OtroObjMapperTest {

    private OtroObjMapper otroObjMapper;

    @BeforeEach
    public void setUp() {
        otroObjMapper = new OtroObjMapperImpl();
    }
}
