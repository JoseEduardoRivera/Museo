package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArtistaMapperTest {

    private ArtistaMapper artistaMapper;

    @BeforeEach
    public void setUp() {
        artistaMapper = new ArtistaMapperImpl();
    }
}
