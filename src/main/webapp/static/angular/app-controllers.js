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
    $routeProvider.when('/upload', {
        templateUrl: 'static/html/upload.html', 
        controller:'videoUploadController' 
    });
    $routeProvider.when('/user-videos', {
        templateUrl: 'static/html/videos.html', 
        controller:'userVideoListController' 
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
.controller('videoUploadController', ['$scope', 'videoService', '$location', function ($scope, videoService, $location) {
    $scope.uploadVideo = function() {
    	var uploadForm = new FormData();
    	uploadForm.append('title', $scope.title);
    	uploadForm.append('description', $scope.description);
    	uploadForm.append('file', $scope.videoFile);
    	var service = videoService.uploadVideo();
    	service.upload({}, uploadForm, 
    		function(response) {
				alert("Upload completed successfully");
				var url = '/video/' + response.id;
				$location.path(url);
			},
			function() {
				alert("Upload error");
			}
		)
     }

    }])
.controller('userVideoListController', ['$scope', 'videoService', '$location', function($scope, videoService, $location){
	$scope.currentPage = $location.search().page;
	$scope.videosPage = videoService.userListAll($scope.currentPage);
	$scope.path = $location.path() + "?";
	
}])
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
	            password : $scope.password
	          }
	  };
	  var success = function() {
		  $location.path("/user-videos");
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