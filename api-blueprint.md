FORMAT: 1A
HOST: http://localhost:8080/api/v1

# Sample Blog API

A simple blog API with implementation using different 
programming languages.

## Status Codes and Errors

This API uses HTTP status codes to communicate with the API consumer.

+ `200 OK` - Response to a successful GET, PUT, PATCH or DELETE.
+ `201 Created` - Response to a POST that results in a creation.
+ `400 Bad Request` - Malformed request; form validation errors.
+ `401 Unauthorized` - When no or invalid authentication details are provided.
+ `403 Forbidden` - When authentication succeeded but authenticated user doesn't have access to the resource.
+ `404 Not Found` - When a non-existent resource is requested.
+ `405 Method Not Allowed` - Method not allowed.
+ `415 Unsupported Media Type` - Unsupported media type in request.