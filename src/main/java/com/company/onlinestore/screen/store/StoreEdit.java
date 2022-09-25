package com.company.onlinestore.screen.store;

import io.jmix.ui.screen.*;
import com.company.onlinestore.entity.Store;

@UiController("Store.edit")
@UiDescriptor("store-edit.xml")
@EditedEntityContainer("storeDc")
public class StoreEdit extends StandardEditor<Store> {
}