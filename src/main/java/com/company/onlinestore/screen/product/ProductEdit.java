package com.company.onlinestore.screen.product;

import io.jmix.ui.screen.*;
import com.company.onlinestore.entity.Product;

@UiController("Product.edit")
@UiDescriptor("product-edit.xml")
@EditedEntityContainer("productDc")
public class ProductEdit extends StandardEditor<Product> {
}