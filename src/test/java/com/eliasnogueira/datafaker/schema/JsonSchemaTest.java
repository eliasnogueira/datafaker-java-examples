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
package com.eliasnogueira.datafaker.schema;

import net.datafaker.Faker;
import net.datafaker.transformations.JsonTransformer;
import net.datafaker.transformations.Schema;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThatCode;

class JsonSchemaTest {

    private static final Logger LOGGER = LogManager.getLogger(JsonSchemaTest.class);
    private final Faker faker = new Faker();

    @Test
    void generateJsonOutput() {
        JsonTransformer<Object> jsonTransformer = JsonTransformer.builder().build();
        String jsonOutput = jsonTransformer.generate(retrieveCompositeSchema(), 2);

        LOGGER.info(jsonOutput);
        assertThatCode(() -> new JSONArray(jsonOutput)).doesNotThrowAnyException();
    }

    private Schema<Object, ?> retrieveCompositeSchema() {
        return Schema.of(
                field("name", () -> faker.name().nameWithMiddle()),
                field("street", () -> faker.address().streetAddress()),
                field("secondaryAddress", () -> faker.address().secondaryAddress()),
                field("postcode", () -> faker.address().postcode()),
                field("city", () -> faker.address().cityName())
        );
    }
}
