package org.example.springbootjava17.mapper;


import org.example.springbootjava17.dto.UserAddress;
import org.example.springbootjava17.dto.UserRecord;
import org.example.springbootjava17.entity.Address;
import org.example.springbootjava17.entity.UserEntity;

import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toSet;

public class UserRecordToEntity implements Function<UserRecord, UserEntity> {

    @Override
    public UserEntity apply(UserRecord user) {
        var entity = new UserEntity(user.username(), user.email());
        entity.setAddresses(extractAddressEntities(user));
        entity.getAddresses().forEach(address -> address.setUser(entity));
        return entity;
    }

    public Set<Address> extractAddressEntities(UserRecord user) {
        return user.addresses().stream()
                .map(this::toAddressEntity)
                .collect(toSet());
    }

    public Address toAddressEntity(UserAddress addressData) {
        var address = new Address();
        address.setLine1(addressData.line1());
        address.setCity(addressData.city());
        address.setState(addressData.state());
        address.setZip(addressData.zip());
        return address;
    }
}
