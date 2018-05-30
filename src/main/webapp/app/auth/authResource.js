'use strict';

angular.module("app").factory('AuthResource', [ '$resource', AuthResource]);

function AuthResource($resource) {
	var urlBase = 'api/auth';
	var rest = $resource(urlBase, {
	}, {
		
	});
	return rest;
}