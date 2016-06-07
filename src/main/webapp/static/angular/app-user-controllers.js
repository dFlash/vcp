angular.module('app-user-controllers', ['ngRoute', 'ngFileUpload'])
.config(function($routeProvider){
    $routeProvider.when('/upload', {
        templateUrl: 'static/html/upload.html', 
        controller:'videoUploadController' 
    });
    $routeProvider.when('/user-videos', {
        templateUrl: 'static/html/user-videos.html', 
        controller:'userVideoListController' 
    });
    $routeProvider.when('/edit-video/:videoId', {
    	templateUrl: 'static/html/edit-video.html',
        controller:'editVideoController' 
    });
    $routeProvider.when('/delete-video/:videoId', {
    	templateUrl: 'static/html/user-videos.html',
        controller:'deleteVideoController' 
    });
})
.controller('videoUploadController', ['$scope', 'userService', '$location', function ($scope, userService, $location) {
	$scope.disabled = false;
    $scope.uploadVideo = function() {
    	$scope.disabled = true;
    	var uploadForm = new FormData();
    	uploadForm.append('title', $scope.title);
    	uploadForm.append('description', $scope.description);
    	uploadForm.append('file', $scope.videoFile);
    	var service = userService.uploadVideo(uploadForm, 
    		function(response) {
				alert("Upload completed successfully");
				var url = '/video/' + response.id;
				$location.path(url);
			},
			function() {
				alert("Upload error");
			});
     }
    }])
.controller('userVideoListController', ['$scope', 'userService', '$location', function($scope, userService, $location){
	if ($location.search().page == null) {
		$scope.currentPage = 0;
	}
	else {
		$scope.currentPage = $location.search().page;
	}
	$scope.dataPage = userService.userListAll($scope.currentPage);
	$scope.path = $location.path() + "?";
	
}])
.controller('editVideoController', ['$scope', 'videoService', '$routeParams', '$location', 'userService',
    function($scope, videoService, $routeParams, $location, userService){
		$scope.video = videoService.getVideo($routeParams.videoId);
		
		$scope.uploadThumbnail = function() {
			var uploadForm = new FormData();
			uploadForm.append('file', $scope.thumbnail);
			var service = userService.uploadThumbnail(uploadForm, 
		    	function(response) {
					alert("Upload completed successfully");
					$scope.video.thumbnail = response.content;
				},
				function() {
					alert("Upload error");
				});
		};
		
		$scope.updateVideo = function(){
			var service = userService.updateVideo($scope.video.id);
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
.controller('deleteVideoController', ['userService', '$routeParams', '$location',
    function(userService, $routeParams, $location){
	if (confirm('Are you sure you want to delete video')){
		userService.deleteVideo($routeParams.videoId, function(){
			alert('Deleted successfully');
			$location.path('/user-videos');
		},
		function() {
			alert("Video was not deleted");
		})
	} else {
		$location.path('/user-videos');
	}
}
]);