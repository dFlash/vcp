angular.module('app-controllers', ['ngRoute', 'ngFileUpload'])
.config(['$routeProvider', function($routeProvider){
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
    $routeProvider.when('/serverError', {
    	templateUrl: 'static/html/500.html'
    });
    $routeProvider.when('/change-password', {
    	templateUrl: 'static/html/changePassword.html',
        controller:'changePasswordController'
    });
    $routeProvider.otherwise({redirectTo:'/videos'});
}])

.controller('videoListController', ['$scope', 'videoService', '$location', function($scope, videoService, $location){
	if ($location.search().page == null) {
		$scope.currentPage = 0;
	}
	else {
		$scope.currentPage = $location.search().page;
	}
	$scope.path = $location.path() + "?";
	$scope.dataPage = videoService.listAll($scope.currentPage);
	}
])
.controller('videoController', ['$scope', 'videoService', '$routeParams', '$route',
    function($scope, videoService, $routeParams, $route){
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
	$scope.dataPage = videoService.listBySearchQuery($scope.currentPage, query);
	$scope.path = $location.path() + "?" + "query=" + query + "&";
}])
.controller('loginController', ['$scope', 'loginService', '$location', '$rootScope', 'Roles',
                                function($scope, loginService, $location, $rootScope, Roles){
	$scope.login = function() {
	  var config = {
	          params: {
	            name: $scope.name,
	            password : $scope.password,
	            rememberMe: $scope.rememberMe
	          }
	  };
	  var success = function() {
		  if ($rootScope.principal.role==Roles.user) {
			  $location.path("/user-videos");
		  } else if ($rootScope.principal.role==Roles.admin) {
			  $location.path("/admin/accounts");
		  } else {
			  $location.path("/videos");
		  }
		  
	  }
	  loginService.login(config, success);
	};
	
	$scope.sendHash = function() {
		loginService.sendMail($scope.name,
				function() {
					alert('Check your email');
				}, function(){
					alert('Server error')
				});
	}
}])
.controller('logoutController', ['$scope', 'loginService', '$location', function($scope, loginService, $location){
	  var success = function() {
		  $location.path("/videos");
	  }
	  loginService.logout(success);
}])
.controller('menuController', ['$scope', 'Roles', function($scope, Roles){
	  $scope.admin = Roles.admin;
	  $scope.user = Roles.user;
}])
.controller("changePasswordController", ['$scope', 'changePasswordService', '$location', '$route',
                                         function($scope, changePasswordService, $location, $route){
	$scope.changePwd = function(){
		var success = function() {
		  alert("Password changed successfully");
		  $location.path("/videos");
	  };
	  
	  var error = function() {
		  alert("Password was not changed");
		  $route.reload();
	  };
	  
	  var postData = {
			  newPassword : $scope.newPassword,
	  		  repeatPassword : $scope.repeatPassword
	  };

	  changePasswordService.sendNewPassword(postData, success, error);
	}
}]);