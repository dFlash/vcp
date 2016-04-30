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
    $routeProvider.otherwise({redirectTo:'/videos'});
})
.controller('videoListController', ['$scope', 'videoService', '$location', function($scope, videoService, $location){
	if ($location.search().page == null) {
		$scope.currentPage = 0;
	}
	else {
		$scope.currentPage = $location.search().page;
	}
	$scope.path = $location.path();
	$scope.videosPage = videoService.listAll($scope.currentPage);
	
	$scope.range = function(min, max, step) {
		step = step || 1;
		var input = [];
		for (var i = min; i <= max; i += step) {
			input.push(i);
		}
	 	return input;
	};
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
	$scope.path = $location.path();
	
	$scope.range = function(min, max, step) {
		step = step || 1;
		var input = [];
		for (var i = min; i <= max; i += step) {
			input.push(i);
		}
	 	return input;
	};
	}
]);