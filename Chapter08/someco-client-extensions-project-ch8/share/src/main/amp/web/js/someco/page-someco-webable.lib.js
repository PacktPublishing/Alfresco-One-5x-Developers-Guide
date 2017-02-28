var app = angular.module("someco-webable", []);
app.controller('SomecoWebableCtrl', SomecoWebableCtrl);

function SomecoWebableCtrl($scope) {

	$scope.documents = [];
	$scope.filter = {};

	$scope.init = function() {

		var url = Alfresco.constants.URL_CONTEXT + "proxy/alfresco-api/-default-/public/cmis/versions/1.1/browser";
		url += "?cmisaction=query";
		url += "&statement=" + encodeURIComponent("SELECT d.*, w.* FROM cmis:document AS d JOIN sc:webable AS w ON d.cmis:objectId = w.cmis:objectId WHERE CONTAINS(d, 'PATH:\"/app:company_home/st:sites/cm:" + Alfresco.constants.SITE + "//*\"')");

		Alfresco.util.Ajax.request({
			method : Alfresco.util.Ajax.POST,
			url : url,
			successCallback : {
				fn : function(response) {
					$scope.documents = response.json.results;
					$scope.$apply();
				},
				scope : this
			},
			failureMessage : "Error retrieving documents."
		});

		var filterMenu = [ {
			text : "Active and Inactive",
			value : "all",
			onclick : {
				fn : updateFilter
			}
		}, {
			text : "Active",
			value : "active",
			onclick : {
				fn : updateFilter
			}
		}, {
			text : "Inactive",
			value : "inactive",
			onclick : {
				fn : updateFilter
			}
		} ];

		$scope.statusFilter = new YAHOO.widget.Button({
			type : "menu",
			label : "Active and Inactive",
			name : "publishStatusSelect",
			menu : filterMenu,
			container : "filter-menu"
		});

	}

	function updateFilter(p_sType, p_aArgs, p_oItem) {

		var sText = p_oItem.cfg.getProperty("text");
		$scope.statusFilter.set("label", sText);

		if (p_oItem.value == "all")
			$scope.filter = {};
		else if (p_oItem.value == "active")
			$scope.filter = {
				properties : {
					"w.sc:isActive" : {
						value : true
					}
				}
			};
		else if (p_oItem.value == "inactive")
			$scope.filter = {
				properties : {
					"w.sc:isActive" : {
						value : false
					}
				}
			};

		$scope.$apply();
	}

};
