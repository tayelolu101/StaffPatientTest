package com.example.hospitalcasestudy.dto.staff;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UpdateStaffReqDto {
    @NotNull
    @Size(min = 2, max = 50)
    String name;

    @NotNull
    Long id;

}
