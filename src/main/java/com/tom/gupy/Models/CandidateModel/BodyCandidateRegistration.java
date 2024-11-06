package com.tom.gupy.Models.CandidateModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BodyCandidateRegistration {
    private String name;
    private String email;
    private Integer document_id;
    private String birthdate;
    private String gender;
    private String test_id;
    private Integer company_id;
    private Integer job_id;
    private String callback_url;
    private String candidate_type;
    private String previous_result;
    private String result_webhook_url;
    public BodyCandidateRegistration() {

    }

}
