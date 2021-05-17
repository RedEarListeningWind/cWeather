package com.crtf.weather.ui.main.location;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import static org.junit.Assert.*;

public class SwitchLocationFragmentTest {

    @Test
    public void updateUiData() {
        System.out.println(LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.SIMPLIFIED_CHINESE));
    }
}