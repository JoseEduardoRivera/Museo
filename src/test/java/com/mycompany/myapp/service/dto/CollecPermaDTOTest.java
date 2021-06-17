package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CollecPermaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollecPermaDTO.class);
        CollecPermaDTO collecPermaDTO1 = new CollecPermaDTO();
        collecPermaDTO1.setId(1L);
        CollecPermaDTO collecPermaDTO2 = new CollecPermaDTO();
        assertThat(collecPermaDTO1).isNotEqualTo(collecPermaDTO2);
        collecPermaDTO2.setId(collecPermaDTO1.getId());
        assertThat(collecPermaDTO1).isEqualTo(collecPermaDTO2);
        collecPermaDTO2.setId(2L);
        assertThat(collecPermaDTO1).isNotEqualTo(collecPermaDTO2);
        collecPermaDTO1.setId(null);
        assertThat(collecPermaDTO1).isNotEqualTo(collecPermaDTO2);
    }
}
