package com.dh.clinica.utils;

import java.time.LocalDate;
import java.util.Date;

public class Utils {
    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }
}
