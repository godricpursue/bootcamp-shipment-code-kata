package com.trendyol.shipment;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basket {

    private List<Product> products;

    public ShipmentSize getShipmentSize() {
        if (products == null || products.isEmpty()) {
            return null;
        }

        Map<ShipmentSize, Integer> shipmentSizeCounts = new HashMap<>();
        ShipmentSize largestSize = null;

        for (Product product : products) {
            ShipmentSize size = product.getSize();
            shipmentSizeCounts.put(size, shipmentSizeCounts.getOrDefault(size, 0) + 1);

            if (largestSize == null || size.compareTo(largestSize) > 0) {
                largestSize = size;
            }
        }

        if (hasSizeWithCountGreaterThanEqualThree(shipmentSizeCounts)) {
            Map.Entry<ShipmentSize, Integer> maxEntry = Collections.max(
                    shipmentSizeCounts.entrySet(), Map.Entry.comparingByValue());
            ShipmentSize maxCountSize = maxEntry.getKey();
            return getNextSize(maxCountSize);
        } else {
            return largestSize;
        }
    }

    private boolean hasSizeWithCountGreaterThanEqualThree(Map<ShipmentSize, Integer> shipmentSizeCounts) {
        for (Integer count : shipmentSizeCounts.values()) {
            if (count >= 3) {
                return true;
            }
        }
        return false;
    }

    private ShipmentSize getNextSize(ShipmentSize size) {
        if (size == ShipmentSize.SMALL) {
            return ShipmentSize.MEDIUM;
        } else if (size == ShipmentSize.MEDIUM) {
            return ShipmentSize.LARGE;
        } else {
            return ShipmentSize.X_LARGE;
        }
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
