# Fix the pre-flight issue

## The issue

When I send a request from frontend nodeJS server with method GET to the backend server, the request method becomes OPTIONS and returns no result. 

## What is pre-flight

Before sending the user's request, Browser sends a pre-request with method OPTIONS to test whether the user is allowed to visit the server. If it is allowed, browser will send the user's real request. In our issue above, it seems that after sending the pre-request with method OPTIONS, the browser stops sending the real request.
 
## The fix

Add ```((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers","authorization, Content-Type");``` in the CORSfilter file.

## Reference

[https://stackoverflow.com/questions/12320467/jquery-cors-content-type-options](https://stackoverflow.com/questions/12320467/jquery-cors-content-type-options)
[https://stackoverflow.com/questions/32500073/request-header-field-access-control-allow-headers-is-not-allowed-by-itself-in-pr](https://stackoverflow.com/questions/32500073/request-header-field-access-control-allow-headers-is-not-allowed-by-itself-in-pr)
