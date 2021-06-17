package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExhibicionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExhibicionDTO.class);
        ExhibicionDTO exhibicionDTO1 = new ExhibicionDTO();
        exhibicionDTO1.setId(1L);
        ExhibicionDTO exhibicionDTO2 = new ExhibicionDTO();
        assertThat(exhibicionDTO1).isNotEqualTo(exhibicionDTO2);
        exhibicionDTO2.setId(exhibicionDTO1.getId());
        assertThat(exhibicionDTO1).isEqualTo(exhibicionDTO2);
        exhibicionDTO2.setId(2L);
        assertThat(exhibicionDTO1).isNotEqualTo(exhibicionDTO2);
        exhibicionDTO1.setId(null);
        assertThat(exhibicionDTO1).isNotEqualTo(exhibicionDTO2);
    }
}
