package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ObjArteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObjArteDTO.class);
        ObjArteDTO objArteDTO1 = new ObjArteDTO();
        objArteDTO1.setId(1L);
        ObjArteDTO objArteDTO2 = new ObjArteDTO();
        assertThat(objArteDTO1).isNotEqualTo(objArteDTO2);
        objArteDTO2.setId(objArteDTO1.getId());
        assertThat(objArteDTO1).isEqualTo(objArteDTO2);
        objArteDTO2.setId(2L);
        assertThat(objArteDTO1).isNotEqualTo(objArteDTO2);
        objArteDTO1.setId(null);
        assertThat(objArteDTO1).isNotEqualTo(objArteDTO2);
    }
}
