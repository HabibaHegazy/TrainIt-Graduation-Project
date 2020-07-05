package com.example.ipingpong.controllers;

import android.content.Context;

import com.example.ipingpong.controllers.CallBacks.SelectAllAddresses;
import com.example.ipingpong.controllers.CallBacks.SelectSingleAddress;
import com.example.ipingpong.models.AddressModel;
import com.example.ipingpong.shared.entities.Address;

import java.util.ArrayList;
import java.util.HashMap;

public class AddressController {

    AddressModel addressModel;

    public AddressController(Context context) {
        this.addressModel = new AddressModel(context);
    }

    public void getAllAddresses(int parentID, final SelectAllAddresses selectAllAddresses){

        addressModel.getAddresses(parentID, new SelectAllAddresses() {
            @Override
            public void onSuccess(ArrayList<Address> parentAddresses) {
                selectAllAddresses.onSuccess(parentAddresses);
            }

            @Override
            public void onFailure(String reason) {
                selectAllAddresses.onFailure(reason);
            }
        });

    }

    public void getSingleAddress(String addressID, final SelectSingleAddress selectSingleAddress) {

        addressModel.getSingleAddress(addressID, new SelectSingleAddress() {
            @Override
            public void onSuccess(Address address) {
                selectSingleAddress.onSuccess(address);
            }

            @Override
            public void onFailure(String reason) {
                selectSingleAddress.onFailure(reason);
            }
        });

    }

}
