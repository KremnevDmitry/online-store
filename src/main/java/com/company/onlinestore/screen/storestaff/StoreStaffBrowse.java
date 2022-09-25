package com.company.onlinestore.screen.storestaff;

import io.jmix.ui.screen.*;
import com.company.onlinestore.entity.StoreStaff;

@UiController("StoreStaff.browse")
@UiDescriptor("store-staff-browse.xml")
@LookupComponent("storeStaffsTable")
public class StoreStaffBrowse extends StandardLookup<StoreStaff> {
}