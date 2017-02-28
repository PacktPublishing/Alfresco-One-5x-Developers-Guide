function getRating(curNode, curUser) {
	var rating = {};
	rating.average = curNode.properties["{http://www.someco.com/model/content/1.0}averageRating"];
	rating.count = curNode.properties["{http://www.someco.com/model/content/1.0}ratingCount"];
	rating.user = getUserRating(curNode, curUser);
	return rating;
}

function getUserRating(curNode, curUser) {
	if (curUser == undefined || curUser == "") {
		logger.log("User name was not passed in");
		return 0;
	}

	var results = curNode.childrenByXPath("*//.[@sc:rater='" + curUser + "']");
	if (results == undefined || results.length == 0) {
		logger.log("No ratings found for this node for user: " + curUser);
		return 0;
	} else {
		var rating = results[results.length - 1].properties["{http://www.someco.com/model/content/1.0}rating"];
		if (rating == undefined) {
			return 0;
		} else {
			return rating;
		}
	}
}
