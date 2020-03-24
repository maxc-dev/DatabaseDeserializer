package requirements;

import java.util.ArrayList;
import java.util.List;

import io.DBUtils;
import io.Deserializer;
import models.Product;

/**
 * @author Max Carter
 * @since 19/03/2020
 */
@Requirement(code = 'C', position = 3, desc = "List the names of products sold at less than 80% of the MSRP.")
public class ProductMSRP implements ExecutableRequirement {
    protected static final double PRODUCT_LIST_MSRP_THRESHOLD = 0.8;

    private Product[] products;

    public ProductMSRP(Product[] products) {
        this.products = products;
    }

    @Override
    public Object execute(Deserializer deserializer, DBUtils dbUtils) {
        List<String> eligibleProducts = new ArrayList<>();

        //loop through all products and check if the price is 80% less than the MSRP
        for (Product product : products) {
            if (product.buyPrice < product.MSRP * PRODUCT_LIST_MSRP_THRESHOLD) {
                eligibleProducts.add(product.productName);
            }
        }

        return eligibleProducts;
    }
}
