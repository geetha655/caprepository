package org.sample.sample.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.i18n.I18n;
import com.day.cq.wcm.api.Page;
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class })
public class Rssfeedsample {
	private static final Logger LOG = LoggerFactory.getLogger(Rssfeedsample.class);
	 @Inject
	    private ResourceResolverFactory resourceResolverFactory;
	 private static final String ALL_NEWS_RSS_LINK = "/services/wcwfeed?lang=%s&feedtype=news";
		private static final String NOTIFIABLE_TRADING_RSS_LINK = "/services/wcwfeed?lang=%s&feedtype=notif";
		private static final String REMIT_RSS_LINK = "/services/remitrss";
		private static final String STOCK_MARKET_ANNOUNCEMENTS_RSS_LINK = "/services/wcwfeed?lang=%s&feedtype=stock";

		private I18n i18n;
		private Locale locale;
    @Inject @Named("title")@Optional
		private String title;
    @Inject @Named("space")@Optional
		private String space;
		private List<RssFeedLink> rssFeedLinks = new ArrayList<RssFeedLink>();
		@Inject
		private Page currentPage;
		@Inject
		protected SlingHttpServletRequest request;
		@Inject
		protected ValueMap properties;
		@PostConstruct
		protected void init()
		{
			LOG.info("Inside rssfeed sample");
			locale=currentPage.getLanguage(true);
            ResourceBundle resbundle=request.getResourceBundle(locale);
            i18n = new I18n(resbundle);
		AddRssFeedLink("allNews", ALL_NEWS_RSS_LINK);
		AddRssFeedLink("notifiableTrading", NOTIFIABLE_TRADING_RSS_LINK);
		AddRssFeedLink("remit", REMIT_RSS_LINK);
		AddRssFeedLink("stockMarketAnnouncements", STOCK_MARKET_ANNOUNCEMENTS_RSS_LINK);
	}

	private void AddRssFeedLink(String propertyName, String rssFeedLink) {
		String propertyValue = properties.get(propertyName, String.class);
		if (StringUtils.isNotBlank(propertyValue) && propertyValue.equals("true")) {
			String text = i18n.get("rssFeed." + propertyName);
			String link = String.format(rssFeedLink, locale.getLanguage());
			rssFeedLinks.add(new RssFeedLink(text, link));
		}
	}

	public List<RssFeedLink> getRssFeedLinks() {
		return rssFeedLinks;
	}

	public String getTitle() {
		return title;
	}

	public String getSpace() {
		return space;
	}
		
	public class RssFeedLink {
		private String link;
		private String text;

		public RssFeedLink(String text, String link) {
			this.text = text;
			this.link = link;
		}

		public String getLink() {
			return link;
		}

		public String getText() {
			return text;
		}
	}
}
