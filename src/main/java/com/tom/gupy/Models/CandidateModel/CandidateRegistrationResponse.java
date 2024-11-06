package com.tom.gupy.Models.CandidateModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CandidateRegistrationResponse {
    private String test_result_id;
    private String test_url;
}
