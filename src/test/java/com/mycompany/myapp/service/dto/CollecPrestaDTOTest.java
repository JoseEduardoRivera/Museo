package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CollecPrestaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollecPrestaDTO.class);
        CollecPrestaDTO collecPrestaDTO1 = new CollecPrestaDTO();
        collecPrestaDTO1.setId(1L);
        CollecPrestaDTO collecPrestaDTO2 = new CollecPrestaDTO();
        assertThat(collecPrestaDTO1).isNotEqualTo(collecPrestaDTO2);
        collecPrestaDTO2.setId(collecPrestaDTO1.getId());
        assertThat(collecPrestaDTO1).isEqualTo(collecPrestaDTO2);
        collecPrestaDTO2.setId(2L);
        assertThat(collecPrestaDTO1).isNotEqualTo(collecPrestaDTO2);
        collecPrestaDTO1.setId(null);
        assertThat(collecPrestaDTO1).isNotEqualTo(collecPrestaDTO2);
    }
}
