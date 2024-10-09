package org.oril.exception.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;
import org.oril.exception.Error_er;

@Getter
@ToString
public class ErrorResponse implements Response {
    @ApiModelProperty(notes = "Contains information about error", required = true)
    private final Error_er error;

    public ErrorResponse(String code, String description, String message) {
        this.error = new Error_er(code, description, message);
    }

    @Override
    @ApiModelProperty(allowableValues = "FAIL", notes = "Status operation", required = true)
    public ResponseStatus getStatus() {
        return ResponseStatus.FAIL;
    }
}