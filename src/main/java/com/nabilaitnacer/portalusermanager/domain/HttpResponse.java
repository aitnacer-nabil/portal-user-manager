package com.nabilaitnacer.portalusermanager.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HttpResponse {
    private int httpStatusCode; // 200, 201, 400, 500
    private String httpStatus; // OK, Created, Bad Request, Internal Server Error
    private String message; // User has been created successfully, User has been updated successfully, User has been deleted successfully
    private String reason;
    private String error; // Bad Request, Internal Server Error
    private String path; // /user/create, /user/update, /user/delete

    public HttpResponse() {
        //default noArgConstructor
    }
}
