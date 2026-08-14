package com.dineth.url2product.service;

import com.dineth.url2product.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.jsoup.HttpStatusException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

@Slf4j
@Service
public class WebPageFetcher {

    private static final int TIMEOUT_MILLIS = 15_000;
    private static final int MAX_BODY_SIZE = 10 * 1024 * 1024;

    private static final String USER_AGENT =
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) " +
                    "Chrome/131.0.0.0 Safari/537.36";

    public String fetch(String url) {

        try {

            log.debug("Fetching webpage | url={}", url);

            Connection.Response response = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .referrer("https://www.google.com/")
                    .header(
                            "Accept",
                            "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"
                    )

                    .header(
                            "Accept-Language",
                            "en-US,en;q=0.9"
                    )

                    // Don't request gzip/br compressed content.
                    // This prevents compressed binary data being interpreted as HTML.
                    .header("Accept-Encoding", "identity")

                    .header("Cache-Control", "no-cache")
                    .header("Pragma", "no-cache")
                    .header("Upgrade-Insecure-Requests", "1")

                    .timeout(TIMEOUT_MILLIS)
                    .followRedirects(true)
                    .maxBodySize(MAX_BODY_SIZE)
                    .ignoreHttpErrors(false)

                    .execute();

            log.debug(
                    "Webpage fetched | status={} | contentType={} | encoding={} | finalUrl={}",
                    response.statusCode(),
                    response.contentType(),
                    response.header("Content-Encoding"),
                    response.url()
            );

            String contentType = response.contentType();

            if (contentType != null && !contentType.toLowerCase().contains("text/html") &&
                    !contentType.toLowerCase().contains("application/xhtml+xml")) {

                log.warn(
                        "Unexpected content type | contentType={} | url={}", contentType, url
                );
            }

            return response.parse().outerHtml();

        } catch (HttpStatusException e) {

            log.error(
                    "Failed to fetch webpage | status={} | url={}", e.getStatusCode(), url, e
            );

            throw new BadRequestException(
                    "Failed to fetch webpage. HTTP status: " + e.getStatusCode() + " | URL: " + url
            );

        } catch (IOException e) {

            log.error("Failed to fetch webpage | url={}", url, e);
            throw new BadRequestException("Failed to fetch webpage: " + url);
        }
    }
}