angular.module('app-services', ['ngResource'])
.service("videoListService", ['$resource', function($resource) {
	return {
		listAll : function (){
			// https://docs.angularjs.org/api/ngResource/service/$resource
			// http://www.sitepoint.com/creating-crud-app-minutes-angulars-resource/
			return $resource('/videos').get();
		}
	}
}])
.service("videoService", ['$resource', function($resource) {
	return {
		getVideo : function (videoId){
			// https://docs.angularjs.org/api/ngResource/service/$resource
			// http://www.sitepoint.com/creating-crud-app-minutes-angulars-resource/
			var url = '/video?id='+videoId;
			return $resource(url).get();
		}
	}
}]);