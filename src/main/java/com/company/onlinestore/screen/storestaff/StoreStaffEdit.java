package com.company.onlinestore.screen.storestaff;

import io.jmix.ui.screen.*;
import com.company.onlinestore.entity.StoreStaff;

@UiController("StoreStaff.edit")
@UiDescriptor("store-staff-edit.xml")
@EditedEntityContainer("storeStaffDc")
public class StoreStaffEdit extends StandardEditor<StoreStaff> {
}