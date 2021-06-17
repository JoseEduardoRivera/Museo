package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ObjArteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObjArte.class);
        ObjArte objArte1 = new ObjArte();
        objArte1.setId(1L);
        ObjArte objArte2 = new ObjArte();
        objArte2.setId(objArte1.getId());
        assertThat(objArte1).isEqualTo(objArte2);
        objArte2.setId(2L);
        assertThat(objArte1).isNotEqualTo(objArte2);
        objArte1.setId(null);
        assertThat(objArte1).isNotEqualTo(objArte2);
    }
}
