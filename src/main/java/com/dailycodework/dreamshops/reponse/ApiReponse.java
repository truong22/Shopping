package com.dailycodework.dreamshops.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class ApiReponse {
    private String message;
    private Object date;
}
