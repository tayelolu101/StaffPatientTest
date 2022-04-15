package com.example.hospitalcasestudy.dto.patient;

import com.example.hospitalcasestudy.util.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeletePatientReqDto {
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DEFAULT_DATETIME_FORMAT)
    @JsonProperty("last_visit_date_from")
    Timestamp lastVisitDateFrom;
    @NotNull
    @JsonProperty("last_visit_date_to")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DEFAULT_DATETIME_FORMAT)
    Timestamp lastVisitDateTo;
}
