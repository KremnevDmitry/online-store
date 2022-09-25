package com.company.onlinestore.screen.retailnetwork;

import io.jmix.ui.screen.*;
import com.company.onlinestore.entity.RetailNetwork;

@UiController("RetailNetwork.browse")
@UiDescriptor("retail-network-browse.xml")
@LookupComponent("retailNetworksTable")
public class RetailNetworkBrowse extends StandardLookup<RetailNetwork> {
}