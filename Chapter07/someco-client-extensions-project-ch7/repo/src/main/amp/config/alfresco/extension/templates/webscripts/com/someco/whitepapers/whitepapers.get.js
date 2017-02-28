<import resource="classpath:alfresco/extension/scripts/rating.js">

var whitepapers = search.luceneSearch("+TYPE:\"sc:whitepaper\" +@sc\\:isActive:true");

if (whitepapers == null || whitepapers.length == 0) {
    logger.log("No whitepapers found");
    status.code = 404;
    status.message = "No whitepapers found";
    status.redirect = true;
 } else {
    var whitepaperInfo = new Array();
    for (i = 0; i < whitepapers.length; i++) {
       var whitepaper = new whitepaperEntry(whitepapers[i], getRating(whitepapers[i]));
       whitepaperInfo[i] = whitepaper;
    }
    model.whitepapers = whitepaperInfo;
 }

 function whitepaperEntry(whitepaper, rating) {
    this.whitepaper = whitepaper;
    this.rating = rating;
 }
