package cz.fi.muni.xkremser.editor.client.tree;


public class ShowcaseData {

	private final String idSuffix;

	public ShowcaseData(String idSuffix) {
		this.idSuffix = idSuffix;
	}

	private ExplorerTreeNode[] data;

	private ExplorerTreeNode[] getData() {
		if (data == null) {
			data = new ExplorerTreeNode[] {
					new ExplorerTreeNode("Featured Samples", "featured-category", "root", "silk/house.png", true, idSuffix),
					new ExplorerTreeNode("Demo Application", "featured-complete-app", "featured-category", "silk/layout_content.png", true, idSuffix),
					new ExplorerTreeNode("Smart GWT MVC", "featured-smartgwt-mvc", "featured-category", "silk/arrow_join.png", true, idSuffix),
					new ExplorerTreeNode("Grid Cell Widgets", "featured-grid-cell-widgets", "featured-category", null, true, idSuffix),
					new ExplorerTreeNode("Miller Columns", "featured-miller-columns", "featured-category", "silk/ipod.png", true, idSuffix),
					new ExplorerTreeNode("Nested Grid", "featured-nested-grid", "featured-category", "crystal/16/mimetypes/widget_doc.png", true, idSuffix),
					new ExplorerTreeNode("Tile Sort &amp; Filtering", "featured-tile-filtering", "featured-category", "silk/application_view_tile.png", true, idSuffix),
					new ExplorerTreeNode("Print Grid", "featured-print-grid", "featured-category", "silk/printer.png", true, idSuffix),
					new ExplorerTreeNode("Adv. Filter Builder", "featured-filter-builder-grid", "featured-category", "crystal/oo/sc_insertformula.png", true, idSuffix),
					new ExplorerTreeNode("Tree Grid", "featured-tree-grid", "featured-category", "silk/chart_organisation.png", true, idSuffix),
					new ExplorerTreeNode("Grid Header Spans", "featured-grid-header-span", "featured-category", null, true, idSuffix),
					new ExplorerTreeNode("Live Grid", "featured-grid-live", "featured-category", "silk/application_put.png", true, idSuffix),
					new ExplorerTreeNode("Vertical Tabs", "featured-vertical-tabs", "featured-category", "silk/tab.png", true, idSuffix),
					new ExplorerTreeNode("Databound Calendar", "featured-databound-calendar-category", "featured-category", "crystal/16/apps/cal.png", true, idSuffix),
					new ExplorerTreeNode("Dropdown Grid", "featured-dropdown-grid-category", "featured-category", "crystal/16/actions/completion.png", true, idSuffix),
					new ExplorerTreeNode("Dynamic Grouping", "featured-dynamic-grouping", "featured-category", "silk/application_side_tree.png", true, idSuffix),
					new ExplorerTreeNode("Animation Playpen", "featured-animation-playpen", "featured-category", "silk/layers.png", true, idSuffix),
					new ExplorerTreeNode("Master Detail", "featured-master-detail", "featured-category", "silk/application_split.png", true, idSuffix),
					new ExplorerTreeNode("RestDataSource", "featured-restfulds", "featured-category", "silk/arrow_refresh_small.png", true, idSuffix),
					new ExplorerTreeNode("Yahoo! JSON Service", "featured-json-integration-category-yahoo", "featured-category", "crystal/16/apps/yahoo_protocol.png",
							true, idSuffix),
					new ExplorerTreeNode("Pattern Reuse", "featured-pattern-reuse", "featured-category", "silk/database_table.png", true, idSuffix),
					new ExplorerTreeNode("XSD DataSource", "featured-xsd-ds", "featured-category", "silk/database_gear.png", true, idSuffix),
					new ExplorerTreeNode("GWT Integration", "featured-gwt-integration", "featured-category", "gwt/icon16.png", true, idSuffix),
					new ExplorerTreeNode("Portal", "featured-portal", "featured-category", "silk/application_view_tile.png", true, idSuffix),
					new ExplorerTreeNode("Databound Dragging", "grid-db-dragging-featured-category", "featured-category", "silk/database_link.png", true, idSuffix),
					new ExplorerTreeNode("Adaptive Filter", "grid-adaptive-filter-featured-category", "featured-category", "crystal/16/actions/show_table_row.png", true,
							idSuffix),
					new ExplorerTreeNode("Formula &amp; Summary Builder", "formula-sumamry-builder-featured-category", "featured-category",
							"crystal/oo/sc_insertformula.png", true, idSuffix),
					new ExplorerTreeNode("Grid Summaries", "grid-summaries-featured-category", "featured-category", "crystal/16/apps/tooloptions.png", true, idSuffix),

					// new ExplorerTreeNode("Background Color",
					// "effects-lf-background-color", "effects-lf-category", null, null,
					// false, idSuffix),
					// new ExplorerTreeNode("Background Texture",
					// "effects-lf-background-texture", "effects-lf-category", null, null,
					// false, idSuffix),
					new ExplorerTreeNode("Translucency", "effects-lf-translucency", "effects-lf-category", null, true, idSuffix),
					// new ExplorerTreeNode("Box Attributes", "effects-lf-box-attributes",
					// "effects-lf-category", null, null, false, idSuffix),
					new ExplorerTreeNode("CSS Styles", "effects-lf-css", "effects-lf-category", null, true, idSuffix),
			// new ExplorerTreeNode("Consistent Sizing", "effects-lf-sizing",
			// "effects-lf-category", null, null, false, idSuffix),
			// new ExplorerTreeNode("Grid Cells", "effects-lf-grid-cells",
			// "effects-lf-category", null, null, false, idSuffix),

			};
		}
		return data;
	}

	public static ExplorerTreeNode[] getData(String idSuffix) {
		return new ShowcaseData(idSuffix).getData();
	}
}
