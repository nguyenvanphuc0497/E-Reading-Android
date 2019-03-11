package com.dtu.capstone2.ereadingandroid.network.request;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Create by Nguyen Van Phuc on 3/11/19
 */
@AllArgsConstructor
@Data
public class AccountLoginRequest {
    private String userName;
    private String passwork;
}
