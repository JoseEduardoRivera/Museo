package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExhibicionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Exhibicion.class);
        Exhibicion exhibicion1 = new Exhibicion();
        exhibicion1.setId(1L);
        Exhibicion exhibicion2 = new Exhibicion();
        exhibicion2.setId(exhibicion1.getId());
        assertThat(exhibicion1).isEqualTo(exhibicion2);
        exhibicion2.setId(2L);
        assertThat(exhibicion1).isNotEqualTo(exhibicion2);
        exhibicion1.setId(null);
        assertThat(exhibicion1).isNotEqualTo(exhibicion2);
    }
}
