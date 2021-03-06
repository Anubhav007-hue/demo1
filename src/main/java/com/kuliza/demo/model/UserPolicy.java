package com.kuliza.demo.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user_policy")
public class UserPolicy {

    @Id
    @Column(name = "policy_name")
    @NonNull
    private String policy_name;

    @Column(name = "user_name")
    private String user_name;

    public UserPolicy(Long user_policyId, String policy_name, String user_name) {
        this.policy_name = policy_name;
        this.user_name = user_name;
    }
    public UserPolicy() {

    }

    public String getPolicy_name() {
        return policy_name;
    }

    public void setPolicy_name(String policy_name) {
        this.policy_name = policy_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
