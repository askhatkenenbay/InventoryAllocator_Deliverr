# InventoryAllocator_Deliverr
Project was created using maven with JUnit dependency and java 8. 

Main located in src/main/java/InventoryAllocator.java

Test class located in src/test/java/InventoryAllocatorTest.java

Time complexity is O(n*m) and Space complexity is O(n+m) where n is number of order items and m is warehouses.

Brief solution: Input text divided into order and warehouse part. From order part obtained hashMap, where key is name and value is needed amount. From warehouse part, list of warehouses obtained, where next one is more expensive than one before. Finally starting from first one all warehouses are iterated and if needed object is found, added to final result string.

