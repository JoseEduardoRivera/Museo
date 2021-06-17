package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObjArteMapperTest {

    private ObjArteMapper objArteMapper;

    @BeforeEach
    public void setUp() {
        objArteMapper = new ObjArteMapperImpl();
    }
}
