package com.tornabene.covid19app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tornabene.covid19app.web.rest.TestUtil;

public class SintomoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sintomo.class);
        Sintomo sintomo1 = new Sintomo();
        sintomo1.setId(1L);
        Sintomo sintomo2 = new Sintomo();
        sintomo2.setId(sintomo1.getId());
        assertThat(sintomo1).isEqualTo(sintomo2);
        sintomo2.setId(2L);
        assertThat(sintomo1).isNotEqualTo(sintomo2);
        sintomo1.setId(null);
        assertThat(sintomo1).isNotEqualTo(sintomo2);
    }
}
