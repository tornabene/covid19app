package com.tornabene.covid19app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tornabene.covid19app.web.rest.TestUtil;

public class PatologiaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Patologia.class);
        Patologia patologia1 = new Patologia();
        patologia1.setId(1L);
        Patologia patologia2 = new Patologia();
        patologia2.setId(patologia1.getId());
        assertThat(patologia1).isEqualTo(patologia2);
        patologia2.setId(2L);
        assertThat(patologia1).isNotEqualTo(patologia2);
        patologia1.setId(null);
        assertThat(patologia1).isNotEqualTo(patologia2);
    }
}
