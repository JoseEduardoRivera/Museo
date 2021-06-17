package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CollecPrestaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollecPresta.class);
        CollecPresta collecPresta1 = new CollecPresta();
        collecPresta1.setId(1L);
        CollecPresta collecPresta2 = new CollecPresta();
        collecPresta2.setId(collecPresta1.getId());
        assertThat(collecPresta1).isEqualTo(collecPresta2);
        collecPresta2.setId(2L);
        assertThat(collecPresta1).isNotEqualTo(collecPresta2);
        collecPresta1.setId(null);
        assertThat(collecPresta1).isNotEqualTo(collecPresta2);
    }
}
