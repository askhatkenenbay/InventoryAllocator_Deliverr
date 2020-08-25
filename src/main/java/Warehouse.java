import java.util.HashMap;

public class Warehouse {
    private String name;
    private HashMap<String, Integer> inventory;

    public Warehouse(String name, HashMap<String, Integer> inventory) {
        this.name = name;
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }


    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "name='" + name + '\'' +
                ", inventory=" + inventory +
                '}';
    }
}
