package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EsculturaMapperTest {

    private EsculturaMapper esculturaMapper;

    @BeforeEach
    public void setUp() {
        esculturaMapper = new EsculturaMapperImpl();
    }
}
