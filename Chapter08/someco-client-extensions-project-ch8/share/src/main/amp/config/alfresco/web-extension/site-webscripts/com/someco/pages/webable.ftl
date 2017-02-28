<#include "/org/alfresco/include/alfresco-template.ftl" />
<@templateHeader>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular.min.js"></script>
	<script type="text/javascript" src="${url.context}/res/js/someco/page-someco-webable.lib.js"></script>
	<@link rel="stylesheet" type="text/css" href="${url.context}/res/components/someco-webable/someco-webable.css"/>
</@>
<@templateBody>
   <@markup id="alf-hd">
   <div id="alf-hd">
      <@region scope="global" id="share-header" chromeless="true"/>
   </div>
   </@>
   <@markup id="bd">
    <div id="bd">
        <div ng-app="someco-webable" ng-init="init()" ng-controller="SomecoWebableCtrl">
        
        	<br />
			<div class="set">
	         	<div class="set-bordered-panel">
	            	<div class="set-bordered-panel-heading">Filter(s)</div>
	            	<div class="set-bordered-panel-body">
	            		<div id="filter-menu"></div>
						<br />	 
	            	</div>
	         	</div>
   			</div>

			<table>
				<tr ng-repeat="doc in documents | filter:filter" class="document">
					<td>
						<span class="thumbnail">
							<a ng-href="${url.context}/page/site/${(page.url.templateArgs.site!"")?url?js_string}/document-details?nodeRef=workspace://SpacesStore/{{ doc.properties['d.cmis:objectId'].value.split(';')[0] }}">
								<img id="workspace://SpacesStore/{{ doc.properties['d.cmis:objectId'].value.split(';')[0] }}" ng-src="${url.context}/proxy/alfresco/api/node/workspace/SpacesStore/{{ doc.properties['d.cmis:objectId'].value.split(';')[0] }}/content/thumbnails/doclib?c=queue&amp;ph=true" title="{{doc.properties['d.cmis:name'].value}}">
							</a>
						</span>
					</td>
					<td>
						<h3 class="filename">
							<a ng-href="${url.context}/page/site/${(page.url.templateArgs.site!"")?url?js_string}/document-details?nodeRef=workspace://SpacesStore/{{ doc.properties['d.cmis:objectId'].value.split(';')[0] }}">
								{{ doc.properties['d.cmis:name'].value }}
							</a>
							<span class="document-version">{{ doc.properties['d.cmis:versionLabel'].value }}</span>
						</h3>
						<div class="detail">
							Last Modified on {{ doc.properties['d.cmis:lastModificationDate'].value | date:'medium' }} By {{ doc.properties['d.cmis:lastModifiedBy'].value }}
						</div>
						<div class="detail">
							<span ng-if="doc.properties['w.sc:published'].value">
								Published on {{ doc.properties['w.sc:published'].value | date:'medium' }}
							</span>
							<span ng-if="!doc.properties['w.sc:published'].value">
								Not Published
							</span>
						</div>
					</td>
				</tr>
			</table>

		</div>
    </div>
   </@>
</@>
<@templateFooter>
   <@markup id="alf-ft">
   <div id="alf-ft">
      <@region id="footer" scope="global" />
   </div>
   </@>
</@>