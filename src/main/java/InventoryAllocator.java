import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class InventoryAllocator {
    private static final String EXIT_WORD = "EXIT";
    private static final String NO_ALLOCATION = "[]";
    private static final String ENTER_MESSAGE = "Enter items (Enter \'exit\' to finish): ";
    private static final String RESULT_MESSAGE = "Best shipment is: ";
    private static final String ORDER_WAREHOUSE_DIVISOR = ", \\[";
    private static final String ITEM_DIVISOR = ",";
    private static final String ITEM_BRACKET_REGEX = "\\{|\\}";
    private static final String WAREHOUSE_DIVISOR = "},";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str;
        while (true) {
            System.out.println(ENTER_MESSAGE);
            str = scanner.nextLine();
            if (str == null || EXIT_WORD.equals(str.toUpperCase())) {
                break;
            }
            System.out.println(RESULT_MESSAGE + getBestShipmentOrder(str));
        }
    }

    /**
     * @param str String input parameter
     * @return best shipment of order, if input is not correct returns []
     * <p>
     * Takes input string, validates that it is not null and empty.
     * Then divides input into order and warehouse part using ORDER_WAREHOUSE_DIVISOR
     * Returns result of overloaded method getBestShipmentOrder
     */
    public static String getBestShipmentOrder(String str) {
        if (str == null || str.isEmpty()) {
            return NO_ALLOCATION;
        }
        String[] arr = str.split(ORDER_WAREHOUSE_DIVISOR);
        if (arr.length != 2) {
            System.out.println("Illegal Input:" + str);
            return NO_ALLOCATION;
        }
        return getBestShipmentOrder(arr[0], arr[1]);
    }

    /**
     * @param items      String input of orders
     * @param warehouses String input of all warehouses
     * @return best shipment of order, if input is not correct returns []
     * <p>
     * Validates that both inputs are not null and not empty
     * Stores orders in HashMap, where key is name and value is amount needed
     * Stores warehouses in List, where first warehouse is less expensive than next one
     * Starting from first one, checks every warehouse and builds result string
     * If at the end there are still orders left, returns [], else result
     */
    public static String getBestShipmentOrder(String items, String warehouses) {
        if (items == null || items.isEmpty() || warehouses == null || warehouses.isEmpty()) {
            return NO_ALLOCATION;
        }
        HashMap<String, Integer> itemMap = parseItems(items);
        List<Warehouse> warehouseList = parseWarehouses(warehouses);
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < warehouseList.size(); i++) {
            String currName = warehouseList.get(i).getName();
            HashMap<String, Integer> currMap = warehouseList.get(i).getInventory();
            HashMap<String, Integer> shipment = new HashMap<>();
            HashMap<String, Integer> tempMap = new HashMap<>(itemMap);
            for (String key : itemMap.keySet()) {
                int count = itemMap.get(key);
                if (currMap.containsKey(key)) {
                    if (currMap.get(key) < count) {
                        count -= currMap.get(key);
                        tempMap.put(key, count);
                        shipment.put(key, currMap.get(key));
                    } else {
                        tempMap.remove(key);
                        shipment.put(key, count);
                    }

                }
            }
            itemMap = tempMap;
            if (!shipment.isEmpty()) {
                builder.append(formatResult(currName, shipment)).append(", ");
            }
        }
        if (builder.length() > 2) {
            builder.deleteCharAt(builder.length() - 1);
            builder.deleteCharAt(builder.length() - 1);
        }
        return (itemMap.isEmpty()) ? builder.append("]").toString() : NO_ALLOCATION;
    }

    /**
     * @param items String input of items
     * @return HashMap where key is item name and value is item amount
     * <p>
     * Removes brackets, then splits string using ITEM_DIVISOR
     * For each string, extracts name, amount. If amount is smaller than 0, it is ignored. Else stored in result hashMap
     */
    private static HashMap<String, Integer> parseItems(String items) {
        HashMap<String, Integer> itemMap = new HashMap<>();
        items = items.replaceAll(ITEM_BRACKET_REGEX, "");
        String[] itemArr = items.split(ITEM_DIVISOR);
        for (String item : itemArr) {
            String[] temp = item.split(":");
            String name = temp[0].replaceAll(" ", "");
            int num = Integer.parseInt(temp[1].replaceAll(" ", ""));
            if (num > 0) {
                itemMap.put(name, num);
            }
        }
        return itemMap;
    }

    /**
     * @param warehouses String input of warehouses
     * @return List of warehouse object where next one is more expensive
     * <p>
     * Validates that input is not empty
     * Splits string into single warehouse using WAREHOUSE_DIVISOR
     * For each warehouse, hashMap of inventory is obtained and stored in list as Warehouse object
     */
    private static List<Warehouse> parseWarehouses(String warehouses) {
        List<Warehouse> warehouseList = new ArrayList<>();
        warehouses = warehouses.replaceAll("]", "");
        if (warehouses.isEmpty()) {
            return warehouseList;
        }
        warehouses = " " + warehouses;
        String[] warehouseArr = warehouses.split(WAREHOUSE_DIVISOR);
        for (String warehouse : warehouseArr) {
            String[] temp = warehouse.split("inventory:");
            String name = temp[0].replaceAll("\\{|name|:|\\p{Space}|,", "");
            String inventory = temp[1];
            warehouseList.add(new Warehouse(name, parseItems(inventory)));
        }
        return warehouseList;
    }

    /**
     * @param name    String input of warehouse name
     * @param hashMap String input of warehouse inventory
     * @return formatted String, human readable
     */
    private static String formatResult(String name, HashMap<String, Integer> hashMap) {
        StringBuilder builder = new StringBuilder();
        builder.append("{").append(" ").append(name).append(":").append(" ").append("{");
        for (String key : hashMap.keySet()) {
            builder.append(" ").append(key).append(":").append(" ").append(hashMap.get(key)).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(" ").append("}").append(" ").append("}");
        return builder.toString();
    }
}
