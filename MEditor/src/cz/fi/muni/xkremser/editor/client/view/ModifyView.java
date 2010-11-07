/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.TabBarControls;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tile.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.tile.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.viewer.DetailFormatter;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter;
import cz.fi.muni.xkremser.editor.client.view.tab.DCTab;
import cz.fi.muni.xkremser.editor.client.view.tab.ModsTab;
import cz.fi.muni.xkremser.editor.shared.valueobj.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class ModifyView.
 */
public class ModifyView extends ViewImpl implements ModifyPresenter.MyView {

	private static final String ID_DC = "dc";
	private static final String ID_MODS = "mods";

	/** The tile grid. */
	private TileGrid tileGrid;

	/** The layout. */
	private final VLayout layout;

	/** The images layout. */
	private VLayout imagesLayout;

	/** The top tab set1. */
	private TabSet topTabSet1;

	/** The top tab set2. */
	private TabSet topTabSet2;
	// private VLayout imagesLayout1;
	// private VLayout imagesLayout2;
	/** The image popup. */
	private PopupPanel imagePopup;

	/** The first. */
	private boolean first = true;

	// private boolean first = true;

	// private final GlassPanel glassPanel;

	/**
	 * Instantiates a new modify view.
	 */
	public ModifyView() {
		layout = new VLayout();
		layout.setOverflow(Overflow.AUTO);
		layout.setLeaveScrollbarGap(true);
		// layout.setCanDragResize(true);

		// HLayout buttons = new HLayout();
		// buttons.setMembersMargin(15);

		// IButton blueButton = new IButton("Select Blue");
		// blueButton.addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// topTabSet.selectTab(0);
		// }
		// });

		// buttons.addMember(blueButton);

		// layout.addMember(buttons);

		// IButton print = new IButton("print");
		// print.addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// print();
		// }
		// });
		// imagesLayout.addMember(print);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter.MyView#getName
	 * ()
	 */
	@Override
	public HasValue<String> getName() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter.MyView#getSend
	 * ()
	 */
	@Override
	public HasClickHandlers getSend() {
		return null;
	}

	/**
	 * Prints the.
	 */
	public void print() {
		Record[] data = tileGrid.getData();
		for (Record rec : data) {
			System.out.println(rec.getAttribute(Constants.ATTR_NAME));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter.MyView#
	 * addDigitalObject(boolean, com.smartgwt.client.data.Record[],
	 * com.gwtplatform.dispatch.client.DispatchAsync)
	 */
	@Override
	public void addDigitalObject(boolean tileGridVisible, Record[] data, final DublinCore dc, DispatchAsync dispatcher) {
		// if (first) {
		// imagesLayout1 = new VLayout();
		// } else {
		// imagesLayout2 = new VLayout();
		// }
		imagesLayout = new VLayout();

		final TabSet topTabSet = new TabSet();
		topTabSet.setTabBarPosition(Side.TOP);
		topTabSet.setWidth100();
		topTabSet.setHeight100();

		final ImgButton closeButton = new ImgButton();
		closeButton.setSrc("[SKIN]headerIcons/close.png");
		closeButton.setSize(16);
		// closeButton.setShowFocused(false);
		closeButton.setShowRollOver(true);
		closeButton.setCanHover(true);
		closeButton.setShowDownIcon(false);
		closeButton.setShowDown(false);
		closeButton.addHoverHandler(new HoverHandler() {
			@Override
			public void onHover(HoverEvent event) {
				closeButton.setPrompt("Close this digital object.");
			}
		});

		// IButton closeButton = new IButton("close");
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				layout.removeMember(topTabSet);
				first = !first;
				if (topTabSet1 == topTabSet) {
					topTabSet1 = null;
				} else {
					topTabSet2 = null;
				}
			}
		});
		topTabSet.setTabBarControls(TabBarControls.TAB_SCROLLER, TabBarControls.TAB_PICKER, closeButton);
		topTabSet.setAnimateTabScrolling(true);

		Tab tTab1 = new Tab("Relations", "pieces/16/pawn_red.png");
		tTab1.setPane(imagesLayout);

		final Tab tTab2 = new Tab("DC", "pieces/16/pawn_green.png");
		tTab2.setID(ID_DC + (first ? "_1" : "_0"));
		final Tab tTab3 = new Tab("MODS", "pieces/16/pawn_blue.png");
		tTab3.setID(ID_MODS + (first ? "_1" : "_0"));

		Tab tTab4 = new Tab("Thumbnail", "pieces/16/pawn_white.png");
		Img tImg4 = new Img("pieces/48/pawn_white.png", 48, 48);
		tTab4.setPane(tImg4);

		Tab tTab5 = new Tab("Full image", "pieces/16/pawn_yellow.png");
		Img tImg5 = new Img("pieces/48/pawn_yellow.png", 48, 48);
		tTab5.setPane(tImg5);

		Tab tTab6 = new Tab("IMG ADM", "pieces/16/piece_blue.png");
		Img tImg6 = new Img("pieces/48/piece_blue.png", 48, 48);
		tTab6.setPane(tImg6);

		Tab tTab7 = new Tab("Policy", "pieces/16/piece_green.png");
		Img tImg7 = new Img("pieces/48/piece_green.png", 48, 48);
		tTab7.setPane(tImg7);

		topTabSet.setTabs(tTab1, tTab2, tTab3, tTab4, tTab5, tTab6, tTab7);
		topTabSet.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				if (((first ? "_1" : "_0") + ID_MODS).equals(event.getTab().getID()) && tTab3.getPane() == null) {
					final ModalWindow mw = new ModalWindow(topTabSet);
					mw.setLoadingIcon("loadingAnimation.gif");
					mw.show(true);
					Timer timer = new Timer() {
						@Override
						public void run() {
							Tab t = new ModsTab(1, true);
							topTabSet.setTabPane(ID_MODS, t.getPane());
							mw.hide();
						}
					};
					timer.schedule(25);
				}
				if ((ID_DC + (first ? "_1" : "_0")).equals(event.getTab().getID()) && tTab2.getPane() == null) {
					final ModalWindow mw = new ModalWindow(topTabSet);
					mw.setLoadingIcon("loadingAnimation.gif");
					mw.show(true);
					Timer timer = new Timer() {
						@Override
						public void run() {
							Tab t = new DCTab(dc);
							topTabSet.setTabPane(ID_DC, t.getPane());
							mw.hide();
						}
					};
					timer.schedule(25);
				}
			}
		});

		// topTabSet.setSelectedTab(2); // TODO: remove
		layout.setMembersMargin(15);
		// layout.addMember(topTabSet);
		if (first) {
			if (topTabSet1 != null)
				layout.removeMember(topTabSet1);
			topTabSet1 = topTabSet;
			layout.addMember(topTabSet1, 0);
		} else {
			if (topTabSet2 != null)
				layout.removeMember(topTabSet2);
			topTabSet2 = topTabSet;
			layout.addMember(topTabSet2, 1);
		}

		if (tileGridVisible == true) {
			setTileGrid();
			tileGrid.setData(data);
		}
		first = !first;
	}

	/**
	 * Sets the tile grid.
	 */
	private void setTileGrid() {
		tileGrid = new TileGrid();
		// tileGrid.setCanSelectText(true);
		tileGrid.setTileWidth(110);
		tileGrid.setTileHeight(140);
		tileGrid.setHeight100();
		tileGrid.setWidth100();
		tileGrid.setCanDrag(true);
		tileGrid.setCanAcceptDrop(true);
		tileGrid.setShowAllRecords(true);
		// tileGrid.setCanDragResize(true);
		imagePopup = new PopupPanel(true);
		imagePopup.setGlassEnabled(true);
		imagePopup.setAnimationEnabled(true);
		tileGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {

			@Override
			public void onRecordDoubleClick(final RecordDoubleClickEvent event) {
				if (event.getRecord() != null) {
					try {
						final ModalWindow mw = new ModalWindow(layout);
						mw.setLoadingIcon("loadingAnimation.gif");
						mw.show(true);
						final Image full = new Image("images/full/" + event.getRecord().getAttribute(Constants.ATTR_UUID));
						full.setHeight("700px");
						full.addLoadHandler(new LoadHandler() {
							@Override
							public void onLoad(LoadEvent event) {
								mw.hide();
								imagePopup.setVisible(true);
								imagePopup.center();

							}
						});
						imagePopup.setWidget(full);
						imagePopup.center();
						imagePopup.setVisible(false);

					} catch (Throwable t) {
						System.out.println("yes sir");
					}
				}
			}

			/*
			 * if (event.getRecord() != null) { try { final String url =
			 * "images/full/" + event.getRecord().getAttribute(Constants.ATTR_UUID);
			 * final Image full = new Image(); Image.prefetch(url); final ModalWindow
			 * mw = new ModalWindow(layout);
			 * mw.setLoadingIcon("loadingAnimation.gif"); mw.show(true); Timer timer1
			 * = new Timer() {
			 * 
			 * @Override public void run() { full.setUrl(url);
			 * full.setHeight("700px"); full.addLoadHandler(new LoadHandler() {
			 * 
			 * @Override public void onLoad(LoadEvent event) { mw.hide();
			 * imagePopup.setWidget(full); imagePopup.center(); } }); } };
			 * timer1.schedule(150);
			 * 
			 * } catch (Throwable t) { System.out.println("yes sir"); } } }
			 */
		});

		DetailViewerField pictureField = new DetailViewerField(Constants.ATTR_PICTURE);
		pictureField.setType("image");
		pictureField.setImageURLPrefix(Constants.SERVLET_THUMBNAIL_PREFIX + '/');
		pictureField.setImageWidth(80);
		pictureField.setImageHeight(120);

		DetailViewerField nameField = new DetailViewerField(Constants.ATTR_NAME);
		nameField.setDetailFormatter(new DetailFormatter() {
			@Override
			public String format(Object value, Record record, DetailViewerField field) {
				return "Title: " + value;
			}
		});

		DetailViewerField descField = new DetailViewerField(Constants.ATTR_DESC);

		tileGrid.setFields(pictureField, nameField, descField);
		imagesLayout.addMember(tileGrid);
	}

	/**
	 * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
	 * 
	 * @return the widget
	 */
	@Override
	public Widget asWidget() {
		return layout;
	}
}