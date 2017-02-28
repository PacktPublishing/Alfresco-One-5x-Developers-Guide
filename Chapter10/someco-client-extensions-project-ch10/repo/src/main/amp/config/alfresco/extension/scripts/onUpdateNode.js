var scriptFailed = false;
// Check the arguments
if (behaviour.args == null) {
	logger.log("The args have not been set.");
	scriptFailed = true;
} else {
	if (behaviour.args.length == 1) {
		var actedOnNode = behaviour.args[0];
		logger.log("You just updated:" + actedOnNode.name);
	} else {
		logger.log("The number of arguments is incorrect.");
		scriptFailed = true;
	}
}
