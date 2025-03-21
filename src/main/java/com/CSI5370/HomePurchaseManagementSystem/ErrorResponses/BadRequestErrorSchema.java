package com.CSI5370.HomePurchaseManagementSystem.ErrorResponses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Schema(description = "Service Unavailable response details")
@Getter
@Setter
@AllArgsConstructor
public class BadRequestErrorSchema {

    @Schema(description = "Error type", example = "Bad Request")
    private String error;

    @Schema(description = "Detailed message", example = "One or more inputs invalid")
    private String message;

    @Schema(description = "HTTP status code", example = "400")
    private int status;

    @Schema(description = "Timestamp of the error", example = "2024-02-25T12:00:00")
    private LocalDateTime timestamp;
}
