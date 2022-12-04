package com.company.onlinestore.screen.product;

import com.company.onlinestore.entity.Store;
import io.jmix.core.DataManager;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import com.company.onlinestore.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@UiController("Product.browse")
@UiDescriptor("product-browse.xml")
@LookupComponent("productsTable")
public class ProductBrowse extends StandardLookup<Product> {

    protected Store store;
    protected List<Product> excludedProducts;
    @Autowired
    private CollectionContainer<Product> productsDc;
    @Autowired
    private DataManager dataManager;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        List<Product> products = dataManager.load(Product.class)
                .all()
                .list()
                .stream()
                .filter(product -> product.getStore().equals(store))
                .filter(product -> !excludedProducts.contains(product))
                .collect(Collectors.toList());

        productsDc.setItems(products);
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setExcludedProducts(List<Product> excludedProducts) {
        this.excludedProducts = excludedProducts;
    }
}