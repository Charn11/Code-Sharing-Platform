<!DOCTYPE html>
<html>
    <head>
        <title>${title}</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="/styles.css">
        <link rel="stylesheet"
               href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
        <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
        <script>hljs.initHighlightingOnLoad();</script>
    </head>
    <body>
        <#list codes as code>
        <div class="code-block">
            <#list code as key, value>
                <#if key == "date">
                <span id="load_date">${value}</span>
                </#if>
                <#if key == "time">
                <span id="time_restriction">The code will be available for ${value} seconds</span>
                </#if>
                <#if key == "views">
                <span id="views_restriction">${value} more views allowed</span>
                </#if>
                <#if key == "code">
                <pre id="code_snippet"><code>${value}</code></pre>
                </#if>
            </#list>
            </div>
        </#list>
        <script src="/script.js"></script>
    </body>
</html>