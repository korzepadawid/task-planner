package io.github.korzepadawid.springtaskplanning.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

public abstract class PaginationLinkHeader {

  public static void addHeader(
      HttpServletResponse httpServletResponse,
      HttpServletRequest httpServletRequest,
      Page<?> page,
      Integer currentPage) {
    Integer firstPage = 1;
    Integer lastPage = page.getTotalPages();

    String uri = httpServletRequest.getRequestURL().toString();

    StringBuilder linkHeader = new StringBuilder();

    if (page.hasNext()) {
      String next = String.format("<%s?page=%d>; rel=\"next\",", uri, currentPage + 1);
      linkHeader.append(next);
    }

    if (page.hasPrevious()) {
      String prev = String.format("<%s?page=%d>; rel=\"prev\",", uri, currentPage - 1);
      linkHeader.append(prev);
    }

    if (!page.isLast()) {
      String last = String.format("<%s?page=%d>; rel=\"last\",", uri, lastPage);
      linkHeader.append(last);
    }

    if (!page.isFirst()) {
      String first = String.format("<%s?page=%d>; rel=\"first\",", uri, firstPage);
      linkHeader.append(first);
    }

    if (linkHeader.length() > 1) {
      linkHeader.deleteCharAt(linkHeader.length() - 1);
      httpServletResponse.addHeader(HttpHeaders.LINK, linkHeader.toString());
    }
  }
}
