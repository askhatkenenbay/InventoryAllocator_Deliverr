import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class InventoryAllocatorTest {
    @Test
    public void testNull() {
        assertEquals("[]", InventoryAllocator.getBestShipmentOrder(null));
    }

    @Test
    public void testEmpty() {
        assertEquals("[]", InventoryAllocator.getBestShipmentOrder(""));
    }

    @Test
    public void testEmptyOrder() {
        String input = "[{ name: owd, inventory: { apple: 1 } }]";
        String expected = "[]";
        assertEquals(expected, InventoryAllocator.getBestShipmentOrder(input));
    }

    @Test
    public void testEmptyWarehouse() {
        String input = "{ apple: 1 }, []";
        String expected = "[]";
        assertEquals(expected, InventoryAllocator.getBestShipmentOrder(input));
    }

    @Test
    public void testExactMatch() {
        String input = "{ apple: 1 }, [{ name: owd, inventory: { apple: 1 } }]";
        String expected = "[{ owd: { apple: 1 } }]";
        assertEquals(expected, InventoryAllocator.getBestShipmentOrder(input));
    }

    @Test
    public void testExactNotEnough() {
        String input = "{ apple: 1 }, [{ name: owd, inventory: { apple: 0 } }]";
        String expected = "[]";
        assertEquals(expected, InventoryAllocator.getBestShipmentOrder(input));
    }

    @Test
    public void testExactSplit() {
        String input = "{ apple: 10 }, [{ name: owd, inventory: { apple: 5 } }, { name: dm, inventory: { apple: 5 }}]";
        String expected = "[{ owd: { apple: 5 } }, { dm: { apple: 5 } }]";
        assertEquals(expected, InventoryAllocator.getBestShipmentOrder(input));
    }

    @Test
    public void testMineOne() {
        String input = "{ apple: 10, banana: 10, orange: 10}, [{ name: first, inventory: { apple: 100 } }, { name: second, inventory: { mango: 5 }}, { name: third, inventory: { orange: 5 }}, { name: fourth, inventory: { apple: 5, orange: 5, banana: 100}}]";
        String expected = "[{ first: { apple: 10 } }, { third: { orange: 5 } }, { fourth: { banana: 10, orange: 5 } }]";
        assertEquals(expected, InventoryAllocator.getBestShipmentOrder(input));
    }

    @Test
    public void testMineTwo() {
        String input = "{ apple: 3, banana: 3, orange: 3}, [{ name: first, inventory: { apple: 3, orange: 0, banana: 0} }, { name: second, inventory: { apple: 0, orange: 3, banana: 0}}, { name: third, inventory: { apple: 0, orange: 0, banana: 3}}]";
        String expected = "[{ first: { apple: 3 } }, { second: { orange: 3 } }, { third: { banana: 3 } }]";
        assertEquals(expected, InventoryAllocator.getBestShipmentOrder(input));
    }

    @Test
    public void testZero() {
        String input = "{ apple: 0 }, [{ name: owd, inventory: { apple: 0 } }]";
        String expected = "[]";
        assertEquals(expected, InventoryAllocator.getBestShipmentOrder(input));
    }

    @Test
    public void testNegativeNum() {
        String input = "{ apple: -1 }, [{ name: owd, inventory: { apple: -1 } }]";
        String expected = "[]";
        assertEquals(expected, InventoryAllocator.getBestShipmentOrder(input));
    }

    @Test
    public void testNoMatch() {
        String input = "{ apple: 10 }, [{ name: owd, inventory: { mango: 5 } }, { name: dm, inventory: { mango: 5 }}]";
        String expected = "[]";
        assertEquals(expected, InventoryAllocator.getBestShipmentOrder(input));
    }


    @Test(expected = NumberFormatException.class)
    public void testNumberFormatOne() {
        String input = "{ apple: asd }, [{ name: owd, inventory: { mango: 10 } }, { name: dm, inventory: { mango: 5 }}]";
        InventoryAllocator.getBestShipmentOrder(input);
    }

    @Test(expected = NumberFormatException.class)
    public void testNumberFormatTwo() {
        String input = "{ apple: 10 }, [{ name: owd, inventory: { mango: asd } }, { name: dm, inventory: { mango: asd }}]";
        InventoryAllocator.getBestShipmentOrder(input);
    }

    @Test
    public void testWrongInput() {
        String input = "{[][}]";
        String expected = "[]";
        assertEquals(expected, InventoryAllocator.getBestShipmentOrder(input));
    }
}
