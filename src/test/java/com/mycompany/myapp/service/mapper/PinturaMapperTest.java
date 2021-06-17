package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PinturaMapperTest {

    private PinturaMapper pinturaMapper;

    @BeforeEach
    public void setUp() {
        pinturaMapper = new PinturaMapperImpl();
    }
}
