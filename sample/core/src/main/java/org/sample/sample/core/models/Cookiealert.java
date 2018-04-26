package org.sample.sample.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;

import com.day.cq.wcm.api.Page;

@Model(adaptables = { SlingHttpServletRequest.class, Resource.class })
public class Cookiealert {
	
	private String message;
	@Inject
	protected ResourceResolver resourceResolver;
	 @Inject
	    private Page currentPage;

@PostConstruct
	protected void init() throws Exception  {
		Page rootPage = getRootPage();
		message = rootPage.getProperties().get("cookieMessage", String.class);
	}
	
	public String getMessage() {
		return message;
	}

	private Page getRootPage() throws Exception {
		return currentPage.getAbsoluteParent(2);

	}


}
