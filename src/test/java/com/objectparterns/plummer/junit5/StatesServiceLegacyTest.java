package com.objectparterns.plummer.junit5;

import com.objectpartners.plummer.junit5.Application;
import com.objectpartners.plummer.junit5.StatesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@SpringBootTest(classes = { Application.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class StatesServiceLegacyTest {

    @Autowired
    private StatesService statesService;

    @Test
    public void testGetMatchingWithNonMatchingValue() {
        assertTrue(statesService.getMatching("blorg").isEmpty());
    }

    @Test
    public void testGetMatchingWithSingleResult() {
        assertTrue(statesService.getMatching("^Alask").size() == 1);
    }

    @Test
    public void testGetMatchingWithMultipleResults() {
        assertTrue(statesService.getMatching("^Ala").size() == 2);
    }
}
