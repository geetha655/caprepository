package org.sample.sample.core.wcmuse;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;

public class NavigationItem {

    private static final Logger logger = LoggerFactory.getLogger(NavigationItem.class);

    private final Page page;

    private final List<NavigationItem> listChildren;


    private Locale locale;

    private boolean hideInNav;

    public NavigationItem(Page page, ResourceResolver resourceResolver) {
        this.page = page;
        this.listChildren = findChildren(page, resourceResolver);
       
    }

    
    
    private List<NavigationItem> findChildren(Page page, ResourceResolver resourceResolver) {
        List<NavigationItem> children = new ArrayList<NavigationItem>();
        Iterator<Page> pageIterator = page.listChildren();
        while (pageIterator.hasNext()) {
            Page nextPage = pageIterator.next();
            if (!nextPage.isHideInNav()) {
                children.add(new NavigationItem(nextPage, resourceResolver));
            }
        }
        return children;
    }

   

  

   
    public List<NavigationItem> listChildren() {
        return listChildren;
    }

    public boolean isHasChildren() {
        return !listChildren.isEmpty();
    }

    public boolean isHideInNav() {
        return hideInNav;
    }

    public String getPath() {
        return page.getPath();
    }

    public String getHideInNav() {
        return Boolean.toString(page.isHideInNav());
    }

    public String getTitle() {
        if (page.getNavigationTitle() != null)
            return page.getNavigationTitle();
        return page.getTitle();
    }

    

}
