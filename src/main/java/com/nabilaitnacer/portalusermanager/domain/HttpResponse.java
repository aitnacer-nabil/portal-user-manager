package com.nabilaitnacer.portalusermanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class HttpResponse {
    private int httpStatusCode; // 200, 201, 400, 500
    private String httpMessage; // OK, Created, Bad Request, Internal Server Error
    private String message; // User has been created successfully, User has been updated successfully, User has been deleted successfully
    private String error; // Bad Request, Internal Server Error
    private String path; // /user/create, /user/update, /user/delete

    public HttpResponse() {
    }
}
