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
        ShipmentSize largestBasketSize = null;

        for (Product product : products) {
            ShipmentSize productSize = product.getSize();
            shipmentSizeCounts.put(productSize, shipmentSizeCounts.getOrDefault(productSize, 0) + 1);

            if (largestBasketSize == null || productSize.compareTo(largestBasketSize) > 0) {
                largestBasketSize = productSize;
            }
        }

        if (hasSizeWithCountGreaterThanEqualThree(shipmentSizeCounts)) {
            Map.Entry<ShipmentSize, Integer> maxEntry = Collections.max(
                    shipmentSizeCounts.entrySet(), Map.Entry.comparingByValue());
            ShipmentSize maxCountSize = maxEntry.getKey();
            return getNextSize(maxCountSize);
        } else {
            return largestBasketSize;
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

    private ShipmentSize getNextSize(ShipmentSize productSize) {
        if (productSize == ShipmentSize.SMALL) {
            return ShipmentSize.MEDIUM;
        } else if (productSize == ShipmentSize.MEDIUM) {
            return ShipmentSize.LARGE;
        } else {
            return ShipmentSize.X_LARGE;
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
