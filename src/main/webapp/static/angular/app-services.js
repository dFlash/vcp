angular.module('app-services', ['ngResource'])
.service("videoListService", ['$resource', function($resource) {
	return {
		listAll : function (pageNum){
			var url = '/videos/' + pageNum;
			return $resource(url).get();
		},
		getPagesCount: function()
		{
			return $resource('/pagesCount').get();
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