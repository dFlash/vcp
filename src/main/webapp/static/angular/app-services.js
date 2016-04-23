angular.module('app-services', ['ngResource'])
.service("videoService", ['$resource', function($resource) {
	return {
		listAll : function (pageNum){
			var url = '/videos/' + pageNum;
			return $resource(url).get();
		},
		getPagesCount: function()
		{
			return $resource('/pagesCount').get();
		},
		getVideo : function (videoId){
			var url = '/video/'+videoId;
			return $resource(url).get();
		}
	}
}]);