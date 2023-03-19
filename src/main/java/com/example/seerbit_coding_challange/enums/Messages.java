package com.example.seerbit_coding_challange.enums;

public enum Messages {

    MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields."),
    SUCCESS("Operation was successful."),
    INTERNAL_SERVER_ERROR("An error occurred on the server."),
    MALFORMED_JSON_REQUEST("Malformed JSON request."),
    NOT_EQUAL("Record is not equal."),
    VALIDATION_ERRORS("Validation errors."),
    ERROR_WRITING_JSON_RESPONSE("Error writing JSON output."),
    MEDIA_TYPE_NOT_SUPPORTED("Media type is not supported. Supported media types are: "),
    METHOD_NOT_SUPPORTED("Method not supported. Supported methods are: ");

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}