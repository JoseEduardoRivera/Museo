package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtistaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Artista.class);
        Artista artista1 = new Artista();
        artista1.setId(1L);
        Artista artista2 = new Artista();
        artista2.setId(artista1.getId());
        assertThat(artista1).isEqualTo(artista2);
        artista2.setId(2L);
        assertThat(artista1).isNotEqualTo(artista2);
        artista1.setId(null);
        assertThat(artista1).isNotEqualTo(artista2);
    }
}
