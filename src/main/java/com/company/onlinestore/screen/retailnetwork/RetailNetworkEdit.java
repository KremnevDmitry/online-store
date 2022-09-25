package com.company.onlinestore.screen.retailnetwork;

import io.jmix.ui.screen.*;
import com.company.onlinestore.entity.RetailNetwork;

@UiController("RetailNetwork.edit")
@UiDescriptor("retail-network-edit.xml")
@EditedEntityContainer("retailNetworkDc")
public class RetailNetworkEdit extends StandardEditor<RetailNetwork> {
}