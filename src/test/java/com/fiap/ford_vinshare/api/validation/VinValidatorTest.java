package com.fiap.ford_vinshare.api.validation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VinValidatorTest {

    private final VinValidator validator = new VinValidator();

    @Test
    void deveAceitarVinValido() {
        assertThat(validator.isValid("9BFZH55L9P8123456", null)).isTrue();
    }

    @Test
    void deveRejeitarVinComTamanhoIncorreto() {
        assertThat(validator.isValid("9BFZH55L9P812345", null)).isFalse();
    }

    @Test
    void deveRejeitarVinComCaracteresInvalidos() {
        assertThat(validator.isValid("9BFZH55L9PI123456", null)).isFalse();
    }
}
