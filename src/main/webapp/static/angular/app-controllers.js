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
        templateUrl: 'static/html/user-videos.html', 
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
    $routeProvider.when('/403', {
    	templateUrl: 'static/html/403.html'
    });
    $routeProvider.when('/admin/accounts', {
    	templateUrl: 'static/html/accounts.html',
        controller:'accountsController' 
    });
    $routeProvider.when('/edit-video/:videoId', {
    	templateUrl: 'static/html/edit-video.html',
        controller:'editVideoController' 
    });
    $routeProvider.when('/delete-video/:videoId', {
    	templateUrl: 'static/html/user-videos.html',
        controller:'deleteVideoController' 
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
	if ($location.search().page == null) {
		$scope.currentPage = 0;
	}
	else {
		$scope.currentPage = $location.search().page;
	}
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
}])
.controller('accountsController', ['$scope', 'adminService', '$location', function($scope, adminService, $location){
	if ($location.search().page == null) {
		$scope.currentPage = 0;
	}
	else {
		$scope.currentPage = $location.search().page;
	}
	$scope.path = $location.path() + "?";
	$scope.accountsPage = adminService.listAccounts($scope.currentPage);
	
	//
	$scope.isNewUser = true;
	$scope.currentUser = {};
	$scope.companies = adminService.listCompanies();
	$scope.currentUser.company = {};
	
	$scope.roles = ['User', 'Admin'];
	$scope.currentUser.role = $scope.roles[0];
	
	$scope.currentUser.name = '';
	$scope.currentUser.surname = '';
	$scope.currentUser.login = '';
	$scope.currentUser.email = '';
	$scope.currentUser.avatar = '';
	$scope.currentUser.password = '';
	
	$scope.editUser = function(id) {
		for (i=0; i < $scope.accountsPage.content.length; i++)
		{
			if ($scope.accountsPage.content[i].id == id)
			{
				$scope.currentUser = $scope.accountsPage.content[i];
				$scope.isNewUser = false;
				break;
			}
		}
	};
	$scope.reset = function() {
		//
		$scope.isNewUser = true;
		$scope.currentUser = {};
		$scope.companies = adminService.listCompanies();
		$scope.currentUser.company = {};
		
		$scope.roles = ['User', 'Admin'];
		$scope.currentUser.role = $scope.roles[0];
		
		$scope.currentUser.name = '';
		$scope.currentUser.surname = '';
		$scope.currentUser.login = '';
		$scope.currentUser.email = '';
		$scope.currentUser.avatar = '';
		$scope.currentUser.password = '';
	};
	
	$scope.uploadAvatar = function() {
		var uploadForm = new FormData();
		uploadForm.append('file', $scope.avatar);
		var service = adminService.uploadAvatar();
		service.upload({}, uploadForm, 
	    		function(response) {
					alert("Upload completed successfully");
					$scope.currentUser.avatar = response.avatarUrl;
				},
				function() {
					alert("Upload error");
				}
			);
	};
	
	$scope.addUser = function () {
		var service = adminService.addUser();
		service.add({}, $scope.currentUser, 
	    		function(response) {
			alert("User added successfully");
			$location.path('/admin/accounts');
		},
		function() {
			alert("User was not added");
		}
	);
	};
	
	$scope.saveUser = function() {
		var service = adminService.updateUser($scope.currentUser.id);
		service.update({}, $scope.currentUser, 
		function(response) {
			alert("User saved successfully");
			$location.path('/admin/accounts');
		},
		function() {
			alert("User was not saved");
		})
	};
	
	$scope.deleteUser = function(id){
		adminService.deleteUser(id, function() {
			alert("Deleted successfully");
			$location.path('/admin/accounts');
		},
		function() {
			alert("User was not deleted");
		})
	}
}])
.controller('editVideoController', ['$scope', 'videoService', '$routeParams', '$location',
    function($scope, videoService, $routeParams, $location){
		$scope.video = videoService.getVideo($routeParams.videoId);
		
		$scope.uploadThumbnail = function() {
			var uploadForm = new FormData();
			uploadForm.append('file', $scope.thumbnail);
			var service = videoService.uploadThumbnail();
			service.upload({}, uploadForm, 
		    		function(response) {
						alert("Upload completed successfully");
						$scope.video.thumbnail = response.thumbnailUrl;
					},
					function() {
						alert("Upload error");
					}
				)
		};
		
		$scope.updateVideo = function(){
			var service = videoService.updateVideo($scope.video.id);
			service.update({}, $scope.video, 
			function(response) {
				alert("Upload completed successfully");
				$location.path('/user-videos');
			},
			function() {
				alert("Upload error");
			})
		};
}
])
.controller('deleteVideoController', ['videoService', '$routeParams', '$location',
    function(videoService, $routeParams, $location){
	videoService.deleteVideo($routeParams.videoId, function(){
		alert('Deleted successfully');
		$location.path('/user-videos');
	},
	function() {
		alert("Error");
	})
}
]);