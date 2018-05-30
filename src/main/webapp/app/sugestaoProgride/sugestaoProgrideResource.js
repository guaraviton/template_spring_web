'use strict';

angular.module("app").factory('SugestaoProgrideResource', [ '$resource', SugestaoProgrideResource ]);

function SugestaoProgrideResource($resource) {
	var urlBase = 'api/sugestaoProgride/:id';
	var rest = $resource(urlBase, {
		'id' : ''
	}, {

	});
	return rest;
}