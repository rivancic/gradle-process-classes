package com.rivancic.kotlin.process.languages.jvm

import com.google.common.collect.ImmutableMap
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

/**
 * Java extends Filter from javax just to show test classloader
 */
class Java : Filter {

    var versions: Map<*, *> = ImmutableMap.of("JDK 1.0", "January 1996",
        "Java SE 8 (LTS)", "March 2014",
        "Java SE 11 (LTS)", "September 20181")

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        // Do nothing
    }
}