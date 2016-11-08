angular.module('operationLogService', [])
    .factory('operationLog', function ($http) {
        return {
            listBuilding: function () {
                return $http({
                    method: 'GET',
                    url: 'http://120.26.54.131:8080/GLCPWCC/button/group?token=1477271288526'
                    //params: {filter: filter, offset: offset, sort: sort}
                });
            },

            getUserAll: function (token) {
                return $http({
                    method: 'GET',
                    url: userUrlAll + '?token=' + token
                });
            },

            queryOperationlog: function (token,data) {
                return $http({
                    method: 'POST',
                    url: queryOperationLog + '?token=' + token,
                    data: data
                })
            },

            queryErrorlog: function (token,data) {
                return $http({
                    method: 'POST',
                    url: queryErrorLog + '?token=' + token,
                    data: data
                })
            },

            queryDebuglog: function (token,data) {
                return $http({
                    method: 'POST',
                    url: queryDebugLog + '?token=' + token,
                    data: data
                })
            },

            clearLog: function (token) {
                return $http({
                    method: 'DELETE',
                    url: clearLog + '?token=' + token
                })
            },

            clearErrorLog: function (token) {
                return $http({
                    method: 'DELETE',
                    url: clearErrorLog + '?token=' + token
                })
            },

            clearDebugLog: function (token) {
                return $http({
                    method: 'DELETE',
                    url: clearDebugLog + '?token=' + token
                })
            },

            trueDebug: function (token) {
                return $http({
                    method: 'GET',
                    url: tureDebugLog + '?token=' + token
                });
            },

            falseDebug: function (token) {
                return $http({
                    method: 'GET',
                    url: falseDebugLog + '?token=' + token
                });
            },

            getDebug: function (token) {
                return $http({
                    method: 'GET',
                    url: getDebug + '?token=' + token
                });
            }
        }
    });