package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EsculturaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EsculturaDTO.class);
        EsculturaDTO esculturaDTO1 = new EsculturaDTO();
        esculturaDTO1.setId(1L);
        EsculturaDTO esculturaDTO2 = new EsculturaDTO();
        assertThat(esculturaDTO1).isNotEqualTo(esculturaDTO2);
        esculturaDTO2.setId(esculturaDTO1.getId());
        assertThat(esculturaDTO1).isEqualTo(esculturaDTO2);
        esculturaDTO2.setId(2L);
        assertThat(esculturaDTO1).isNotEqualTo(esculturaDTO2);
        esculturaDTO1.setId(null);
        assertThat(esculturaDTO1).isNotEqualTo(esculturaDTO2);
    }
}
