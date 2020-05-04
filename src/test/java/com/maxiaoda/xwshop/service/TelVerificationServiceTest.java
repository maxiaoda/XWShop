package com.maxiaoda.xwshop.service;

import com.maxiaoda.xwshop.entity.TelAndCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TelVerificationServiceTest {
    public static TelAndCode VALID_PARAMETER = new TelAndCode("13298158436", null);
    public static TelAndCode EMPTY_TEL = new TelAndCode(null, null);

    @Test
    public void returnTrueIfValid() {
        Assertions.assertTrue(new TelVerificationService()
                .verifyTelParameter(VALID_PARAMETER));
    }

    @Test
    public void returnFalseIfNotTel() {
        Assertions.assertFalse(new TelVerificationService()
                .verifyTelParameter(EMPTY_TEL));
        Assertions.assertFalse(new TelVerificationService()
                .verifyTelParameter(null));
    }
}
