angular.module('app-controllers', ['ngRoute', 'ngFileUpload'])
.config(function($routeProvider){
    $routeProvider.when('/videos', {
        templateUrl: 'static/html/videos.html', 
        controller:'videoListController' 
    });
    $routeProvider.when('/video/:videoId', {
        templateUrl: 'static/html/video.html', 
        controller:'videoController' 
    });
    $routeProvider.when('/videos/search', {
        templateUrl: 'static/html/videos.html', 
        controller:'searchResultController' 
    });
    $routeProvider.when('/login', {
        templateUrl: 'static/html/login.html', 
        controller:'loginController' 
    });
    $routeProvider.when('/logout', {
    	templateUrl: 'static/html/login.html',
        controller:'logoutController' 
    });
    $routeProvider.when('/403', {
    	templateUrl: 'static/html/403.html'
    });
    $routeProvider.otherwise({redirectTo:'/videos'});
})
.factory('authHttpResponseInterceptor',['$q', '$location', function($q, $location){
    return {
        responseError: function(rejection) {
            if (rejection.status === 401) {
                alert("Unauthorized");
                $location.path('/login');
            }
            if (rejection.status === 400) {
                alert("Wrong login or password");
                $location.path('/login');
            }
            if (rejection.status === 403) {
                $location.path('/403');
            }
            return $q.reject(rejection);
        }
    }
}])
.config(['$httpProvider',function($httpProvider) {
    $httpProvider.interceptors.push('authHttpResponseInterceptor');
}])
.controller('videoListController', ['$scope', 'videoService', '$location', function($scope, videoService, $location){
	if ($location.search().page == null) {
		$scope.currentPage = 0;
	}
	else {
		$scope.currentPage = $location.search().page;
	}
	$scope.path = $location.path() + "?";
	$scope.videosPage = videoService.listAll($scope.currentPage);
	}
])
.controller('videoController', ['$scope', 'videoService', '$routeParams',
    function($scope, videoService, $routeParams){
		$scope.video = videoService.getVideo($routeParams.videoId);
	}
])
.controller('searchController', ['$scope', '$location', function($scope, $location){
	$scope.query = '';
	$scope.find = function (){
		if($scope.query.trim() != '') {
			$location.path('/videos/search').search({query: $scope.query, page:0});
			$scope.query = '';
		} else{
			$location.path('/videos')
		}
	};
	$scope.onEnter = function(keyEvent) {
		if (keyEvent.which === 13) {
			$scope.find();
		}
	}
}])
.controller('searchResultController', ['$scope', '$location', 'videoService', function($scope, $location, videoService){
	$scope.currentPage = $location.search().page;
	var query = $location.search().query;
	$scope.videosPage = videoService.listBySearchQuery($scope.currentPage, query);
	$scope.path = $location.path() + "?" + "query=" + query + "&";
}])
.controller('loginController', ['$scope', 'loginService', '$location', function($scope, loginService, $location){
	$scope.login = function() {
	  var config = {
	          params: {
	            name: $scope.name,
	            password : $scope.password,
	            rememberMe: $scope.rememberMe
	          }
	  };
	  var success = function() {
		  $location.path("/videos");
	  }
	  loginService.login(config, success);
	}
}])
.controller('logoutController', ['$scope', 'loginService', '$location', function($scope, loginService, $location){
	  var success = function() {
		  $location.path("/videos");
	  }
	  loginService.logout(success);
}]);