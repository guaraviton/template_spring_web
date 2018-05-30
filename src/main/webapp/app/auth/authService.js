'use strict';

angular.module("app").factory('AuthService', ['$rootScope', '$location', AuthService]);

function AuthService($rootScope, $location) {
	var service = {
		logout : function() {
			service.resetAndRedirect();	
		},
		reset : function() {
			$rootScope.auth = null;
		},
		resetAndRedirect : function() {
			service.reset();
			$location.path('/logout');			
		}
	}
	return service;
}