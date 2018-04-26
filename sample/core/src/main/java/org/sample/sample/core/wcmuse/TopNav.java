package org.sample.sample.core.wcmuse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;

public class TopNav extends WCMUsePojo {
	
	private static final Logger LOG = LoggerFactory.getLogger(TopNav.class);
	private List<NavigationItem> items = new ArrayList<NavigationItem>();
	


	private String home;
	@Override
	public void activate() throws Exception {
		// TODO Auto-generated method stub
		LOG.debug("Inisde topnav component wcmuse class");
		Page rootPage = getCurrentPage().getAbsoluteParent(2);
		items = buildNavigationItems(rootPage);
		home = rootPage.getPath() + ".html";
	}
		
		
		
		private List<NavigationItem> buildNavigationItems(Page page) {
			List<NavigationItem> navigationItems = new ArrayList<NavigationItem>();

			if (page != null) {
				Iterator<Page> childPages = page.listChildren();

				while (childPages.hasNext()) {
					Page childPage = childPages.next();
					if (childPage != null ) {
						navigationItems.add(new NavigationItem(childPage, getResourceResolver()));
					}
				}
			}

			return navigationItems;
		}
		
	
		public String getHome() {
			return home;
		}

		public List<NavigationItem> getItems() {
			return items;
		}


}
