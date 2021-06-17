package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OtroObjDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OtroObjDTO.class);
        OtroObjDTO otroObjDTO1 = new OtroObjDTO();
        otroObjDTO1.setId(1L);
        OtroObjDTO otroObjDTO2 = new OtroObjDTO();
        assertThat(otroObjDTO1).isNotEqualTo(otroObjDTO2);
        otroObjDTO2.setId(otroObjDTO1.getId());
        assertThat(otroObjDTO1).isEqualTo(otroObjDTO2);
        otroObjDTO2.setId(2L);
        assertThat(otroObjDTO1).isNotEqualTo(otroObjDTO2);
        otroObjDTO1.setId(null);
        assertThat(otroObjDTO1).isNotEqualTo(otroObjDTO2);
    }
}
