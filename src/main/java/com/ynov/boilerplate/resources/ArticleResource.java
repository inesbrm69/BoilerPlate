package com.ynov.boilerplate.resources;

import com.ynov.boilerplate.entity.Article;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.slf4j.Logger;

@Controller
@RequestMapping(value = "/articles")
public class ArticleResource {
    private static final Logger log = LoggerFactory.getLogger(ArticleResource.class);
}
