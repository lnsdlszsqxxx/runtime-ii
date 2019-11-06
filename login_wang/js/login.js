/**
 * Copyright 2019, Liwei Wang <daveywang@live.com>.
 * All rights reserved. 
 * Author: Liwei Wang
 * Date: 02/2019
 *
 */
 
$("#login_form").submit(function(event) {
	event.preventDefault(); 						
	var url = $(this).attr("action"); 			
	var requestMethod = $(this).attr("method"); 	
	var formData = $(this).serialize(); 			

	$.ajax({
		url: url,
		type: requestMethod,
		headers: {
			'Authorization':'Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNTczMDU3MTU0LCJpc3MiOiJjb20uYXNjZW5kaW5nIiwiZXhwIjoxNTczMTQzNTU0LCJhbGxvd2VkUmVhZFJlc291cmNlcyI6Ii8sL2RlcHRzLC9kZXBhcnRtZW50cywvZW1wbG95ZWVzLC9lbXMsL2FjbnRzLC9hY2NvdW50cywvZW1wbG95ZWVzLC9lbXMsL2FjbnRzLC9hY2NvdW50cyIsImFsbG93ZWRDcmVhdGVSZXNvdXJjZXMiOiIvLC9kZXB0cywvZGVwYXJ0bWVudHMsL2VtcGxveWVlcywvZW1zLC9hY250cywvYWNjb3VudHMiLCJhbGxvd2VkVXBkYXRlUmVzb3VyY2VzIjoiLywvZGVwdHMsL2RlcGFydG1lbnRzLC9lbXBsb3llZXMsL2VtcywvYWNudHMsL2FjY291bnRzIiwiYWxsb3dlZERlbGV0ZVJlc291cmNlcyI6Ii8ifQ.kHDlbRtX68XN_KEGNFralrVATtKcLpUdgPIP92ccHAE'
		},
		data: formData,
		dataType: 'text',
		contentType: "application/json",
		success: function(response) {
			//alert('GOOD: ' + JSON.stringify(response));
			$("#msg_container").html(JSON.stringify(response)).show();
			//window.location = '/homePage';
		},
		error: function(response) {
			//alert('BAD: ' + JSON.stringify(response));
			$("#msg_container").html(JSON.stringify(response)).show();
		}
	});
});