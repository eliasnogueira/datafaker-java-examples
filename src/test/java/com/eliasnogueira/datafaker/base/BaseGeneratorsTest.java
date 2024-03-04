/*
 * MIT License
 *
 * Copyright (c) 2024 Elias Nogueira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.eliasnogueira.datafaker.base;

import net.datafaker.Faker;
import net.datafaker.providers.base.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

class BaseGeneratorsTest {

    private static final Logger LOGGER = LogManager.getLogger(BaseGeneratorsTest.class);
    private Faker faker = new Faker();

    @Test
    void basicDataGeneration() {
        String name = faker.name().fullName();
        String address = faker.address().fullAddress();
        String iban = faker.finance().iban("NL");
        String creditCardNumber = faker.business().creditCardNumber();
        String image = faker.internet().image();
        String birthday = faker.date().birthday("yyyy/MM/dd");

        Options opt = faker.options();
        Day day = opt.option(Day.class);

        LOGGER.info(name);
        LOGGER.info(address);
        LOGGER.info(iban);
        LOGGER.info(creditCardNumber);
        LOGGER.info(image);
        LOGGER.info(birthday);
        LOGGER.info(day);

        /*
         * Changing the locale
         */
        faker = new Faker(Locale.JAPAN);
        String nameInJapanese = faker.name().fullName();

        LOGGER.info(nameInJapanese);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(name).isNotEmpty();
            softly.assertThat(address).isNotEmpty();
            softly.assertThat(iban).startsWith("NL");
            softly.assertThat(creditCardNumber).isNotEmpty();
            softly.assertThat(image).isNotEmpty();
            softly.assertThat(birthday).isNotEmpty();
            softly.assertThat(nameInJapanese).isNotEmpty();
            softly.assertThat(day).isNotNull();
        });
    }

    public enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
        THURSDAY, FRIDAY, SATURDAY
    }
}
