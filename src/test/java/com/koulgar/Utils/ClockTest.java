package com.hemlak.realtyservice.utils;

import com.koulgar.Utils.Clock;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDateTime;

public class ClockTest {

    @Test
    public void it_should_get_now() {

        //Given
        Clock.freeze();
        final LocalDateTime now = Clock.now();

        //When
        final LocalDateTime actualNow = Clock.now();

        //Then
        Assertions.assertThat(actualNow).isEqualTo(now);
        Clock.unfreeze();
    }
}