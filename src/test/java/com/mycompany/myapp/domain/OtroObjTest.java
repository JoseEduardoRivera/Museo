package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OtroObjTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OtroObj.class);
        OtroObj otroObj1 = new OtroObj();
        otroObj1.setId(1L);
        OtroObj otroObj2 = new OtroObj();
        otroObj2.setId(otroObj1.getId());
        assertThat(otroObj1).isEqualTo(otroObj2);
        otroObj2.setId(2L);
        assertThat(otroObj1).isNotEqualTo(otroObj2);
        otroObj1.setId(null);
        assertThat(otroObj1).isNotEqualTo(otroObj2);
    }
}
