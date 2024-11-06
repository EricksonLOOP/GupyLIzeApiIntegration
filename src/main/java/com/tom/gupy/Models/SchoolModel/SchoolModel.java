package com.tom.gupy.Models.SchoolModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SchoolModel {
    private String id;
    private String name;
    private String coordination;
    private String school_year;
    public SchoolModel(){

    }

}
