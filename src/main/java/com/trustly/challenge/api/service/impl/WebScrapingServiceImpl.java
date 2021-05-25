package com.trustly.challenge.api.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.trustly.challenge.api.entity.GitHubFileData;
import com.trustly.challenge.api.entity.GitHubRepositoryData;
import com.trustly.challenge.api.service.WebScrapingService;
import com.trustly.challenge.api.utils.StringUtils;

@Service
public class WebScrapingServiceImpl implements WebScrapingService {

	private static final Logger log = LoggerFactory.getLogger(WebScrapingServiceImpl.class);

	/**
	 * @author Christiano Soneghett
	 * @param webUrl = the root URL that the method will extract the information
	 * @return a list of all files from a GitHub repository
	 * @throws IOException
	 */
	@Cacheable(value = "getRepositoryData")
	public GitHubRepositoryData getRepositoryData(String webUrl) throws IOException {

		log.info("Initializing the scraping of the repository page");

		URL url = new URL(webUrl);
		InputStream is = url.openStream();

		GitHubRepositoryData ghrd = new GitHubRepositoryData(webUrl);

		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			List<GitHubFileData> files = new ArrayList<>();

			String reference = "href=\"/".concat(ghrd.getOwner()).concat("/").concat(ghrd.getName()).concat("/");
			String line;

			while ((line = br.readLine()) != null) {
				if (line.contains("<span") && line.contains(reference)
						&& (line.contains("blob") || line.contains("tree"))) {
					getFilesInformation(files, line, ghrd);
				}
			}

			ghrd.setRepositoryFiles(files);

			return ghrd;

		} catch (MalformedURLException e) {
			log.error(e.toString());
			throw new MalformedURLException("URL is malformed!!");
		} catch (IOException e) {
			log.error(e.toString());
			throw new IOException();
		}

	}

	private static void getFilesInformation(List<GitHubFileData> files, String line, GitHubRepositoryData ghrd)
			throws IOException {

		String[] s = line.split("href=\"");
		String refUrl = s[1].substring(0, s[1].indexOf('\"'));
		String urlFinal = "https://github.com" + refUrl;

		log.info(refUrl);

		/*
		 * If the webpage finds a element that contains a single file, outside a folder,
		 * just save its information in the list
		 */
		if (!urlFinal.contains("/tree/")) {
			URL subUrl = new URL(urlFinal);
			InputStream is2 = subUrl.openStream();

			try (BufferedReader br2 = new BufferedReader(new InputStreamReader(is2))) {

				String l;
				while ((l = br2.readLine()) != null) {

					if (l.contains("class=\"text-mono f6 flex-auto pr-3 flex-order-2 flex-md-order-1\"")) {

						List<String> fileInfos = new ArrayList<>();
						String[] nameAndExtension = StringUtils.getFileNameAndExtension(refUrl);

						while ((l = br2.readLine()) != null && !l.contains("</div>")) {
							if (!l.isEmpty() && !l.contains("span"))
								fileInfos.add(l.trim());
						}

						if (fileInfos.size() == 1) {
							files.add(new GitHubFileData(ghrd, nameAndExtension[0], nameAndExtension[1], "0", "0",
									fileInfos.get(0)));
						} else if (fileInfos.size() == 2) {
							String[] numberOfLines = fileInfos.get(0).replaceAll("[()]", "").split(" ");

							files.add(new GitHubFileData(ghrd, nameAndExtension[0], nameAndExtension[1],
									numberOfLines[0], numberOfLines[2], fileInfos.get(1)));
						} else {
							log.info("Unexpected information. Need to check!");
						}

						log.info("{}", fileInfos);
						break;
					}
				}
			}
		}
		/*
		 * If the webpage finds a folder (with or withou a file), makes a recursive
		 * function to process each file inside folder
		 */
		else if (urlFinal.contains("/tree/")) {

			URL subUrl = new URL(urlFinal);
			InputStream is2 = subUrl.openStream();

			try (BufferedReader br2 = new BufferedReader(new InputStreamReader(is2))) {

				String reference = "href=\"/".concat(ghrd.getOwner()).concat("/").concat(ghrd.getName()).concat("/");

				String l;
				while ((l = br2.readLine()) != null) {
					if (l.contains("<span") && l.contains(reference) && (l.contains("blob") || l.contains("tree"))) {
						getFilesInformation(files, l, ghrd);
					}
				}
			}
		}

	}

}
