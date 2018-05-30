'use strict';

angular.module('app').controller('MainController', [ '$rootScope', 'ProgressConfig', 'AuthService', 'AuthResource', MainController ]);

function MainController($rootScope, ProgressConfig, AuthService, AuthResource) {
	var ctrl = this;
	
	ProgressConfig.eventListeners();
	ProgressConfig.color('#398DD5');
	ProgressConfig.height('6px');
	
	AuthResource.get(function(data) {
		$rootScope.auth = data;
		ctrl.appLoaded = true;	
	});
	
	ctrl.logout = function() {
		AuthService.logout();
	}
}