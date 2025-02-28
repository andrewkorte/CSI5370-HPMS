package com.CSI5370.HomePurchaseManagementSystem.ErrorResponses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "Error response details")
@Getter
@Setter
@AllArgsConstructor
public class Schema404 {

    @Schema(description = "Error type", example = "Not Found")
    private String error;

    @Schema(description = "Detailed message", example = "Resource not found")
    private String message;

    @Schema(description = "HTTP status code", example = "404")
    private int status;

    @Schema(description = "Timestamp of the error", example = "2024-02-25T12:00:00")
    private LocalDateTime timestamp;

}
