var module = angular.module("geeky-phonebook", []);

function loadPhonebook(model, http) {
	http.get("GetAll").then(
		function (result) {
		    model.list = result.data;
		    model.showForm = false;
		    model.formData = {};
		},
		function (err) {
			alert("Error while loading phonebook: " + err.status + "/" + err.statusText);
		}
	);
}

function save(model, data, http) {
	http.post("Save", data).then(
		function (result) {
			loadPhonebook(model, http);
		},
		function (err) {
			alert("Error while saving phonebook record: " + err.status + "/" + err.statusText);
		}
	);	
}

function del(model, id, http) {
	http.delete("Delete?id=" + id).then(
			function (result) {
				loadPhonebook(model, http);
			},
			function (err) {
				alert("Error while deleting phonebook record: " + err.status + "/" + err.statusText);
			}
		);	
}

module.controller("main-controller", function ($scope, $http) {
	$scope.model = {};
	$scope.model.list = [];
	$scope.model.formData = {};
	loadPhonebook($scope.model, $http);
	
	$scope.newEntry = function () {
		$scope.model.showForm = true;
		$scope.model.formData = {};
	};
	$scope.save = function () {
		var data = $scope.model.formData;
		save($scope.model, data, $http);
	};
	$scope.update = function (record) {
		var str = JSON.stringify(record);
		var copy = JSON.parse(str);
		$scope.model.formData = copy;
		$scope.model.showForm = true;
	};
	$scope.delete = function (record) {
		del($scope.model, record.id, $http);
	};
});
