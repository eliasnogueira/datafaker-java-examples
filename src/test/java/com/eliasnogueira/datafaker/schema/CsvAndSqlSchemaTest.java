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
import net.datafaker.transformations.CsvTransformer;
import net.datafaker.transformations.Schema;
import net.datafaker.transformations.sql.SqlDialect;
import net.datafaker.transformations.sql.SqlTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static net.datafaker.transformations.Field.field;
import static org.assertj.core.api.Assertions.assertThat;

class CsvAndSqlSchemaTest extends SchemaBase {

    private static final Logger LOGGER = LogManager.getLogger(CsvAndSqlSchemaTest.class);
    private static final String COMMA = ",";
    private final Faker faker = new Faker();


    @Test
    @DisplayName("Should generate a CSV output")
    void generateCsvData() {
        CsvTransformer<String> csvTransformer = CsvTransformer.<String>builder().header(true).separator(COMMA).build();

        String csvOutput = csvTransformer.generate(retrieveSchema(), 5);
        LOGGER.info(csvOutput);

        assertThat(csvOutput).contains(COMMA);

        writeFile("auto-generated.csv", csvOutput);
    }

    @Test
    @DisplayName("Should generate a SQL output")
    void generateSqlData() {
        SqlTransformer<String> sqlTransformer =
                new SqlTransformer.SqlTransformerBuilder<String>()
                        .batch(5)
                        .tableName("USERS")
                        .dialect(SqlDialect.POSTGRES)
                        .build();
        String sqlOutput = sqlTransformer.generate(retrieveSchema(), 5);
        LOGGER.info(sqlOutput);

        assertThat(sqlOutput).contains("INSERT INTO", "VALUES");
        writeFile("auto-generated.sql", sqlOutput);
    }

    private Schema<String, String> retrieveSchema() {
        return Schema.of(
                field("name", () -> faker.name().nameWithMiddle()),
                field("username", () -> faker.internet().username()),
                field("email", () -> faker.internet().emailAddress()),
                field("password", () -> faker.internet().password(8, 12))
        );
    }
}
