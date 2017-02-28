<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <title>Alfresco</title>
   <style>
		/* Root styles, footer and links */
				
		body, html
		{
			font: 13px/1.231 Open Sans, Arial, sans-serif;
		}
		
		h1
		{
		   font-size: 220%;
		   font-family: Open Sans Condensed, Arial, sans-serif;
		   color: #333;
		}
		
		.title
		{
		   margin: 1em 0 0;
		}
				
		.title > h1
		{
		   display: inline;
		   position: relative;
		   left: 7px;
		   top: -5px;
		}
		
		.index
		{
		   margin-left: 18em;
		   padding-top: 1em;
		}
		
		.index-list
		{
		   padding-top: 1em;
		   border-top: 1px solid #eee;
		}
		
		p
		{
		   margin: 5px;
		}
   </style>
</head>
<body>
	<div class="index">
		<div class="title">
			<h1>Alfresco Artifacts Intialisation</h1>
		</div>
		<div class="index-list">
			<#list messages as message>
				<p>${message}</p>
			</#list>
		</div>     
	</div>
</body>
</html>