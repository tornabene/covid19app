package com.tornabene.covid19app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tornabene.covid19app.web.rest.TestUtil;

public class FarmacoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Farmaco.class);
        Farmaco farmaco1 = new Farmaco();
        farmaco1.setId(1L);
        Farmaco farmaco2 = new Farmaco();
        farmaco2.setId(farmaco1.getId());
        assertThat(farmaco1).isEqualTo(farmaco2);
        farmaco2.setId(2L);
        assertThat(farmaco1).isNotEqualTo(farmaco2);
        farmaco1.setId(null);
        assertThat(farmaco1).isNotEqualTo(farmaco2);
    }
}
