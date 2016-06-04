angular.module('app-interceptors', [])
.factory('authHttpResponseInterceptor',['$q', '$location', '$rootScope',
                                        function($q, $location, $rootScope){
    return {
        responseError: function(rejection) {
            if (rejection.status === 401) {
                $location.path('/login');
            }
            if (rejection.status === 400) {
                alert("Wrong login or password");
                $location.path('/login');
            }
            if (rejection.status === 403) {
                $location.path('/403');
            }
            if (rejection.status === 500) {
            	if (rejection.data.hasOwnProperty("addError") && rejection.data.addError == true){
            		alert(rejection.data.message);
            	}
            	else{
            		$location.path('/serverError');
            	}
            }
            return $q.reject(rejection);
        },
        response: function(response) {
        	var production = response.headers('production');
        	$rootScope.production = production;
        	var name = response.headers('PrincipalName');
        	var role = response.headers('PrincipalRole');
        	if(name != undefined && role != undefined) {
        		$rootScope.principal = {
                	auth : true,
        			name : name,
                	role : role
                };
        	} else{
        		$rootScope.principal = {
        			auth : false,
        			name : '',
                	role : 'anonym'
                };
        	}
            return response;
        }
    }
}])
.config(['$httpProvider',function($httpProvider) {
    $httpProvider.interceptors.push('authHttpResponseInterceptor');
}]);