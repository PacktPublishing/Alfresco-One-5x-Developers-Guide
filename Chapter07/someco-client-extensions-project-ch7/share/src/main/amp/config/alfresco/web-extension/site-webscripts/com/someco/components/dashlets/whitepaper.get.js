<import resource="classpath:/alfresco/templates/org/alfresco/import/alfresco-util.js">

/* Get filters */
function getFilters()
{
   var myConfig = new XML(config.script),
      filters = [];

   for each (var xmlFilter in myConfig..filter)
   {
      if (xmlFilter.@evaluator.toString() === "" || runEvaluator(xmlFilter.@evaluator.toString()))
      {
         filters.push(
         {
            type: xmlFilter.@type.toString(),
            parameters: xmlFilter.@parameters.toString()
         });
      }
   }
   return filters
}

var regionId = args['region-id'];
model.preferences = AlfrescoUtil.getPreferences("org.alfresco.share.whitepaper.dashlet." + regionId);
model.filters = getFilters(); 

function main()
{
   // Widget instantiation metadata...
   var whitepaper = {
      id : "Whitepaper",
      name : "Alfresco.dashlet.Whitepaper",
      options : {
         filter : model.preferences.filter != null ? model.preferences.filter : "active",
         regionId : regionId
      }
   };
   
   var dashletResizer = {
       id: "DashletResizer",
	   name: "Alfresco.widget.DashletResizer",
	   initArgs: ["\"" + args.htmlid + "\"", "\"" + instance.object.id + "\""],
	   useMessages: false
   };

   model.widgets = [whitepaper, dashletResizer];
}

main();
