<#-- JavaScript Dependencies -->
<@markup id="js">
   <@script type="text/javascript" src="${url.context}/res/components/dashlets/whitepaper.js" group="dashlets"/>
</@>

<#-- Stylesheet Dependencies -->
<@markup id="css">
    <@link rel="stylesheet" type="text/css" href="${url.context}/res/components/dashlets/whitepaper.css" group="dashlets"/>
</@>

<#-- Widget creation -->
<@markup id="widgets">
   <@createWidgets group="dashlets"/>
</@>

<@markup id="html">
   <@uniqueIdDiv>
      <#assign id = args.htmlid>
      <#assign prefFilter = preferences.filter!"active">
      <div class="dashlet whitepaper resizable yui-resize">
         <div class="title">${msg("someco.whitepaper.dashletName")}</div>
         <div class="toolbar flat-button">
            <div class="hidden">
               <span class="align-left yui-button yui-menu-button" id="${id}-filters">
                  <span class="first-child">
                     <button type="button" tabindex="0"></button>
                  </span>
               </span>
               <select id="${id}-filters-menu">
               <#list filters as filter>
                  <option value="${filter.type?html}">${msg("filter." + filter.type)}</option>
               </#list>
               </select>
               <div class="clear"></div>
            </div>
         </div>
         <div class="body scrollableList" <#if args.height??>style="height: ${args.height?html}px;"</#if>>
            <div id="${id}-documents"></div>
         </div>
      </div>
   </@>
</@>