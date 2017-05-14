package com.objectpartners.plummer.junit5;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@DisplayName("StatesService")
@SpringBootTest
@ExtendWith(SpringExtension.class)
class StatesServiceTest {

    @Autowired
    private StatesService service;

    @Nested
    @DisplayName("getAll()")
    class GetAll {

        @Test
        void shouldReturnFiftyValues() {
            assertTrue(service.getAll().size() == 50);
        }

        @TestFactory
        @DisplayName("should return all values (Dynamic)")
        Collection<DynamicTest> testAllValuesReturnedWithDynamicTest() {
            return Utilities.readFromCsv("/data.csv").stream()
                    .map(values -> dynamicTest(
                            values[0],
                            () -> assertTrue(service.getAll().contains(new State(values[0], values[1])))
                            )
                    )
                    .collect(Collectors.toList());
        }

        @ParameterizedTest(name = "{0}")
        @CsvFileSource(resources = "/data.csv")
        @DisplayName("should return all values (Parameterized)")
        void testAllValuesReturnedWithParameterizedTest(String name, String capital) {
            assertTrue(service.getAll().contains(new State(name, capital)));
        }
    }
    
    @Nested
    @DisplayName("findByName(..)")
    class FindByName {
        @Test
        @DisplayName("null value should throw error")
        void testNullValue() {
            assertThrows(IllegalArgumentException.class, () -> service.findByName(null));
        }

        @Test
        @DisplayName("blank value should throw error")
        void testBlankValue() {
            assertThrows(IllegalArgumentException.class, () -> service.findByName(" "));
        }

        @Test
        @DisplayName("empty value should throw error")
        void testEmptyValue() {
            assertThrows(IllegalArgumentException.class, () -> service.findByName(""));
        }

        @Test
        @DisplayName("matching value should return value")
        void testMatchingValue() {
            assertNotNull(service.findByName("Iowa"));
        }

        @Test
        @DisplayName("non-matching value should return null")
        void testNonMatchingvalue() {
            assertNull(service.findByName("blorg"));
        }
    }

    @Nested
    @DisplayName("getAllMatching(..)")
    class GetAllMatching {
        @Test
        @DisplayName("regex that matches one should return result")
        void testMatchingRegex() {
            assertTrue(service.getAllMatching("laska").size() == 1);
        }

        @Test
        @DisplayName("regex that matches none should return empty list")
        void testNonMatchingRegex() {
            assertTrue(service.getAllMatching("^Hampshire").isEmpty());
        }

        @Test
        @DisplayName("regex that matches multiple values should return all")
        void testMultiMatch() {
            assertTrue(service.getAllMatching("aska").size() == 2);
        }
    }
}
