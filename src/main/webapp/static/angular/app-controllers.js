angular.module('app-controllers', ['ngRoute', 'ngFileUpload'])
.config(function($routeProvider){
    $routeProvider.when('/videos/:pageNum', {
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
    $routeProvider.otherwise({redirectTo:'videos/0'});
})
.controller('videoListController', ['$scope', 'videoService', '$routeParams', function($scope, videoService, $routeParams){
	$scope.currentPage = $routeParams.pageNum;
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
.controller('videoUploadController', ['$scope', '$http', function ($scope, $http) {
    $scope.uploadVideo = function() {
    	console.log($scope.title);
    	console.log($scope.description);
    	$scope.response = {};
    	var uploadForm = new FormData();
    	uploadForm.append('title', $scope.title);
    	uploadForm.append('description', $scope.description);
    	uploadForm.append('file', $scope.videoFile);
    	$http.post('/upload', uploadForm, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(response){
        	$scope.response = response;
        })
        .error(function(){
        });
        }

    }]);