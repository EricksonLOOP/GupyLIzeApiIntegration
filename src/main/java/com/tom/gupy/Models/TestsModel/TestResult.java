package com.tom.gupy.Models.TestsModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class TestResult {
    private String title;
    private String testCode;
    private String description;
    private String providerName;
    private String company_result_string;
    private String providerLink;
    private String status;
    private String result_candidate_page_url;
    private List<TestResultItem> results;
    public TestResult(){

    }
}
