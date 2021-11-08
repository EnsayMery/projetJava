package business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonManagerTest {

    private PersonManager manager;

    @BeforeEach
    void setUp() {
        manager = new PersonManager();
    }

    @Test
    void test1() {
        try {
            assertEquals(5626.10,manager.getCaloriesFor("chrichri"),0.01);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void test2() {
        try {
            assertEquals(6495.1,manager.getCaloriesFor("vinvin"),0.01);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void test3() {
        try {
            assertEquals(3757.78,manager.getCaloriesFor("gluglu"),0.01);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}