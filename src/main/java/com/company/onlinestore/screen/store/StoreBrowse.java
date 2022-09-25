package com.company.onlinestore.screen.store;

import io.jmix.ui.screen.*;
import com.company.onlinestore.entity.Store;

@UiController("Store.browse")
@UiDescriptor("store-browse.xml")
@LookupComponent("storesTable")
public class StoreBrowse extends StandardLookup<Store> {
}