angular.module('app-services', ['ngResource'])
.service("videoListService", ['$resource', function($resource) {
	return {
		listAll : function (){
			return $resource('/videos').get();
		}
	}
}])
.service("videoService", ['$resource', function($resource) {
	return {
		getVideo : function (videoId){
			var url = '/video?id='+videoId;
			return $resource(url).get();
		}
	}
}]);