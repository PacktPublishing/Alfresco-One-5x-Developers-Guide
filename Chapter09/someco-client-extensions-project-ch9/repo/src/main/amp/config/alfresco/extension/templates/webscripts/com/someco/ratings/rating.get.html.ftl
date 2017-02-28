<html>
	<head>
		<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/rateYo/2.2.0/jquery.rateyo.min.css">
		<script src="//code.jquery.com/jquery-1.12.4.min.js"></script>
		<script src="//cdnjs.cloudflare.com/ajax/libs/rateYo/2.2.0/jquery.rateyo.min.js"></script>
	</head>
	<body>
		<script type="text/javascript">			
			function deleteRatings(id) {
			  $.ajax({
               	url: "${url.serviceContext}/someco/rating?id=" + id,
               	method: "delete"
              }).success(function() {
                    location.reload();
              });
			}
			
			$(function () {
			  $("#rating").rateYo({
			    rating: ${rating.average},
			    fullStar: true
			  }).on("rateyo.set", function (e, data) {
                var rating = data.rating;
                var user = $("input[name='userId]").val();
                if (user == null || user.length() == 0)
                  user = "ben";
                var id = "${args.id}";
                $.post({
                	url: "${url.serviceContext}/someco/rating",
                	data: {
                		rating: rating,
                		user: user,
                		id: id
                	}
                }).success(function() {
                    location.reload();
                });
              });
			});
		</script>

		<p><a href="${url.serviceContext}/someco/whitepapers.html?guest=true">Back to the list</a> of whitepapers</p>
		<p>Node: ${args.id}</p>
		<p>Average: ${rating.average}</p>
		<p># of Ratings: ${rating.count}</p>
    	<form name="login">
			Rater: <input name="userId"></input>
    	</form>
		<#if (rating.user > 0)>
			<p>User rating: ${rating.user}</p>
		</#if>
		<p>Rating:</p>
		<div id="rating"></div>
		<p><a href="#" onclick=deleteRatings("${args.id}")>Delete ratings</a> for this node</p>         
	</body>
</html>