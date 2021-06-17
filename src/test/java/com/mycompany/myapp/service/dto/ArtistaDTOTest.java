package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtistaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtistaDTO.class);
        ArtistaDTO artistaDTO1 = new ArtistaDTO();
        artistaDTO1.setId(1L);
        ArtistaDTO artistaDTO2 = new ArtistaDTO();
        assertThat(artistaDTO1).isNotEqualTo(artistaDTO2);
        artistaDTO2.setId(artistaDTO1.getId());
        assertThat(artistaDTO1).isEqualTo(artistaDTO2);
        artistaDTO2.setId(2L);
        assertThat(artistaDTO1).isNotEqualTo(artistaDTO2);
        artistaDTO1.setId(null);
        assertThat(artistaDTO1).isNotEqualTo(artistaDTO2);
    }
}
