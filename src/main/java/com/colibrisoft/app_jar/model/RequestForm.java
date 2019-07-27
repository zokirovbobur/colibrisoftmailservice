package com.colibrisoft.app_jar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestForm {
    private String userName, phone, serviceType;
}
