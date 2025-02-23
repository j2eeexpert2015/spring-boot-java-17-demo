package org.example.springbootjava17.data;

import org.example.springbootjava17.dto.UserAddress;
import org.example.springbootjava17.dto.UserRecord;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TestData {

    public UserRecord getTestUserRecord() {
        return new UserRecord(
                "testuser",
                "test@example.com",
                Set.of(new UserAddress("123 Main St", "Anytown", "CA", "12345"))
        );
    }
}