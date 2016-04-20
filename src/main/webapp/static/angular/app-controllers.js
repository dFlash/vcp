angular.module('app-controllers', ['ngRoute'])
.config(function($routeProvider){
    $routeProvider.when('/videos', {
        templateUrl: 'static/html/videos.html', 
        controller:'videoController' 
    });
    $routeProvider.when('/video/:videoId', {
        templateUrl: 'static/html/video.html', 
        controller:'currVideoController' 
    });
    $routeProvider.otherwise({redirectTo:'videos'});
})
.controller('videoController', ['$scope', 'videoService', function($scope, videoService){
	$scope.videosPage = videoService.listAll();
	}
])
.controller('currVideoController', ['$scope', '$routeParams', function($scope, $routeParams){
	$scope.products = productService.getProducts();
	$scope.currVideo = allVideos.content[$routeParams.videoId];
}
]);