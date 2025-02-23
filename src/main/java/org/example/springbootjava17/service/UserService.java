package org.example.springbootjava17.service;


import org.example.springbootjava17.dto.UserAddress;
import org.example.springbootjava17.dto.UserRecord;
import org.example.springbootjava17.dto.UserSummary;
import org.example.springbootjava17.entity.UserEntity;
import org.example.springbootjava17.mapper.UserEntityToRecord;
import org.example.springbootjava17.mapper.UserRecordToEntity;
import org.example.springbootjava17.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

@Service
public class UserService {

    private UserEntityToRecord toUserRecord = new UserEntityToRecord();
    private UserRecordToEntity toUserEntity = new UserRecordToEntity();

    private UserRepository userRepo;

    public UserService(UserRepository repo) {
        this.userRepo = repo;
    }

    public boolean validateAddressApiCall(UserAddress address) throws SocketTimeoutException, ConnectException {
        // this would be a call to a third party service like USPS address validation API
        return true;
    }

    @Transactional
    public UserRecord createUser(UserRecord user) {
        var entity = toUserEntity.apply(user);
        var saved = userRepo.save(entity);
        return toUserRecord.apply(saved);
    }

    @Transactional(readOnly = true)
    public Page<UserSummary> getUserSummaries(Pageable page) {
        return userRepo.loadSummaries(page);
    }
}
