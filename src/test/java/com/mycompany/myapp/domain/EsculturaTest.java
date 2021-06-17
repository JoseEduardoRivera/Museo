package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EsculturaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Escultura.class);
        Escultura escultura1 = new Escultura();
        escultura1.setId(1L);
        Escultura escultura2 = new Escultura();
        escultura2.setId(escultura1.getId());
        assertThat(escultura1).isEqualTo(escultura2);
        escultura2.setId(2L);
        assertThat(escultura1).isNotEqualTo(escultura2);
        escultura1.setId(null);
        assertThat(escultura1).isNotEqualTo(escultura2);
    }
}
