'use strict';

angular.module('app').config(function($stateProvider, $urlRouterProvider, $locationProvider) {

	$locationProvider.hashPrefix('');
	$urlRouterProvider.when("", "/");
	
	$stateProvider

	.state('/', {
		url : '/',
		redirectTo: 'home'
	})
	.state('logout', {
		url : '/logout',
		templateUrl: 'app/logout/logout.html'
	})
	.state('home', {
		url : '/home',
		templateUrl: 'app/home/home.html',
		controller: "HomeController",
		controllerAs: "ctrl",
		resolve: {
			load: function ($ocLazyLoad) {
                return $ocLazyLoad.load(["app/home/homeController.js"])
            }
        }
	})
	.state('sugestaoProgride', {
		url : '/sugestaoProgride',
		templateUrl: 'app/sugestaoProgride/listarSugestaoProgride.html',
		controller: "SugestaoProgrideController",
		controllerAs: "ctrl",
		resolve: {
			load: function ($ocLazyLoad) {
                return $ocLazyLoad.load("app/sugestaoProgride/sugestaoProgrideController.js")
            },
            sugestaoProgride: function() {	 
	    		return null;
	    	}
        }
	})
	.state('/sugestaoProgride/:id', {
		url : '/sugestaoProgride/:id',
		templateUrl : 'app/sugestaoProgride/editarSugestaoProgride.html',
		controller : 'SugestaoProgrideController',
		controllerAs: "ctrl",
		resolve: {
			load: function ($ocLazyLoad) {
				return $ocLazyLoad.load("app/sugestaoProgride/sugestaoProgrideController.js")
            },
            sugestaoProgride: ['SugestaoProgrideResource', '$stateParams', function(SugestaoProgrideResource, $stateParams) {	 
            	if($stateParams.id == 0){
	    			return null;
	    		}		    
	       		return SugestaoProgrideResource.get({id: $stateParams.id}).$promise;
	    	}]   
        }
	})
	.state('otherwise', {
		url : '*path',
		templateUrl: 'app/error/error-404.html'
	})
	
});