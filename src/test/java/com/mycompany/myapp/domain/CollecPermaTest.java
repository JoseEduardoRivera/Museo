package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CollecPermaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollecPerma.class);
        CollecPerma collecPerma1 = new CollecPerma();
        collecPerma1.setId(1L);
        CollecPerma collecPerma2 = new CollecPerma();
        collecPerma2.setId(collecPerma1.getId());
        assertThat(collecPerma1).isEqualTo(collecPerma2);
        collecPerma2.setId(2L);
        assertThat(collecPerma1).isNotEqualTo(collecPerma2);
        collecPerma1.setId(null);
        assertThat(collecPerma1).isNotEqualTo(collecPerma2);
    }
}
