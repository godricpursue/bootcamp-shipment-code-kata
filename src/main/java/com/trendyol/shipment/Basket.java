package com.trendyol.shipment;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Basket {

    private List<Product> productList;
    private static final int SHIPMENT_SIZE_THRESHOLD = 3;

    public ShipmentSize getShipmentSize() {

        final var products = getProductList();

        if (products == null || products.isEmpty()) {
            return null;
        }

        Map<ShipmentSize, Integer> shipmentSizeCounts = products.stream()
                .collect(Collectors.toMap(
                        Product::getSize,
                        count -> 1,
                        Integer::sum
                ));

            ShipmentSize largestBasketSize = getLargestBasketSize(products);

        return hasSizeWithCountGreaterThanThreshold(shipmentSizeCounts) ?
                getNextSize(shipmentSizeCounts.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse(null)
                ) :
                largestBasketSize;

    }

    private ShipmentSize getLargestBasketSize(List<Product> products) {
        return products.stream()
                .map(Product::getSize)
                .max(ShipmentSize::compareTo)
                .orElse(null);
    }
    private boolean hasSizeWithCountGreaterThanThreshold(Map<ShipmentSize, Integer> shipmentSizeCounts) {
        return shipmentSizeCounts.values()
                .stream()
                .anyMatch(count -> count >= SHIPMENT_SIZE_THRESHOLD);
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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProducts(List<Product> productList) {
        this.productList = productList;
    }
}
