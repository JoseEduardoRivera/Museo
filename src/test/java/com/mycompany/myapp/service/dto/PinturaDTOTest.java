package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PinturaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PinturaDTO.class);
        PinturaDTO pinturaDTO1 = new PinturaDTO();
        pinturaDTO1.setId(1L);
        PinturaDTO pinturaDTO2 = new PinturaDTO();
        assertThat(pinturaDTO1).isNotEqualTo(pinturaDTO2);
        pinturaDTO2.setId(pinturaDTO1.getId());
        assertThat(pinturaDTO1).isEqualTo(pinturaDTO2);
        pinturaDTO2.setId(2L);
        assertThat(pinturaDTO1).isNotEqualTo(pinturaDTO2);
        pinturaDTO1.setId(null);
        assertThat(pinturaDTO1).isNotEqualTo(pinturaDTO2);
    }
}
