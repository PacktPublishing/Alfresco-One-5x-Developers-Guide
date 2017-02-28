var contentType = "whitepaper";
var contentName = "wp-";
var timestamp = new Date().getTime();
var extension = document.name.substr(document.name.lastIndexOf('.') + 1);

document.specializeType("sc:" + contentType);
document.addAspect("sc:webable");

document.properties["cm:name"] = contentName + " (" + timestamp + ")." + extension;
document.properties["sc:isActive"] = true;
document.properties["sc:published"] = new Date("07/31/2016");
document.save();
