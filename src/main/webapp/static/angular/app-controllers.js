angular.module('app-controllers', ['ngRoute'])
.config(function($routeProvider){
    $routeProvider.when('/videos/:pageNum', {
        templateUrl: 'static/html/videos.html', 
        controller:'videoListController' 
    });
    $routeProvider.when('/video/:videoId', {
        templateUrl: 'static/html/video.html', 
        controller:'videoController' 
    });
    $routeProvider.otherwise({redirectTo:'videos/0'});
})
.controller('videoListController', ['$scope', 'videoListService', '$routeParams', function($scope, videoListService, $routeParams){
	$scope.pagesCount = videoListService.getPagesCount();
	$scope.currentPage = $routeParams.pageNum;
	$scope.videosPage = videoListService.listAll($scope.currentPage);
	
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
]);