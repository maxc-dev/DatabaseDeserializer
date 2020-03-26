package requirements;

import java.util.ArrayList;
import java.util.List;

import io.DBUtils;
import io.Deserializer;
import models.OrderDetail;
import models.Product;

/**
 * @author Max Carter
 * @since 19/03/2020
 */
@Requirement(code = 'C', position = 3, desc = "List the names of products sold at less than 80% of the MSRP.")
public class ProductMSRP implements ExecutableRequirement<List<String>> {
    protected static final double PRODUCT_LIST_MSRP_THRESHOLD = 0.8;

    private Product[] products;
    private OrderDetail[] orderDetails;

    public ProductMSRP(Product[] products, OrderDetail[] orderDetails) {
        this.products = products;
        this.orderDetails = orderDetails;
    }

    @Override
    public List<String> execute(Deserializer deserializer, DBUtils dbUtils) {
        List<String> eligibleProducts = new ArrayList<>();

        //loop through all products and check if the price is 80% less than the MSRP
        for (Product product : products) {
            for (OrderDetail orderDetail : orderDetails) {
                if (orderDetail.productCode.equals(product.productCode) &&
                        orderDetail.priceEach < product.MSRP * PRODUCT_LIST_MSRP_THRESHOLD &&
                        !eligibleProducts.contains(product.productName)) {
                    eligibleProducts.add(product.productName);
                }
            }
        }
        return eligibleProducts;
    }
}
