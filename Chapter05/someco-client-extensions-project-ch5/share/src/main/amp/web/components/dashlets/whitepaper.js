/**
 * Dashboard Whitepaper component.
 * 
 * @namespace Alfresco
 * @class Alfresco.dashlet.Whitepaper
 */
(function() {

	/**
	 * YUI Library aliases
	 */
	var Selector = YAHOO.util.Selector;

	/**
	 * Dashboard Whitepaper constructor.
	 * 
	 * @param {String}
	 *            htmlId The HTML id of the parent element
	 * @return {Alfresco.dashlet.Whitepaper} The new component instance
	 * @constructor
	 */
	Alfresco.dashlet.Whitepaper = function Whitepaper_constructor(htmlId) {
		return Alfresco.dashlet.Whitepaper.superclass.constructor.call(this, htmlId);
	};

	YAHOO.extend(Alfresco.dashlet.Whitepaper, Alfresco.component.SimpleDocList, {

		/**
		 * Preferences
		 */
		PREFERENCES_WHITEPAPER_DASHLET : "",
		PREFERENCES_WHITEPAPER_DASHLET_FILTER : "",

		/**
		 * Fired by YUI when parent element is available for scripting
		 * 
		 * @method onReady
		 */
		onReady : function Whitepaper_onReady() {
			this.PREFERENCES_WHITEPAPER_DASHLET = this.services.preferences.getDashletId(this, "whitepaper");
			this.PREFERENCES_WHITEPAPER_DASHLET_FILTER = this.PREFERENCES_WHITEPAPER_DASHLET + ".filter";

			// Create Dropdown filter
			this.widgets.filter = Alfresco.util.createYUIButton(this, "filters", this.onFilterChange, {
				type : "menu",
				menu : "filters-menu",
				lazyloadmenu : false
			});

			// Select the preferred filter in the ui
			var filter = this.options.filter;
			filter = filter != null ? filter : "active";
			this.widgets.filter.set("label", this.msg("filter." + filter) + " " + Alfresco.constants.MENU_ARROW_SYMBOL);
			this.widgets.filter.value = filter;
			this.options.simpleView = true;

			// Display the toolbar now that we have selected the filter
			Dom.removeClass(Selector.query(".toolbar div", this.id, true), "hidden");

			// DataTable can now be rendered
			Alfresco.dashlet.Whitepaper.superclass.onReady.apply(this, arguments);
			
			// Update some data to be compatible with Alfresco.component.SimpleDocList
			var me = this, 
				dataTable = this.widgets.alfrescoDataTable.getDataTable(), 
				original_onDataReturnSetRows = dataTable.onDataReturnSetRows;

			dataTable.onDataReturnSetRows = function Whitepaper_onDataReturnSetRows(sRequest, oResponse, oPayload) {
				if (oResponse.results && oResponse.results.length > 0) {
					for (var i = 0; i < oResponse.results.length; i++) {
						oResponse.results[i].fileName = oResponse.results[i].name;
						oResponse.results[i].location = {
							site: oResponse.results[i].site.shortName,
							siteTitle: oResponse.results[i].site.title
						};
						oResponse.results[i].permissions = {
							userAccess: {
								create: false
							}
						}
					}
				}

				return original_onDataReturnSetRows.apply(this, arguments);
			};
		},

		/**
		 * Calculate webscript parameters
		 * 
		 * @method getParameters
		 * @override
		 */
		getParameters : function Whitepaper_getParameters() {
			var param = "term=";
			var query = "+TYPE:sc\\:whitepaper";
			if (this.widgets.filter.value == "inactive")
				query += " +sc\\:isActive:false";
			else
				query += " +sc\\:isActive:true";
			param += encodeURIComponent(query);
			return param;
		},

		/**
		 * Generate base webscript url.
		 * 
		 * @method getWebscriptUrl
		 * @override
		 */
		getWebscriptUrl : function Whitepaper_getWebscriptUrl() {
			return Alfresco.constants.PROXY_URI + "slingshot/search";
		},

		/**
		 * Filter Change menu handler
		 * 
		 * @method onFilterChange
		 * @param p_sType
		 *            {string} The event
		 * @param p_aArgs
		 *            {array}
		 */
		onFilterChange : function Whitepaper_onFilterChange(p_sType, p_aArgs) {
			var menuItem = p_aArgs[1];
			if (menuItem) {
				this.widgets.filter.set("label", menuItem.cfg.getProperty("text") + " " + Alfresco.constants.MENU_ARROW_SYMBOL);
				this.widgets.filter.value = menuItem.value;

				this.services.preferences.set(this.PREFERENCES_WHITEPAPER_DASHLET_FILTER, this.widgets.filter.value);

				this.reloadDataTable();
			}
		}
	});
})();
