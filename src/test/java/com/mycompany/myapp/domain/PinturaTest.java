package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PinturaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pintura.class);
        Pintura pintura1 = new Pintura();
        pintura1.setId(1L);
        Pintura pintura2 = new Pintura();
        pintura2.setId(pintura1.getId());
        assertThat(pintura1).isEqualTo(pintura2);
        pintura2.setId(2L);
        assertThat(pintura1).isNotEqualTo(pintura2);
        pintura1.setId(null);
        assertThat(pintura1).isNotEqualTo(pintura2);
    }
}
